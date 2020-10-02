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
    void validate_shouldReturnFalse_whenIdHasLengthGreaterThanNine() {
        assertThat(spainIdValidator.validate("1234567890"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLengthLowerThanNine() {
        assertThat(spainIdValidator.validate("12345678"), is(false));
    }

    @Test
    void validate_shouldIgnoreDashesAndReturnTrue_whenControlCharacterMatches() {
        assertThat(spainIdValidator.validate("843-456-42L"), is(true));
    }

    @Test
    void validate_shouldIgnoreTrailingAndLeadingSpacesAndReturnTrue_whenControlCharacterMatches() {
        assertThat(spainIdValidator.validate(" 84345642L "), is(true));
    }

    @ParameterizedTest
    @ValueSource(strings = { "84345642L", "Y3338121F", "40298386V", "Y0597591L", "09730915Y" })
    void validate_shouldReturnTrue_whenControlCharacterMatches(final String validId) {
        assertThat(spainIdValidator.validate(validId), is(true));
    }

    @ParameterizedTest
    @ValueSource(strings = { "05756786M", "YY597522L", "4020X069V", "XX597591L", "A9730215Y" })
    void validate_shouldReturnFalse_whenControlCharacterDoesNotMatch(final String invalidId) {
        assertThat(spainIdValidator.validate(invalidId), is(false));
    }
}
