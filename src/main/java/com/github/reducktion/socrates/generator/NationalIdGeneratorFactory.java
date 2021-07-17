package com.github.reducktion.socrates.generator;

import com.github.reducktion.socrates.Country;

public final class NationalIdGeneratorFactory {

    private NationalIdGeneratorFactory() {}

    /**
     * Return a new instance of {@link NationalIdGenerator}, that is specific for the country parameter.
     *
     * @throws UnsupportedOperationException if the country is not supported
     */
    public static NationalIdGenerator newInstance(final Country country) {
        switch (country) {
            case DK: return new DenmarkNationalIdGenerator();
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
