package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.internal.LuhnAlgorithm;
import com.github.reducktion.socrates.internal.StringUtils;
import com.github.reducktion.socrates.internal.VerhoeffAlgorithm;

/**
 * National Identification Number validator for Luxembourg.
 *
 * This validation algorithm is based on this pdf: https://www.oecd.org/tax/automatic-exchange/crs-implementation-and-assistance/tax-identification-numbers/Luxembourg-TIN.pdf
 */
class LuxembourgIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 13;

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS || !StringUtils.isNumeric(sanitizedId)) {
            return false;
        }

        final int luhnCheckDigit = Integer.parseInt(sanitizedId.substring(11, 12));
        final int verhoeffCheckDigit = Integer.parseInt(sanitizedId.substring(12, 13));
        final String partialId = sanitizedId.substring(0, sanitizedId.length() - 2);

        return luhnCheckDigit == LuhnAlgorithm.computeCheckDigit(partialId).get()
            && verhoeffCheckDigit == VerhoeffAlgorithm.computeCheckDigit(partialId).get();
    }

    private String sanitize(final String id) {
        return id.replaceAll("[ -]", "");
    }
}
