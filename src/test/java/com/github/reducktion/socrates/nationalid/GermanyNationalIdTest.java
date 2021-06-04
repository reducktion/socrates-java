package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Citizen;

class GermanyNationalIdTest {

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        final GermanyNationalId germanyNationalId = new GermanyNationalId(null);

        final boolean result = germanyNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "1234567890123",    // more than 11 digits
        "12345678901",      // less than 11 digits
        "12345678901A",     // not numeric
        "02476291358",      // has leading 0, which indicates a test id
        "44491234560",      // has more than 3 digits (in the IdNr) that are equal
        "11145678908"       // has 3 consecutive digits (in the IdNr) that are equal
    })
    void validate_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final GermanyNationalId germanyNationalId = new GermanyNationalId(notValidId);

        final boolean result = germanyNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "86095742719",
        "47036892816",
        "65929970489",
        "57549285017",
        "25768131411"
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        final GermanyNationalId germanyNationalId = new GermanyNationalId(validId);

        final boolean result = germanyNationalId.isValid();

        assertThat(result, is(true));
    }

    @Test
    void getCitizen_shouldReturnEmpty() {
        final GermanyNationalId germanyNationalId = new GermanyNationalId("25768131411");

        final Optional<Citizen> resultCitizen = germanyNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "25768131411";
        final GermanyNationalId germanyNationalId = new GermanyNationalId(id);

        final String result = germanyNationalId.toString();

        assertThat(result, is(id));
    }
}
