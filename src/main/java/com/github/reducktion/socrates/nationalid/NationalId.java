package com.github.reducktion.socrates.nationalid;

import java.util.Objects;
import java.util.Optional;

import com.github.reducktion.socrates.Citizen;

public abstract class NationalId {

    private final String id;
    protected final String sanitizedId;

    public NationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null ? null : id.replaceAll("[. -]+", "").toUpperCase();
    }

    public abstract boolean isValid();

    /**
     * Extracts the citizen information present in the national id.
     *
     * @return a valid {@link Citizen} if {@link #isValid()} returns {@code true}, empty otherwise. It can also return
     *      empty if it is impossible to extract the citizen information from the national id or if the feature is not
     *      implemented yet.
     */
    public abstract Optional<Citizen> extractCitizen();

    @Override
    public final String toString() {
        return id == null ? null : id.trim();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof NationalId)) {
            return false;
        }

        final NationalId nationalId = (NationalId) obj;
        return Objects.equals(nationalId.sanitizedId, sanitizedId);
    }

    @Override
    public final int hashCode() {
        return Objects.hashCode(sanitizedId);
    }
}
