package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.internal.LuhnAlgorithm;

class PortugalNationalId implements NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 12;
    private static final int BASE_36_RADIX = 36;

    private final String id;
    private final String sanitizedId;

    public PortugalNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null ? null : id.replace(" ", "").toUpperCase();
    }

    @Override
    public boolean isValid() {
        return sanitizedId != null
            && sanitizedId.length() == ID_NUMBER_OF_CHARACTERS
            && LuhnAlgorithm.validate(sanitizedId, BASE_36_RADIX);
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
