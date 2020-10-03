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

    @Test
    void validate_shouldReturnFalse_whenIdHasLengthGreaterThanTwelve() {
        assertThat(portugalIdValidator.validate("1234567890123"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLengthLowerThanTwelve() {
        assertThat(portugalIdValidator.validate("12345678901"), is(false));
    }

    @Test
    void validate_shouldIgnoreSpacesAndReturnTrue_whenIdIsValid() {
        assertThat(portugalIdValidator.validate("11084129 8 ZX8"), is(true));
    }

    @Test
    void validate_shouldIgnoreTrailingAndLeadingSpacesAndReturnTrue_whenIdIsValid() {
        assertThat(portugalIdValidator.validate(" 110841298ZX8 "), is(true));
    }

    @ParameterizedTest
    @ValueSource(strings = { "154203556ZX9", "176539174ZZ5", "174886721ZX1", "148984754ZY5" })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        assertThat(portugalIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsInvalid() {
        assertThat(portugalIdValidator.validate("154A03556ZX9"), is(false));
    }
}
