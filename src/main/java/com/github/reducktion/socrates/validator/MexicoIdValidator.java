package com.github.reducktion.socrates.validator;

import java.text.Normalizer;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

/**
 * National Identification Number validator for Mexico.
 *
 * The validation algorithm was based on the following wikipedia source:
 * https://en.wikipedia.org/wiki/Unique_Population_Registry_Code
 *
 * More information about each field of the ID can be found at:
 * http://sistemas.uaeh.edu.mx/dce/admisiones/docs/guia_CURP.pdf
 *
 * The list of inappropriate words can be found at:
 * https://solucionfactible.com/sfic/resources/files/palabrasInconvenientes-rfc.pdf
 */
class MexicoIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 18;
    private static final String DICTIONARY = "0123456789ABCDEFGHIJKLMN&OPQRSTUVWXYZ";
    private static final String[] INAPPROPRIATE_WORDS = {
        "BUEI",
        "CACA",
        "CAGA",
        "CAKA",
        "COGE",
        "COJE",
        "COJO",
        "FETO",
        "JOTO",
        "KACO",
        "KAGO",
        "KOJO",
        "KULO",
        "MAMO",
        "MEAS",
        "MION",
        "MULA",
        "PEDO",
        "PUTA",
        "RUIN",
        "BUEY",
        "CACO",
        "CAGO",
        "CAKO",
        "COJA",
        "COJI",
        "CULO",
        "GUEY",
        "KACA",
        "KAGA",
        "KOGE",
        "KAKA",
        "MAME",
        "MEAR",
        "MEON",
        "MOCO",
        "PEDA",
        "PENE",
        "PUTO",
        "RATA",
    };

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        return validateCharacters(sanitizedId)
            && validateInappropriateWords(sanitizedId)
            && validateCheckDigit(sanitizedId);
    }

    private String sanitize(final String id) {
        return id
            .replace(" ", "")
            .toUpperCase();
    }

    private boolean validateCharacters(final String id) {
        return StringUtils.isAlpha(id.substring(0, 4))
            && StringUtils.isNumeric(id.substring(4, 10))
            && StringUtils.isAlpha(id.substring(10, 16))
            && StringUtils.isNumeric(id.substring(16, 18))
            && Normalizer.isNormalized(id, Normalizer.Form.NFD);
    }

    private boolean validateInappropriateWords(final String id) {
        return Arrays.stream(INAPPROPRIATE_WORDS).noneMatch(id::contains);
    }

    private boolean validateCheckDigit(final String id) {
        final int expectedCheckDigit = getCheckDigit(id);
        final int resultCheckDigit = computeCheckDigit(id);
        return resultCheckDigit == expectedCheckDigit;
    }

    private int computeCheckDigit(final String id) {
        int sum = 0;

        for (int i = 0; i < id.length() - 1; i++) {
            sum += DICTIONARY.indexOf(id.charAt(i)) * (id.length() - i);
        }

        return (10 - (sum % 10)) % 10;
    }

    private int getCheckDigit(final String id) {
        final String checkCharacter = id.substring(id.length() - 1);
        return Integer.parseInt(checkCharacter);
    }
}
