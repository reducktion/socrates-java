package com.github.reducktion.socrates.nationalid;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.extractor.Gender;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class BelgiumIdTest {

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        final BelgiumNationalId belgiumNationalId = new BelgiumNationalId(null);

        final boolean result = belgiumNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "123456789012",     // more than 11 digits
        "1234567890",       // less than 11 digits
        "5001010A156",      // not numeric
        "01.11.16-000.06",  // bad sequence number
        "01.13.16-001.19",  // month too high
        "01.11.31-001.40",  // November has 30 days
        "01.12.32-001.17",  // day too high
        "01.02.29-001.37",  // 29 February only exists in leap year
        "01.11.16-001.06"   // bad checksum
    })
    void validate_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final BelgiumNationalId belgiumNationalId = new BelgiumNationalId(notValidId);

        final boolean result = belgiumNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "01.11.16-001.05",
        "01 11 16-001 05",
        "01111600105",
        "01.11.16-001.34",  // same date & sequence but y2k
        "40.00.00-953.81",  // unknown month and day
        "40.00.01-001.33",  // unknown month
        "00.00.00-001.96"   // unknown month and day and y2k
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        final BelgiumNationalId belgiumNationalId = new BelgiumNationalId(validId);

        final boolean result = belgiumNationalId.isValid();

        assertThat(result, is(true));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1}")
    @MethodSource("citizenByIdProvider")
    void getCitizen_shouldReturnCorrectCitizenInfo_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final BelgiumNationalId belgiumNationalId = new BelgiumNationalId(id);

        final Citizen resultCitizen = belgiumNationalId.getCitizen();

        assertThat(resultCitizen, is(expectedCitizen));
    }

    private static List<Arguments> citizenByIdProvider() {
        return Arrays.asList(
            Arguments.arguments(
                "01.11.16-001.05",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1901)
                    .monthOfBirth(11)
                    .dayOfBirth(16)
                    .build()
            ),
            Arguments.arguments(
                "01.11.16-001.34",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(2001)
                    .monthOfBirth(11)
                    .dayOfBirth(16)
                    .build()
            ),
            Arguments.arguments(
                "40.00.00-954.80",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1940)
                    .build()
            ),
            Arguments.arguments(
                "40.00.01-001.33",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1940)
                    .dayOfBirth(1)
                    .build()
            )
        );
    }

    @Test
    void toString_shouldReturnIdString() {
        final String id = "01.11.16-001.05";
        final BelgiumNationalId belgiumNationalId = new BelgiumNationalId(id);

        final String result = belgiumNationalId.toString();

        assertThat(result, is(id));
    }
}
