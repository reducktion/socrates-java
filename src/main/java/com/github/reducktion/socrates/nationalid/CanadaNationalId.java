package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.internal.LuhnAlgorithm;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Id for Canada.
 *
 * Information about this national id can be found at:
 *  - https://en.wikipedia.org/wiki/Social_Insurance_Number
 */
final class CanadaNationalId extends NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;

    public CanadaNationalId(final String id) {
        super(id);
    }

    @Override
    public boolean isValid() {
        return sanitizedId != null
            && sanitizedId.length() == ID_NUMBER_OF_CHARACTERS
            && StringUtils.isNumeric(sanitizedId)
            && hasValidSequence();
    }

    private boolean hasValidSequence() {
        return LuhnAlgorithm.validate(sanitizedId);
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        return Optional.empty();
    }
}
