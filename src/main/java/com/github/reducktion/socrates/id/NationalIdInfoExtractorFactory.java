package com.github.reducktion.socrates.id;

import com.github.reducktion.socrates.Country;

public final class NationalIdInfoExtractorFactory {

    private NationalIdInfoExtractorFactory() {}

    /**
     * Return a new instance of {@link IdInfoExtractor}, that is specific for the country parameter.
     *
     * @param id the national id
     * @param country the {@link Country}
     * @return a new instance of {@link IdInfoExtractor}
     * @throws UnsupportedOperationException if the country is not supported
     */
    static IdInfoExtractor getNationalIdInfoExtractor(final String id, final Country country) {
        switch (country) {
            case DK: return new DenmarkId(id);
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
