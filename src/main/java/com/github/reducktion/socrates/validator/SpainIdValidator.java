package com.github.reducktion.socrates.validator;

import org.apache.commons.lang3.StringUtils;

/**
 * National Identification Number validator for Spain.
 *
 * This validation algorithm is based on the official documentation:
 * http://www.interior.gob.es/web/servicios-al-ciudadano/dni/calculo-del-digito-de-control-del-nif-nie
 */
class SpainIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;
    private static final String CONTROL_CHARACTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        final String partialId = stripControlCharacter(sanitizedId)
            .replace("X", "0")
            .replace("Y", "1")
            .replace("Z", "2");

        if (!StringUtils.isNumeric(partialId)) {
            return false;
        }

        final int result = Integer.parseInt(partialId) % CONTROL_CHARACTERS.length();
        final char expectedControlCharacter = CONTROL_CHARACTERS.charAt(result);

        return getControlCharacter(sanitizedId) == expectedControlCharacter;
    }

    private String sanitize(final String id) {
        return id
            .trim()
            .replace("-", "")
            .toUpperCase();
    }

    private String stripControlCharacter(final String id) {
        return id.substring(0, id.length() - 1);
    }

    private char getControlCharacter(final String id) {
        return id
            .substring(id.length() - 1)
            .charAt(0);
    }
}
