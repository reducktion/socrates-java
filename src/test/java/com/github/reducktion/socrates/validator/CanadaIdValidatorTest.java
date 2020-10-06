package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CanadaIdValidatorTest {
    private CanadaIdValidator canadaIdValidator;

    @BeforeEach
    void setup() {
        canadaIdValidator = new CanadaIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {

        assertThat(canadaIdValidator.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasMoreThan9Characters() {
        assertThat(canadaIdValidator.validate("1234567890"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLessThan9Characters() {
        assertThat(canadaIdValidator.validate("12345678"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasAlphaCharacters() {
        assertThat(canadaIdValidator.validate("12345678A"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenLuhnValidationFails() {
        assertThat(canadaIdValidator.validate("046 454 287"), is(false));
    }

    @Test
    void validate_shouldReturnTrue_whenIdHasSpaces() {
        assertThat(canadaIdValidator.validate("046 454 286"), is(true));
    }

    @Test
    void validate_shouldReturnTrue_whenIdHasNoSpaces() {
        assertThat(canadaIdValidator.validate("046 454 286"), is(true));
    }

}
