package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.internal.LuhnAlgorithm;
import com.github.reducktion.socrates.internal.StringUtils;
import com.github.reducktion.socrates.internal.VerhoeffAlgorithm;

/**
 * National Id for Luxembourg.
 *
 * Information about this national id can be found at:
 *  - https://www.oecd.org/tax/automatic-exchange/crs-implementation-and-assistance/tax-identification-numbers/Luxembourg-TIN.pdf
 */
final class LuxembourgNationalId extends NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 13;

    public LuxembourgNationalId(final String id) {
        super(id);
    }

    @Override
    public boolean isValid() {
        if (sanitizedId == null
            || sanitizedId.length() != ID_NUMBER_OF_CHARACTERS
            || !StringUtils.isNumeric(sanitizedId)
        ) {
            return false;
        }

        final int luhnCheckDigit = Integer.parseInt(sanitizedId.substring(11, 12));
        final int verhoeffCheckDigit = Integer.parseInt(sanitizedId.substring(12, 13));
        final String partialId = sanitizedId.substring(0, sanitizedId.length() - 2);

        return luhnCheckDigit == LuhnAlgorithm.computeCheckDigit(partialId).get()
            && verhoeffCheckDigit == VerhoeffAlgorithm.computeCheckDigit(partialId).get();
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        return Optional.empty();
    }
}
