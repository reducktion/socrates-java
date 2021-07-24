package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.Citizen;

class UsaNationalIdTest {
    
    @Test
    void isValid_shouldReturnFalse_whenIdIsNull() {
        final UsaNationalId usaNationalId = new UsaNationalId(null);

        final boolean result = usaNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "12345678A",    // not numeric
        "1234567890",   // more than 9 digits
        "12345678",     // less than 9 digits
        "078051120",    // blacklisted
        "219099999",    // blacklisted
        "457555462",    // blacklisted
        "078051120"     // invalid area codes
    })
    void isValid_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final UsaNationalId usaNationalId = new UsaNationalId(notValidId);

        final boolean result = usaNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "167-38-1265",
        " 536228726 ",
        "536225232",
        "574227664",
        "671269121"
    })
    void isValid_shouldReturnTrue_whenIdIsValid(final String validId) {
        final UsaNationalId usaNationalId = new UsaNationalId(validId);

        final boolean result = usaNationalId.isValid();

        assertThat(result, is(true));
    }

    @Test
    void extractCitizen_shouldReturnEmpty() {
        final UsaNationalId usaNationalId = new UsaNationalId("671269121");

        final Optional<Citizen> extractedCitizen = usaNationalId.extractCitizen();

        assertThat(extractedCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "671269121";
        final UsaNationalId usaNationalId = new UsaNationalId(id);

        final String result = usaNationalId.toString();

        assertThat(result, is(id));
    }
}
