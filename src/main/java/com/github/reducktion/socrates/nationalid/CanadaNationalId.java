package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.internal.LuhnAlgorithm;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Id for Canada.
 *
 * Information about this national id can be found at:
 *  - https://en.wikipedia.org/wiki/Social_Insurance_Number
 */
class CanadaNationalId implements NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;

    private final String id;
    private final String sanitizedId;

    public CanadaNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null ? null : id.replace(" ", "").replace("-", "");
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

    @Override
    public String toString() {
        return id;
    }
}
