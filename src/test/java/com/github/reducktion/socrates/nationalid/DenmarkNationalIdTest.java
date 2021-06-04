package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.extractor.Gender;

class DenmarkNationalIdTest {

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        final DenmarkNationalId denmarkNationalId = new DenmarkNationalId(null);

        final boolean result = denmarkNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "123456789A",  // not numeric
        "12345678901", // more than 10 digits
        "123456789",   // less than 10 digits
        "161301-0001", // month too high
        "311101-0001", // November has 30 days
        "321201-0001", // day too high
        "290201-0001", // 29 February only exists in leap year
        "2343212454"   // bad checksum
    })
    void validate_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final DenmarkNationalId denmarkNationalId = new DenmarkNationalId(notValidId);

        final boolean result = denmarkNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        " 090792-1395 ",
        "0705930600",
        "1504373068",
        "1608881995",
        "0404047094"
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        final DenmarkNationalId denmarkNationalId = new DenmarkNationalId(validId);

        final boolean result = denmarkNationalId.isValid();

        assertThat(result, is(true));
    }

    @ParameterizedTest(name = "#{index} - Test with Arguments={0},{1}")
    @MethodSource("citizenByIdProvider")
    void getCitizen_shouldReturnCorrectCitizenInfo_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final DenmarkNationalId denmarkNationalId = new DenmarkNationalId(id);

        final Optional<Citizen> resultCitizen = denmarkNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.of(expectedCitizen)));
    }

    private static List<Arguments> citizenByIdProvider() {
        return Arrays.asList(
            Arguments.arguments(
                "090792-1395",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1992)
                    .monthOfBirth(7)
                    .dayOfBirth(9)
                    .build()
            ),
            Arguments.arguments(
                "070593-0600",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1993)
                    .monthOfBirth(5)
                    .dayOfBirth(7)
                    .build()
            ),
            Arguments.arguments(
                "150437-3068",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1937)
                    .monthOfBirth(4)
                    .dayOfBirth(15)
                    .build()
            ),
            Arguments.arguments(
                "160888-1995",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1988)
                    .monthOfBirth(8)
                    .dayOfBirth(16)
                    .build()
            ),
            Arguments.arguments(
                "040404-7094",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(2004)
                    .monthOfBirth(4)
                    .dayOfBirth(4)
                    .build()
            )
        );
    }

    @Test
    void getCitizen_shouldReturnEmpty_whenIdIsNotValid() {
        final DenmarkNationalId denmarkNationalId = new DenmarkNationalId(null);

        final Optional<Citizen> resultCitizen = denmarkNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "0404047094";
        final DenmarkNationalId denmarkNationalId = new DenmarkNationalId(id);

        final String result = denmarkNationalId.toString();

        assertThat(result, is(id));
    }
}
