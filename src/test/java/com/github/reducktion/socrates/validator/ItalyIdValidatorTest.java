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

    @Test
    void validate_shouldReturnFalse_whenIdHasLengthGreaterThanSixteen() {
        assertThat(italyIdValidator.validate("12345678901234567"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLengthLowerThanSixteen() {
        assertThat(italyIdValidator.validate("123456789012345"), is(false));
    }

    @Test
    void validate_shouldIgnoreSpacesAndReturnTrue_whenIdIsValid() {
        assertThat(italyIdValidator.validate(" MRTMTT25D09F20 5Z "), is(true));
    }

    @ParameterizedTest
    @ValueSource(strings = { "MLLSNT82P65Z404U", "DLMCTG75B07H227Y", "BRSLSE08D50H987B", "MRCDRA01A13A065E" })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        assertThat(italyIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsInvalid() {
        assertThat(italyIdValidator.validate("MECDRE01A11A025E"), is(false));
    }
}
