package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UsaIdValidatorTest {

    private UsaIdValidator usaIdValidator;

    @BeforeEach
    void setup() {
        usaIdValidator = new UsaIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(usaIdValidator.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasMoreThan9Characters() {
        assertThat(usaIdValidator.validate("1234567890"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLessThan9Characters() {
        assertThat(usaIdValidator.validate("12345678"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasAlphaCharacters() {
        assertThat(usaIdValidator.validate("12345678A"), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = { "078051120", "219099999", "457555462" })
    void validate_shouldReturnFalse_whenIdIsBlacklisted(final String validId) {
        assertThat(usaIdValidator.validate(validId), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = { "167-38-1265", " 536228726 ", "536225232", "574227664", "671269121" })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        assertThat(usaIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsInvalid() {
        assertThat(usaIdValidator.validate("078051120"), is(false));
    }
}
