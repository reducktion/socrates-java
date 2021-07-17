package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Citizen;

class CanadaNationalIdTest {

    @Test
    void isValid_shouldReturnFalse_whenIdIsNull() {
        final CanadaNationalId canadaNationalId = new CanadaNationalId(null);

        final boolean result = canadaNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "12345678A",    // not numeric
        "1234567890",   // more than 9 digits
        "12345678",     // less than 9 digits
        "046 454 287"   // Luhn's algorithm fails
    })
    void isValid_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final CanadaNationalId canadaNationalId = new CanadaNationalId(notValidId);

        final boolean result = canadaNationalId.isValid();

        assertThat(result, is(false));
    }

    @Test
    void isValid_shouldReturnTrue_whenIdIsValid() {
        final CanadaNationalId canadaNationalId = new CanadaNationalId("046 454 286");

        final boolean result = canadaNationalId.isValid();

        assertThat(result, is(true));
    }

    @Test
    void extractCitizen_shouldReturnEmpty() {
        final CanadaNationalId canadaNationalId = new CanadaNationalId("046454286");

        final Optional<Citizen> extractedCitizen = canadaNationalId.extractCitizen();

        assertThat(extractedCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "046454286";
        final CanadaNationalId canadaNationalId = new CanadaNationalId(id);

        final String result = canadaNationalId.toString();

        assertThat(result, is(id));
    }
}
