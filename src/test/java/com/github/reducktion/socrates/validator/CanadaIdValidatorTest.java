package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @ParameterizedTest
    @ValueSource(strings = {
        "12345678",
        "1234567890"
    })
    void validate_shouldReturnFalse_whenIdLengthIsNot9(final String idOfInvalidLength) {
        assertThat(canadaIdValidator.validate(idOfInvalidLength), is(false));
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
        assertThat(canadaIdValidator.validate("046454286"), is(true));
    }

}
