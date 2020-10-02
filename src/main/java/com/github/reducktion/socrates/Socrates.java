package com.github.reducktion.socrates;

import com.github.reducktion.socrates.validator.IdValidator;
import com.github.reducktion.socrates.validator.IdValidatorFactory;

/**
 * Socrates allows you to validate and retrieve personal data from National Identification Numbers across the world.
 */
public class Socrates {

    /**
     * Validate the National Identification Number.
     *
     * @param id the personal identification number
     * @param country the country of the personal identification number
     * @return true if the personal identification number is valid, false otherwise
     * @throws UnsupportedOperationException if the country is not supported
     */
    public boolean validateId(final String id, final Country country) {
        final IdValidator idValidator = IdValidatorFactory.getValidator(country);
        return idValidator.validate(id);
    }
}
