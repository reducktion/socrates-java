package com.github.reducktion.socrates.nationalid;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Year;
import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Gender;
import com.github.reducktion.socrates.internal.ItalyOmocodiaSwapper;
import com.github.reducktion.socrates.internal.TwoYearDateParser;

/**
 * National Id for Italy.
 *
 * Information about this national id can be found at:
 *  - http://www.dossier.net/utilities/codice-fiscale/decreto1974_2227.html
 *  - https://en.wikipedia.org/wiki/Italian_fiscal_code
 *  - https://web.archive.org/web/20160819012136/http://www.agenziaentrate.gov.it/wps/wcm/connect/321b0500426a5e2492629bc065cef0e8/codicicatastali_comuni_29_11_2010.pdf?MOD=AJPERES&CACHEID=321b500426a5e2492629bc065cef0e8
 */
final class ItalyNationalId extends NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 16;
    private static final String MONTH_CODES = "ABCDEHLMPRST";
    private static final Path REGIONS_FILE_PATH = Paths.get("./src/main/resources/italy_regions.csv");

    private final String swappedSanitizedId;

    private final TwoYearDateParser twoYearDateParser = new TwoYearDateParser(Year.now().getValue());

    public ItalyNationalId(final String id) {
        super(id);
        swappedSanitizedId = ItalyOmocodiaSwapper.swap(sanitizedId);
    }

    @Override
    public boolean isValid() {
        return swappedSanitizedId != null
            && swappedSanitizedId.length() == ID_NUMBER_OF_CHARACTERS
            && hasValidControlCharacter();
    }

    private boolean hasValidControlCharacter() {
        final String expectedControlCharacter = swappedSanitizedId.substring(swappedSanitizedId.length() - 1);
        final String computedControlCharacter = computeControlCharacter();

        return expectedControlCharacter.equals(computedControlCharacter);
    }

    private String computeControlCharacter() {
        boolean isOdd = true;
        int sum = 0;

        final String partialId = stripControlCharacter(swappedSanitizedId);

        for (final String character : partialId.split("")) {
            if (isOdd) {
                sum += getValueByOddCharacter(character);
            } else {
                sum += getValueForEvenCharacter(character);
            }

            isOdd = !isOdd;
        }

        return getRemainderCharacterForValue(sum % 26);
    }

    private String stripControlCharacter(final String id) {
        return id.substring(0, id.length() - 1);
    }

    private int getValueByOddCharacter(final String oddCharacter) {
        switch (oddCharacter) {
            case "0": case "A": return 1;
            case "1": case "B": return 0;
            case "2": case "C": return 5;
            case "3": case "D": return 7;
            case "4": case "F": return 9;
            case "5": case "E": return 13;
            case "6": case "G": return 15;
            case "7": case "H": return 17;
            case "8": case "I": return 19;
            case "9": case "J": return 21;
            case "K": return 2;
            case "L": return 4;
            case "M": return 18;
            case "N": return 20;
            case "O": return 11;
            case "P": return 3;
            case "Q": return 6;
            case "R": return 8;
            case "S": return 12;
            case "T": return 14;
            case "U": return 16;
            case "V": return 10;
            case "W": return 22;
            case "X": return 25;
            case "Y": return 24;
            case "Z": return 23;
            default: return -1; // will make validation fail
        }
    }

    private int getValueForEvenCharacter(final String evenCharacter) {
        final int value = Character.getNumericValue(evenCharacter.charAt(0));

        return value > 9 ? value - 10 : value;
    }

    private String getRemainderCharacterForValue(final int value) {
        return String.valueOf((char) (value + 65));
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        if (!isValid()) {
            return Optional.empty();
        }

        return Optional.of(
            Citizen
                .builder()
                .gender(extractGender())
                .yearOfBirth(extractYearOfBirth())
                .monthOfBirth(extractMonthOfBirth())
                .dayOfBirth(extractDayOfBirth())
                .placeOfBirth(extractPlaceOfBirth())
                .build()
        );
    }

    private Gender extractGender() {
        final String dayOfBirthCharacters = extractDayOfBirthCharacters();

        if (Integer.parseInt(dayOfBirthCharacters) > 40) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
        }
    }

    private String extractDayOfBirthCharacters() {
        return swappedSanitizedId.substring(9, 11);
    }

    private Integer extractYearOfBirth() {
        final String yearOfBirthCharacters = swappedSanitizedId.substring(6, 8);
        return twoYearDateParser
            .parse(yearOfBirthCharacters)
            .orElse(null);
    }

    private Integer extractMonthOfBirth() {
        final String monthOfBirthCharacter = swappedSanitizedId.substring(8, 9);

        return MONTH_CODES.indexOf(monthOfBirthCharacter) + 1;
    }

    private Integer extractDayOfBirth() {
        final String dayOfBirthCharacter = extractDayOfBirthCharacters();
        final int dayOfBirth = Integer.parseInt(dayOfBirthCharacter);

        return dayOfBirth > 40 ? dayOfBirth - 40 : dayOfBirth;
    }

    private String extractPlaceOfBirth() {
        final String placeOfBirthCharacter = extractPlaceOfBirthCharacters();

        final Optional<String> placeOfBirthConfig = fetchPlaceOfBirthConfig(placeOfBirthCharacter);

        return placeOfBirthConfig.map(c -> c.split(",")[1]).orElse(null);
    }

    private String extractPlaceOfBirthCharacters() {
        return swappedSanitizedId.substring(11, 15);
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
