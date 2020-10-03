package com.github.reducktion.socrates.validator;

import org.apache.commons.lang3.StringUtils;

/**
 * National Identification Number validator for France.
 *
 * This validation algorithm is based on documentation released in 22 of March of 2001:
 * http://resoo.org/docs/_docs/regles-numero-insee.pdf
 *
 * An english version is available in wikipedia: https://en.wikipedia.org/wiki/INSEE_code
 *
 * Also, there is extra info in https://fr.wikipedia.org/wiki/Num%C3%A9ro_de_s%C3%A9curit%C3%A9_sociale_en_France,
 * specially about the algorithm for Corsica (2A -> 19, 2B -> 20).
 */
class FranceIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 15;
    private static final int CONTROL_DIGIT_MAX_VALUE = 97;

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id)
            .replace("2A", "19")
            .replace("2B", "18");

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        if (!StringUtils.isNumeric(sanitizedId)) {
            return false;
        }

        return validateControlDigit(sanitizedId);
    }

    private String sanitize(final String id) {
        return id.replace(" ", "");
    }

    private boolean validateControlDigit(final String id) {
        final String partialId = stripControlDigit(id);
        final long controlDigit = getControlDigit(id);

        return controlDigit == CONTROL_DIGIT_MAX_VALUE - (Long.parseLong(partialId) % CONTROL_DIGIT_MAX_VALUE);
    }

    private String stripControlDigit(final String id) {
        return id.substring(0, id.length() - 2);
    }

    private long getControlDigit(final String id) {
        final String controlDigit = id.substring(id.length() - 2);
        return Integer.parseInt(controlDigit);
    }
}
