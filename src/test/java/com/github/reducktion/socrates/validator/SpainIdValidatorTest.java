package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class SpainIdValidatorTest {

    private SpainIdValidator spainIdValidator;

    @BeforeEach
    void setup() {
        spainIdValidator = new SpainIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(spainIdValidator.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasMoreThan9Characters() {
        assertThat(spainIdValidator.validate("1234567890"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLessThan9Characters() {
        assertThat(spainIdValidator.validate("12345678"), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = { "843-456-42L", " Y3338121F ", "40298386V", "Y0597591L", "09730915Y" })
    void validate_shouldReturnTrue_whenControlCharacterMatches(final String validId) {
        assertThat(spainIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenControlCharacterDoesNotMatch() {
        assertThat(spainIdValidator.validate("05756786M"), is(false));
    }
}
