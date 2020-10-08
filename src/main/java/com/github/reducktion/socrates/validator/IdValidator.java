package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.Country;

public interface IdValidator {

    boolean validate(String id);

    /**
     * Return a new instance of {@link IdValidator}, that is specific for the country parameter.
     *
     * @param country the {@link Country}
     * @return a new instance of {@link IdValidator}
     * @throws UnsupportedOperationException if the country is not supported
     */
    static IdValidator newInstance(final Country country) {
        switch (country) {
            case BE: return new BelgiumIdValidator();
            case BR: return new BrazilIdValidator();
            case CA: return new CanadaIdValidator();
            case FR: return new FranceIdValidator();
            case IT: return new ItalyIdValidator();
            case LU: return new LuxembourgIdValidator();
            case MX: return new MexicoIdValidator();
            case PT: return new PortugalIdValidator();
            case ES: return new SpainIdValidator();
            case US: return new UsaIdValidator();
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
