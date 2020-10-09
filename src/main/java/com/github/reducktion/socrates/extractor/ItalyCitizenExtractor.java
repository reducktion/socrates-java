package com.github.reducktion.socrates.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Optional;

import com.github.reducktion.socrates.internal.ItalyOmocodiaSwapper;
import com.github.reducktion.socrates.internal.TwoYearDateParser;
import com.github.reducktion.socrates.validator.IdValidator;

/**
 * {@link Citizen} extractor for Italy.
 *
 * This algorithm is based on the Italy's Ministry of Finance Decree of 12/03/1974 n. 2227:
 * http://www.dossier.net/utilities/codice-fiscale/decreto1974_2227.html
 *
 * An english version is available in wikipedia: https://en.wikipedia.org/wiki/Italian_fiscal_code
 *
 * Information about the region codes can be found in
 * https://web.archive.org/web/20160819012136/http://www.agenziaentrate.gov.it/wps/wcm/connect/321b0500426a5e2492629bc065cef0e8/codicicatastali_comuni_29_11_2010.pdf?MOD=AJPERES&CACHEID=321b500426a5e2492629bc065cef0e8
 */
class ItalyCitizenExtractor implements CitizenExtractor {

    private static final String MONTH_CODES = "ABCDEHLMPRST";
    private static final Path REGIONS_FILE_PATH = Paths.get("./src/main/resources/italy_regions.csv");

    private final TwoYearDateParser twoYearDateParser = new TwoYearDateParser(Year.now().getValue());

    @Override
    public Optional<Citizen> extractFromId(final String id, final IdValidator idValidator) {
        if (!idValidator.validate(id)) {
            return Optional.empty();
        }

        final String sanitizedId = ItalyOmocodiaSwapper.swap(sanitize(id));

        final Citizen citizen = Citizen
            .builder()
            .gender(extractGender(sanitizedId))
            .yearOfBirth(extractYearOfBirth(sanitizedId))
            .monthOfBirth(extractMonthOfBirth(sanitizedId))
            .dayOfBirth(extractDayOfBirth(sanitizedId))
            .placeOfBirth(extractPlaceOfBirth(sanitizedId))
            .build();

        return Optional.of(citizen);
    }

    private String sanitize(final String id) {
        return id
            .replace(" ", "")
            .toUpperCase();
    }

    private Gender extractGender(final String id) {
        final String dayOfBirthCharacters = getDayOfBirthCharacters(id);

        if (Integer.parseInt(dayOfBirthCharacters) > 40) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
        }
    }

    private String getDayOfBirthCharacters(final String id) {
        return id.substring(9, 11);
    }

    private Integer extractYearOfBirth(final String id) {
        final String yearOfBirthCharacters = id.substring(6, 8);
        return twoYearDateParser
            .parse(yearOfBirthCharacters)
            .orElse(null);
    }

    private Integer extractMonthOfBirth(final String id) {
        final String monthOfBirthCharacter = id.substring(8, 9);

        return MONTH_CODES.indexOf(monthOfBirthCharacter) + 1;
    }

    private Integer extractDayOfBirth(final String id) {
        final String dayOfBirthCharacter = getDayOfBirthCharacters(id);
        final int dayOfBirth = Integer.parseInt(dayOfBirthCharacter);

        return dayOfBirth > 40 ? dayOfBirth - 40 : dayOfBirth;
    }

    private String extractPlaceOfBirth(final String id) {
        final String placeOfBirthCharacter = getPlaceOfBirthCharacters(id);

        final Optional<String> placeOfBirthConfig = fetchPlaceOfBirthConfig(placeOfBirthCharacter);

        return placeOfBirthConfig.map(c -> c.split(",")[1]).orElse(null);
    }

    private String getPlaceOfBirthCharacters(final String id) {
        return id.substring(11, 15);
    }

    private Optional<String> fetchPlaceOfBirthConfig(final String placeOfBirthCharacter) {
        Optional<String> placeOfBirthConfig = Optional.empty();

        try (final BufferedReader bufferedReader = Files.newBufferedReader(REGIONS_FILE_PATH)) {
            placeOfBirthConfig = bufferedReader
                .lines()
                .skip(1)    // skip header
                .filter(line -> line.contains(placeOfBirthCharacter))
                .findFirst();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return placeOfBirthConfig;
    }
}
