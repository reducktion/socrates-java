package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LuxembourgValidatorTest {

    private LuxembourgIdValidator luxembourgIdValidator;

    @BeforeEach
    void setup() {
        luxembourgIdValidator = new LuxembourgIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(luxembourgIdValidator.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasAlphaCharacters() {
        assertThat(luxembourgIdValidator.validate("12345678901AB"), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "123456789012",
        "12345678901234"
    })
    void validate_shouldReturnFalse_whenIdLengthIsNot13(final String idOfInvalidLength) {
        assertThat(luxembourgIdValidator.validate(idOfInvalidLength), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "198-308-124-6785",
        " 2003042581939 ",
        "1971110258749",
        "2012051469331",
        "1994092874550"
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        assertThat(luxembourgIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsValid() {
        assertThat(luxembourgIdValidator.validate("1994789587182"), is(false));
    }
}
