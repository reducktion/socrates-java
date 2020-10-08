package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DenmarkIdValidatorTest {

    private DenmarkIdValidator denmarkIdValidator;

    @BeforeEach
    void setup() {
        denmarkIdValidator = new DenmarkIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(denmarkIdValidator.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdNotNumeric() {
        assertThat(denmarkIdValidator.validate("123456789A"), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "12345678901",
        "123456789"
    })
    void validate_shouldReturnFalse_whenIdLengthIsNot10(final String idOfIncorrectLength) {
        assertThat(denmarkIdValidator.validate(idOfIncorrectLength), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "161301-001", // month too high
        "311101-001", // November has 30 days
        "321201-001", // day too high
        "290201-001", // 29 February only exists in leap year
    })
    void validate_shouldReturnFalse_whenDateOfBirthDoesNotExist(final String idWithNonExistingDateOfBirth) {
        assertThat(denmarkIdValidator.validate(idWithNonExistingDateOfBirth), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        " 090792-1395 ",
        "0705930600",
        "1504373068",
        "1608881995",
        "0404047094"
    })
    void validate_shouldReturnTrue_whenChecksumMatches(final String validId) {
        assertThat(denmarkIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenChecksumDoesNotMatch() {
        assertThat(denmarkIdValidator.validate("2343212454"), is(false));
    }
}
