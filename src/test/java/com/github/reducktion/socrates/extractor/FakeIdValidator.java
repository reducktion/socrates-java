package com.github.reducktion.socrates.extractor;

import com.github.reducktion.socrates.validator.IdValidator;

/**
 * Fake ID validator that always returns the value that is passed in the constructor, when the method
 * {@link #validate(String)} is called. I.e. it doesn't actually validates the ID.
 */
public class FakeIdValidator implements IdValidator {

    private final boolean valueToBeReturned;

    /**
     * Fake ID validator constructor.
     *
     * @param valueToBeReturned the value to always be returned when the method {@link #validate(String)} is called.
     */
    public FakeIdValidator(final boolean valueToBeReturned) {
        this.valueToBeReturned = valueToBeReturned;
    }

    @Override
    public boolean validate(final String id) {
        return valueToBeReturned;
    }
}
