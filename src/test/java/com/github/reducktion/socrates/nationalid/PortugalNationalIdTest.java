package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Citizen;

class PortugalNationalIdTest {

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        final PortugalNationalId portugalNationalId = new PortugalNationalId(null);

        final boolean result = portugalNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "1234567890123",    // more than 12 digits
        "12345678901",      // less than 12 digits
        "154A03556ZX9"      // Luhn's algorithm fails
    })
    void validate_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final PortugalNationalId portugalNationalId = new PortugalNationalId(notValidId);

        final boolean result = portugalNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        " 11084129 8 ZX8 ",
        "154203556ZX9",
        "176539174ZZ5",
        "174886721ZX1",
        "148984754ZY5"
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        final PortugalNationalId portugalNationalId = new PortugalNationalId(validId);

        final boolean result = portugalNationalId.isValid();

        assertThat(result, is(true));
    }

    @Test
    void getCitizen_shouldReturnEmpty() {
        final PortugalNationalId portugalNationalId = new PortugalNationalId("148984754ZY5");

        final Optional<Citizen> resultCitizen = portugalNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "148984754ZY5";
        final PortugalNationalId portugalNationalId = new PortugalNationalId(id);

        final String result = portugalNationalId.toString();

        assertThat(result, is(id));
    }
}
