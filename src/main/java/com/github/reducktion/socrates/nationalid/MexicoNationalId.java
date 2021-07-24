package com.github.reducktion.socrates.nationalid;

import java.time.Year;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Gender;
import com.github.reducktion.socrates.internal.DateValidator;
import com.github.reducktion.socrates.internal.TwoYearDateParser;

/**
 * National Id for Mexico.
 *
 * Information about this national id can be found at:
 *  - https://en.wikipedia.org/wiki/Unique_Population_Registry_Code
 *  - http://sistemas.uaeh.edu.mx/dce/admisiones/docs/guia_CURP.pdf
 *  - (Inappropriate words) https://solucionfactible.com/sfic/resources/files/palabrasInconvenientes-rfc.pdf
 */
class MexicoNationalId implements NationalId {

    private static final String FEMALE_CHARACTER = "M";
    private static final int ID_NUMBER_OF_CHARACTERS = 18;
    private static final Pattern ID_PATTERN =
        Pattern.compile("[A-Z]{4}[0-9]{6}[HM][A-Z]{2}[B-DF-HJ-NP-TV-Z]{3}[A-Z0-9][0-9]");
    private static final String DICTIONARY = "0123456789ABCDEFGHIJKLMN&OPQRSTUVWXYZ";
    private static final String[] INAPPROPRIATE_WORDS = {
        "BACA",
        "BAKA",
        "BUEI",
        "BUEY",
        "CACA",
        "CACO",
        "CAGA",
        "CAGO",
        "CAKA",
        "CAKO",
        "COGE",
        "COGI",
        "COJA",
        "COJE",
        "COJI",
        "COJO",
        "COLA",
        "CULO",
        "FALO",
        "FETO",
        "GETA",
        "GUEI",
        "GUEY",
        "JETA",
        "JOTO",
        "KACA",
        "KACO",
        "KAGA",
        "KAGO",
        "KAKA",
        "KAKO",
        "KOGE",
        "KOGI",
        "KOJA",
        "KOJE",
        "KOJI",
        "KOJO",
        "KOLA",
        "KULO",
        "LILO",
        "LOCA",
        "LOCO",
        "LOKA",
        "LOKO",
        "MAME",
        "MAMO",
        "MEAR",
        "MEAS",
        "MEON",
        "MIAR",
        "MION",
        "MOCO",
        "MOKO",
        "MULA",
        "MULO",
        "NACA",
        "NACO",
        "PEDA",
        "PEDO",
        "PENE",
        "PIPI",
        "PITO",
        "POPO",
        "PUTA",
        "PUTO",
        "QULO",
        "RATA",
        "ROBA",
        "ROBE",
        "ROBO",
        "RUIN",
        "SENO",
        "TETA",
        "VACA",
        "VAGA",
        "VAGO",
        "VAKA",
        "VUEI",
        "VUEY",
        "WUEI",
        "WUEY"
    };

    private final String id;
    private final String sanitizedId;

    private final TwoYearDateParser twoYearDateParser = new TwoYearDateParser(Year.now().getValue());

    public MexicoNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null
            ? null
            : id
                .replace(" ", "")
                .toUpperCase();
    }

    @Override
    public boolean isValid() {
        return sanitizedId != null
            && sanitizedId.length() == ID_NUMBER_OF_CHARACTERS
            && ID_PATTERN.matcher(sanitizedId).matches()
            && !hasInappropriateWords()
            && hasValidDateOfBirth()
            && hasValidCheckDigit();
    }

    private boolean hasInappropriateWords() {
        return Arrays.stream(INAPPROPRIATE_WORDS).anyMatch(sanitizedId::contains);
    }

    private boolean hasValidDateOfBirth() {
        final int year = twoYearDateParser.parse(sanitizedId.substring(4, 6)).orElse(0);
        final int month = Integer.parseInt(sanitizedId.substring(6, 8));
        final int day = Integer.parseInt(sanitizedId.substring(8, 10));

        return DateValidator.validate(year, month, day);
    }

    private boolean hasValidCheckDigit() {
        final int expectedCheckDigit = extractCheckDigit();
        final int resultCheckDigit = computeCheckDigit();
        return expectedCheckDigit == resultCheckDigit;
    }

    private int computeCheckDigit() {
        int sum = 0;

        for (int i = 0; i < sanitizedId.length() - 1; i++) {
            sum += DICTIONARY.indexOf(sanitizedId.charAt(i)) * (sanitizedId.length() - i);
        }

        return (10 - (sum % 10)) % 10;
    }

    private int extractCheckDigit() {
        final String checkCharacter = sanitizedId.substring(sanitizedId.length() - 1);
        return Integer.parseInt(checkCharacter);
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
        final String genderCharacter = sanitizedId.substring(10, 11);
        if (genderCharacter.equals(FEMALE_CHARACTER)) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
        }
    }

    private Integer extractYearOfBirth() {
        final String yearOfBirthCharacters = sanitizedId.substring(4, 6);
        return twoYearDateParser
            .parse(yearOfBirthCharacters)
            .orElse(null);
    }

    private Integer extractMonthOfBirth() {
        final String monthOfBirthCharacters = sanitizedId.substring(6, 8);
        return Integer.parseInt(monthOfBirthCharacters);
    }

    private Integer extractDayOfBirth() {
        final String dayOfBirthCharacters = sanitizedId.substring(8, 10);
        return Integer.parseInt(dayOfBirthCharacters);
    }

    private String extractPlaceOfBirth() {
        final String placeOfBirthCharacters = sanitizedId.substring(11, 13);
        return getRegionByCode(placeOfBirthCharacters);
    }

    private String getRegionByCode(final String code) {
        switch (code) {
            case "AS": return "AGUASCALIENTES";
            case "BS": return "BAJA CALIFORNIA SUR";
            case "CL": return "COAHUILA";
            case "CS": return "CHIAPAS";
            case "DF": return "DISTRITO FEDERAL";
            case "GT": return "GUANAJUATO";
            case "HG": return "HIDALGO";
            case "MC": return "MÉXICO";
            case "MS": return "MORELOS";
            case "NL": return "NUEVO LEÓN";
            case "PL": return "PUEBLA";
            case "QR": return "QUINTANA ROO";
            case "SL": return "SINALOA";
            case "TC": return "TABASCO";
            case "TL": return "TLAXCALA";
            case "YN": return "YUCATÁN";
            case "NE": return "NACIDO EN EL EXTRANJERO";
            case "BC": return "BAJA CALIFORNIA";
            case "CC": return "CAMPECHE";
            case "CM": return "COLIMA";
            case "CH": return "CHIHUAHUA";
            case "DG": return "DURANGO";
            case "GR": return "GUERRERO";
            case "JC": return "JALISCO";
            case "MN": return "MICHOACÁN";
            case "NT": return "NAYARIT";
            case "OC": return "OAXACA";
            case "QT": return "QUERÉTARO";
            case "SP": return "SAN LUIS POTOSÍ";
            case "SR": return "SONORA";
            case "TS": return "TAMAULIPAS";
            case "VZ": return "VERACRUZ";
            case "ZS": return "ZACATECAS";
            default: return null;
        }
    }

    @Override
    public String toString() {
        return id;
    }
}
