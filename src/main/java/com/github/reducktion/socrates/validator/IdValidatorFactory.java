package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.Country;

public class IdValidatorFactory {

    private IdValidatorFactory() {}

    public static IdValidator getValidator(final Country country) {
        if (Country.ES == country) {
            return new SpainIdValidator();
        }

        throw new UnsupportedOperationException("Country not supported.");
    }
}
