package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.extractor.Gender;

class ItalyNationalIdTest {

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        final ItalyNationalId italyNationalId = new ItalyNationalId(null);

        final boolean result = italyNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "123456789012345",     // more than 16 digits
        "12345678901234567",   // less than 16 digits
        "MECDRE01A11A025E"    // bad control character
    })
    void validate_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final ItalyNationalId italyNationalId = new ItalyNationalId(notValidId);

        final boolean result = italyNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        " MRTMTT25D09F20 5Z ",
        "MLLSNT82P65Z404U",
        "DLMCTG75B07H227Y",
        "BRSLSE08D50H987B",
        "MRCDRALMAMPALSRE"
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        final ItalyNationalId italyNationalId = new ItalyNationalId(validId);

        final boolean result = italyNationalId.isValid();

        assertThat(result, is(true));
    }

    @ParameterizedTest(name = "#{index} - Test with Arguments={0},{1}")
    @MethodSource("citizenByIdProvider")
    void getCitizen_shouldReturnCorrectCitizenInfo_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final ItalyNationalId italyNationalId = new ItalyNationalId(id);

        final Citizen resultCitizen = italyNationalId.getCitizen();

        assertThat(resultCitizen, is(expectedCitizen));
    }

    private static List<Arguments> citizenByIdProvider() {
        return Arrays.asList(
            Arguments.arguments(
                "MRTMTT25D09F205Z",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1925)
                    .monthOfBirth(4)
                    .dayOfBirth(9)
                    .placeOfBirth("MILANO (MI)")
                    .build()
            ),
            Arguments.arguments(
                "MLLSNT82P65Z404U",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1982)
                    .monthOfBirth(9)
                    .dayOfBirth(25)
                    .placeOfBirth("STATI UNITI D'AMERICA")
                    .build()
            ),
            Arguments.arguments(
                "DLMCTG75B07H227Y",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1975)
                    .monthOfBirth(2)
                    .dayOfBirth(7)
                    .placeOfBirth("REINO (BN)")
                    .build()
            ),
            Arguments.arguments(
                "BRSLSE08D50H987B",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(2008)
                    .monthOfBirth(4)
                    .dayOfBirth(10)
                    .placeOfBirth("SAN MARTINO ALFIERI (AT)")
                    .build()
            ),
            Arguments.arguments(
                "MRCDRA01A13A065E",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(2001)
                    .monthOfBirth(1)
                    .dayOfBirth(13)
                    .placeOfBirth("AFRICO (RC)")
                    .build()
            ),
            Arguments.arguments(
                "MRCDRALMAMPALSRE",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(2001)
                    .monthOfBirth(1)
                    .dayOfBirth(13)
                    .placeOfBirth("AFRICO (RC)")
                    .build()
            )
        );
    }

    @Test
    void toString_shouldReturnIdString() {
        final String id = "MRCDRALMAMPALSRE";
        final ItalyNationalId italyNationalId = new ItalyNationalId(id);

        final String result = italyNationalId.toString();

        assertThat(result, is(id));
    }
}
