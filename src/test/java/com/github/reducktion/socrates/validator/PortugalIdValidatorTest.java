package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PortugalIdValidatorTest {

    private PortugalIdValidator portugalIdValidator;

    @BeforeEach
    void setup() {
        portugalIdValidator = new PortugalIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(portugalIdValidator.validate(null), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "12345678901",
        "1234567890123"
    })
    void validate_shouldReturnFalse_whenIdLengthIsNot12(final String idOfIncorrectLength) {
        assertThat(portugalIdValidator.validate(idOfIncorrectLength), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        " 11084129 8 ZX8 ",
        "154203556ZX9",
        "176539174ZZ5",
        "174886721ZX1",
        "148984754ZY5"
    })
    void validate_shouldReturnTrue_whenLuhnAlgorithmSucceeds(final String validId) {
        assertThat(portugalIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenLuhnAlgorithmFails() {
        assertThat(portugalIdValidator.validate("154A03556ZX9"), is(false));
    }
}
