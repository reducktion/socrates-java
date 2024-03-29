package com.github.reducktion.socrates.nationalid;

import com.github.reducktion.socrates.Country;

public final class NationalIdFactory {

    private NationalIdFactory() {}

    /**
     * Returns a new instance of {@link NationalId}, that is specific for the country parameter.
     *
     * @param id the national id
     * @param country the {@link Country}
     * @return a new instance of {@link NationalId}
     * @throws UnsupportedOperationException if the country is not supported
     */
    public static NationalId newInstance(final String id, final Country country) {
        switch (country) {
            case BE: return new BelgiumNationalId(id);
            case BR: return new BrazilNationalId(id);
            case CA: return new CanadaNationalId(id);
            case DE: return new GermanyNationalId(id);
            case DK: return new DenmarkNationalId(id);
            case ES: return new SpainNationalId(id);
            case FR: return new FranceNationalId(id);
            case IT: return new ItalyNationalId(id);
            case LU: return new LuxembourgNationalId(id);
            case MX: return new MexicoNationalId(id);
            case PT: return new PortugalNationalId(id);
            case US: return new UsaNationalId(id);
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
