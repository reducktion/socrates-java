package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.Country;

public class IdValidatorFactory {

    private IdValidatorFactory() {}

    public static IdValidator getValidator(final Country country) {
        switch (country) {
            case FR: return new FranceIdValidator();
            case IT: return new ItalyIdValidator();
            case PT: return new PortugalIdValidator();
            case ES: return new SpainIdValidator();
            case US: return new USAIdValidator();
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
