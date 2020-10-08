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

    @ParameterizedTest
    @ValueSource(strings = {
        "12345678901234",
        "1234567890123456"
    })
    void validate_shouldReturnFalse_whenIdLengthIsNot15(final String idOfIncorrectLength) {
        assertThat(franceIdValidator.validate(idOfIncorrectLength), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasAlphaCharacters() {
        assertThat(franceIdValidator.validate("12345678901234A"), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        " 2820819398814 09 ",
        "238108021456811",
        "188085870457157",
        "182089740115475",
        "199072A22807010"
    })
    void validate_shouldReturnTrue_whenControlDigitMatches(final String validId) {
        assertThat(franceIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenControlDigitDoesNotMatch() {
        assertThat(franceIdValidator.validate("103162989566972"), is(false));
    }
}
