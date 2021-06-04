package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.internal.LuhnAlgorithm;
import com.github.reducktion.socrates.internal.StringUtils;
import com.github.reducktion.socrates.internal.VerhoeffAlgorithm;

/**
 * National Id for Luxembourg.
 *
 * Information about this national id can be found at:
 *  - https://www.oecd.org/tax/automatic-exchange/crs-implementation-and-assistance/tax-identification-numbers/Luxembourg-TIN.pdf
 */
class LuxembourgNationalId implements NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 13;

    private final String id;
    private final String sanitizedId;

    public LuxembourgNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null ? null : id.replaceAll("[ -]", "");
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
    public Optional<Citizen> getCitizen() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return id;
    }
}
