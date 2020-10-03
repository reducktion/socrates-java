package com.github.reducktion.socrates.validator;

import org.apache.commons.lang3.StringUtils;

class FranceIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 15;
    private static final int CONTROL_DIGIT_MAX_VALUE = 97;

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        if (!StringUtils.isNumeric(sanitizedId)) {
            return false;
        }

        return validateControlDigit(sanitizedId);
    }

    private String sanitize(final String id) {
        return id.trim()
            .replace(" ", "");
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
