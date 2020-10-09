package com.github.reducktion.socrates.extractor;

import java.time.Year;
import java.util.Optional;

import com.github.reducktion.socrates.internal.TwoYearDateParser;
import com.github.reducktion.socrates.validator.IdValidator;

/**
 * {@link Citizen} extractor for Mexico.
 *
 * Information about the ID can be found in at: http://sistemas.uaeh.edu.mx/dce/admisiones/docs/guia_CURP.pdf
 *
 * Also, there is this wikipedia article: https://en.wikipedia.org/wiki/Unique_Population_Registry_Code
 */
class MexicoCitizenExtractor implements CitizenExtractor {

    private static final String FEMALE_CHARACTER = "M";

    private final TwoYearDateParser twoYearDateParser = new TwoYearDateParser(Year.now().getValue());

    @Override
    public Optional<Citizen> extractFromId(final String id, final IdValidator idValidator) {
        if (!idValidator.validate(id)) {
            return Optional.empty();
        }

        final String sanitizedId = sanitize(id);

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
        final String genderCharacter = id.substring(10, 11);
        if (genderCharacter.equals(FEMALE_CHARACTER)) {
            return Gender.FEMALE;
        } else {
            return Gender.MALE;
        }
    }

    private Integer extractYearOfBirth(final String id) {
        final String yearOfBirthCharacters = id.substring(4, 6);
        return twoYearDateParser
            .parse(yearOfBirthCharacters)
            .orElse(null);
    }

    private Integer extractMonthOfBirth(final String id) {
        final String monthOfBirthCharacters = id.substring(6, 8);
        return Integer.parseInt(monthOfBirthCharacters);
    }

    private Integer extractDayOfBirth(final String id) {
        final String dayOfBirthCharacters = id.substring(8, 10);
        return Integer.parseInt(dayOfBirthCharacters);
    }

    private String extractPlaceOfBirth(final String id) {
        final String placeOfBirthCharacters = id.substring(11, 13);
        return getRegionForCode(placeOfBirthCharacters);
    }

    private String getRegionForCode(final String code) {
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
}
