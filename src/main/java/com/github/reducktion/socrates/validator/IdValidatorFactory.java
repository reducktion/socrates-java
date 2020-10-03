package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.Country;

public class IdValidatorFactory {

    private IdValidatorFactory() {}

    public static IdValidator getValidator(final Country country) {
        switch (country) {
            case ES: return new SpainIdValidator();
            case PT: return new PortugalIdValidator();
            case FR: return new FranceIdValidator();
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
