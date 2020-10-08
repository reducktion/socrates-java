package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ItalyIdValidatorTest {

    private ItalyIdValidator italyIdValidator;

    @BeforeEach
    void setup() {
        italyIdValidator = new ItalyIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(italyIdValidator.validate(null), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "123456789012345",
        "12345678901234567"
    })
    void validate_shouldReturnFalse_whenIdLengthIsNot16(final String idOfInvalidLength) {
        assertThat(italyIdValidator.validate(idOfInvalidLength), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        " MRTMTT25D09F20 5Z ",
        "MLLSNT82P65Z404U",
        "DLMCTG75B07H227Y",
        "BRSLSE08D50H987B",
        "MRCDRALMAMPALSRE"
    })
    void validate_shouldReturnTrue_whenControlCharacterMatches(final String validId) {
        assertThat(italyIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenControlCharacterDoesNotMatch() {
        assertThat(italyIdValidator.validate("MECDRE01A11A025E"), is(false));
    }
}
