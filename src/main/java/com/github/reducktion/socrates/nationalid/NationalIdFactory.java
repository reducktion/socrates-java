package com.github.reducktion.socrates.nationalid;

import com.github.reducktion.socrates.Country;

public final class NationalIdFactory {

    private NationalIdFactory() {}

    /**
     * Return a new instance of {@link NationalId}, that is specific for the country parameter.
     *
     * @param id the national id
     * @param country the {@link Country}
     * @return a new instance of {@link NationalId}
     * @throws UnsupportedOperationException if the country is not supported
     */
    static NationalId getNationalId(final String id, final Country country) {
        switch (country) {
            case BE: return new BelgiumNationalId(id);
            case DK: return new DenmarkNationalId(id);
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
