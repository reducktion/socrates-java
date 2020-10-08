package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BelgiumIdValidatorTest {

    private static BelgiumIdValidator belgiumIdValidator;

    @BeforeAll
    static void setup() {
        belgiumIdValidator = new BelgiumIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(belgiumIdValidator.validate(null), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "1234567890",
        "123456789012"
    })
    void validate_shouldReturnFalse_whenIdLengthIsNot11(final String idOfIncorrectLength) {
        assertThat(belgiumIdValidator.validate(idOfIncorrectLength), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdNotNumeric() {
        assertThat(belgiumIdValidator.validate("5001010A156"), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "01.11.16-000.06",
        "01.11.16-999.74"
    })
    void validate_shouldReturnFalse_whenSequenceNumberInvalid(final String idWithInvalidSequenceNumber) {
        assertThat(belgiumIdValidator.validate(idWithInvalidSequenceNumber), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "01.13.16-001.19", // month too high
        "01.11.31-001.40", // November has 30 days
        "01.12.32-001.17", // day too high
        "01.02.29-001.37", // 29 February only exists in leap year
    })
    void validate_shouldReturnFalse_whenDateOfBirthDoesNotExist(final String idWithNonExistingDateOfBirth) {
        assertThat(belgiumIdValidator.validate(idWithNonExistingDateOfBirth), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "01.11.16-001.06",
        "01.11.16-001.35"
    })
    void validate_shouldReturnFalse_whenChecksumIsInvalid(final String idWithInvalidChecksum) {
        assertThat(belgiumIdValidator.validate(idWithInvalidChecksum), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "01.11.16-001.05",
        "01 11 16-001 05",
        "01111600105",
        "01.11.16-001.34" // same date & sequence but y2k
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        assertThat(belgiumIdValidator.validate(validId), is(true));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "40.00.00-953.81",
        "40.00.01-001.33",
        "00.00.00-001.96"
    })
    void validate_shouldReturnTrue_whenIdIsHasUnknownMonthOrDayButIsValid(final String validId) {
        assertThat(belgiumIdValidator.validate(validId), is(true));
    }
}
