package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MexicoValidatorTest {

    private MexicoIdValidator mexicoIdValidator;

    @BeforeEach
    void setup() {
        mexicoIdValidator = new MexicoIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(mexicoIdValidator.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasMoreThan18Characters() {
        assertThat(mexicoIdValidator.validate("1234567890123456789"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLessThan18Characters() {
        assertThat(mexicoIdValidator.validate("12345678901234567"), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {" AAIM901112MBCNMN08 ", "JOIM890106HHGSMN08", "JOTA950616HBCSWS03", "AAIT101109MHGNMN01" })
    void validate_shouldReturnTrue_whenControlDigitMatches(final String validId) {
        assertThat(mexicoIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenControlCharacterDoNotMatch() {
        assertThat(mexicoIdValidator.validate("BMHM260906HCHQAN04"), is(false));
    }
}
