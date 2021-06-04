package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Citizen;

class SpainNationalIdTest {

    @Test
    void isValid_shouldReturnFalse_whenIdIsNull() {
        final SpainNationalId spainNationalId = new SpainNationalId(null);

        final boolean result = spainNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "1234567890",   // more than 9 digits
        "12345678",     // less than 9 digits
        "12345678A",    // not numeric
        "05756786M"     // invalid control character
    })
    void isValid_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final SpainNationalId spainNationalId = new SpainNationalId(notValidId);

        final boolean result = spainNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "843-456-42L",
        " Y3338121F ",
        "40298386V",
        "Y0597591L",
        "09730915Y"
    })
    void isValid_shouldReturnTrue_whenIdIsValid(final String validId) {
        final SpainNationalId spainNationalId = new SpainNationalId(validId);

        final boolean result = spainNationalId.isValid();

        assertThat(result, is(true));
    }

    @Test
    void getCitizen_shouldReturnEmpty() {
        final SpainNationalId spainNationalId = new SpainNationalId("09730915Y");

        final Optional<Citizen> resultCitizen = spainNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "09730915Y";
        final SpainNationalId spainNationalId = new SpainNationalId(id);

        final String result = spainNationalId.toString();

        assertThat(result, is(id));
    }
}
