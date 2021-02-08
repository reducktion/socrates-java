package com.github.reducktion.socrates.id;

import com.github.reducktion.socrates.Country;

public final class NationalIdValidatorFactory {

    private NationalIdValidatorFactory() {}

    /**
     * Return a new instance of {@link IdValidator}, that is specific for the country parameter.
     *
     * @param id the national id
     * @param country the {@link Country}
     * @return a new instance of {@link IdValidator}
     * @throws UnsupportedOperationException if the country is not supported
     */
    static IdValidator getNationalIdValidator(final String id, final Country country) {
        switch (country) {
            case DK: return new DenmarkId(id);
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
