package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.Citizen;

class LuxembourgNationalIdTest {

    @Test
    void isValid_shouldReturnFalse_whenIdIsNull() {
        final LuxembourgNationalId luxembourgNationalId = new LuxembourgNationalId(null);

        final boolean result = luxembourgNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "12345678901AB",    // not numeric
        "12345678901234",   // more than 13 digits
        "123456789012",     // less than 13 digits
        "1994789587182"     // bad check digit
    })
    void isValid_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final LuxembourgNationalId luxembourgNationalId = new LuxembourgNationalId(notValidId);

        final boolean result = luxembourgNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "198-308-124-6785",
        " 2003042581939 ",
        "1971110258749",
        "2012051469331",
        "1994092874550"
    })
    void isValid_shouldReturnTrue_whenIdIsValid(final String validId) {
        final LuxembourgNationalId luxembourgNationalId = new LuxembourgNationalId(validId);

        final boolean result = luxembourgNationalId.isValid();

        assertThat(result, is(true));
    }

    @Test
    void extractCitizen_shouldReturnEmpty() {
        final LuxembourgNationalId luxembourgNationalId = new LuxembourgNationalId("1994092874550");

        final Optional<Citizen> extractedCitizen = luxembourgNationalId.extractCitizen();

        assertThat(extractedCitizen, is(Optional.empty()));
    }
}
