package com.github.reducktion.socrates.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Optional;

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

    private static final int[] NUMERICAL_CHARACTER_POSITIONS = {6, 7, 9, 10, 12, 13, 14};
    private static final String NUMERICAL_CHARACTER_SUBSTITUTIONS = "LMNPQRSTUV";
    private static final String MONTH_CODES = "ABCDEHLMPRST";
    private static final Path REGIONS_FILE_PATH = Paths.get("./src/main/resources/italy_regions.csv");

    private final DateTimeFormatter twoYearFormatter = new DateTimeFormatterBuilder()
        .appendValueReduced(ChronoField.YEAR, 2, 2, Year.now().getValue() - 100) // change time window
        .toFormatter();

    @Override
    public Optional<Citizen> extractFromId(final String id, final IdValidator idValidator) {
        if (!idValidator.validate(id)) {
            return Optional.empty();
        }

        final String sanitizedId = omocodiaSwap(sanitize(id));

        final Citizen citizen = new Citizen(
            extractGender(sanitizedId),
            extractYearOfBirth(sanitizedId),
            extractMonthOfBirth(sanitizedId),
            extractDayOfBirth(sanitizedId),
            extractPlaceOfBirth(sanitizedId)
        );

        return Optional.of(citizen);
    }

    private String sanitize(final String id) {
        return id
            .replace(" ", "")
            .toUpperCase();
    }

    // When the alphanumeric expression relating to the first fifteen characters of the code is common to two or more
    // subjects, it is differentiated for each of the subjects following the first coded subject.
    // To this end, within the seven numerical characters contained in the code, systematic substitutions of one or
    // more digits starting from the right one are carried out with corresponding alphabetic characters.
    private String omocodiaSwap(final String id) {
        final char[] idCharArray = id.toCharArray();

        for (final int i : NUMERICAL_CHARACTER_POSITIONS) {
            if (Character.isAlphabetic(idCharArray[i])) {
                idCharArray[i] = (char) (NUMERICAL_CHARACTER_SUBSTITUTIONS.indexOf(idCharArray[i])  + '0');
            }
        }
        return String.valueOf(idCharArray);
    }

    private String extractGender(final String id) {
        final String dayOfBirthCharacters = getDayOfBirthCharacters(id);

        if (Integer.parseInt(dayOfBirthCharacters) > 40) {
            return "F";
        } else {
            return "M";
        }
    }

    private Integer extractYearOfBirth(final String id) {
        return twoYearFormatter
            .parse(getYearOfBirthCharacters(id))
            .get(ChronoField.YEAR);
    }

    private String getYearOfBirthCharacters(final String id) {
        return id.substring(6, 8);
    }

    private Integer extractMonthOfBirth(final String id) {
        final String monthOfBirthCharacter = getMonthOfBirthCharacter(id);

        return MONTH_CODES.indexOf(monthOfBirthCharacter) + 1;
    }

    private String getMonthOfBirthCharacter(final String id) {
        return id.substring(8, 9);
    }

    private Integer extractDayOfBirth(final String id) {
        final String dayOfBirthCharacter = getDayOfBirthCharacters(id);
        final int dayOfBirth = Integer.parseInt(dayOfBirthCharacter);

        return dayOfBirth > 40 ? dayOfBirth - 40 : dayOfBirth;
    }

    private String getDayOfBirthCharacters(final String id) {
        return id.substring(9, 11);
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
