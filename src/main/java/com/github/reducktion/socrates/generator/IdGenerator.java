package com.github.reducktion.socrates.generator;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Country;

/**
 * Used to generate a national id based on the {@link Citizen} information provided.
 *
 * @deprecated Use class {@link NationalIdGenerator} instead
 */
@Deprecated
public interface IdGenerator {

    /**
     * Generates an identifier based on the {@link Citizen} information provided.
     */
    String generate(final Citizen citizen);

    /**
     * Return a new instance of {@link IdGenerator}, that is specific for the country parameter.
     *
     * @throws UnsupportedOperationException if the country is not supported
     */
    static IdGenerator newInstance(final Country country) {
        switch (country) {
            case DK: return new DenmarkNationalIdGenerator();
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
