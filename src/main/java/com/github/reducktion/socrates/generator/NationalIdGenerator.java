package com.github.reducktion.socrates.generator;

import com.github.reducktion.socrates.Citizen;

public interface NationalIdGenerator {

    /**
     * Generates a national id based on the {@link Citizen} information provided.
     */
    String generate(final Citizen citizen);
}
