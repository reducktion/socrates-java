package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.internal.LuhnAlgorithm;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Identification Number validator for Canada.
 *
 * A wikipedia source can be found in: https://en.wikipedia.org/wiki/Social_Insurance_Number
 *
 * @deprecated Use class CanadaNationalId instead
 */
@Deprecated
class CanadaIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS || !StringUtils.isNumeric(sanitizedId)) {
            return false;
        }

        return validateSequence(sanitizedId);
    }

    private String sanitize(final String id) {
        return id
            .replace(" ", "")
            .replace("-", "");
    }

    private boolean validateSequence(final String sanitizedId) {
        return LuhnAlgorithm.validate(sanitizedId);
    }
}
