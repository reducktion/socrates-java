package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.Citizen;

public interface NationalId {

    boolean isValid();

    /**
     * Extracts the citizen information present in the national id.
     *
     * @return a valid {@link Citizen} if {@link #isValid()} returns {@code true}, empty otherwise. It can also return
     *      empty if it is impossible to extract the citizen information from the national id or if the feature is not
     *      implemented yet.
     */
    Optional<Citizen> extractCitizen();
}
