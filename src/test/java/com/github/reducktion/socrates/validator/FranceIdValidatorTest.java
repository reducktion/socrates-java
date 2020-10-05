package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FranceIdValidatorTest {

    private FranceIdValidator franceIdValidator;

    @BeforeEach
    void setup() {
        franceIdValidator = new FranceIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(franceIdValidator.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasMoreThan15Characters() {
        assertThat(franceIdValidator.validate("1234567890123456"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLessThan15Characters() {
        assertThat(franceIdValidator.validate("12345678901234"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasAlpha() {
        assertThat(franceIdValidator.validate("12345678901234A"), is(false));
    }

    @Test
    void validate_shouldIgnoreSpacesAndReturnTrue_whenIdIsValid() {
        assertThat(franceIdValidator.validate(" 2820819398814 09 "), is(true));
    }

    @ParameterizedTest
    @ValueSource(strings = { "238108021456811", "188085870457157", "182089740115475", "199072A22807010" })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        assertThat(franceIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsInvalid() {
        assertThat(franceIdValidator.validate("103162989566972"), is(false));
    }
}
