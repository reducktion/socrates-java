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

class FranceNationalIdTest {

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        final FranceNationalId franceNationalId = new FranceNationalId(null);

        final boolean result = franceNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "1234567890123456", // more than 15 digits
        "12345678901234",   // less than 15 digits
        "12345678901234A",  // has alpha characters in the wrong position
        "103162989566972"   // bad control digit
    })
    void validate_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final FranceNationalId franceNationalId = new FranceNationalId(notValidId);

        final boolean result = franceNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        " 2820819398814 09 ",
        "238108021456811",
        "188085870457157",
        "182089740115475",
        "199072A22807010"
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        final FranceNationalId franceNationalId = new FranceNationalId(validId);

        final boolean result = franceNationalId.isValid();

        assertThat(result, is(true));
    }

    @ParameterizedTest(name = "#{index} - Test with Arguments={0},{1}")
    @MethodSource("citizenByIdProvider")
    void getCitizen_shouldReturnCorrectCitizenInfo_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final FranceNationalId franceNationalId = new FranceNationalId(id);

        final  Optional<Citizen> resultCitizen = franceNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.of(expectedCitizen)));
    }

    private static List<Arguments> citizenByIdProvider() {
        return Arrays.asList(
            Arguments.arguments(
                "2820819398814 09",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1982)
                    .monthOfBirth(8)
                    .placeOfBirth("Corrèze")
                    .build()
            ),
            Arguments.arguments(
                "1350455179061 16",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1935)
                    .monthOfBirth(4)
                    .placeOfBirth("Meuse")
                    .build()
            ),
            Arguments.arguments(
                "2381080214568 11",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1938)
                    .monthOfBirth(10)
                    .placeOfBirth("Somme")
                    .build()
            ),
            Arguments.arguments(
                "1880858704571 57",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1988)
                    .monthOfBirth(8)
                    .placeOfBirth("Nièvre")
                    .build()
            ),
            Arguments.arguments(
                "1030307795669 72",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(2003)
                    .monthOfBirth(3)
                    .placeOfBirth("Ardèche")
                    .build()
            ),
            Arguments.arguments(
                "1820897401154 75",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1982)
                    .monthOfBirth(8)
                    .placeOfBirth("La Réunion")
                    .build()
            ),
            Arguments.arguments(
                "2041098718061 61",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(2004)
                    .monthOfBirth(10)
                    .placeOfBirth("Polynésie française")
                    .build()
            ),
            Arguments.arguments(
                "1103442505781 11",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(2010)
                    .monthOfBirth(4)
                    .placeOfBirth("Loire")
                    .build()
            ),
            Arguments.arguments(
                "2115028242370 20",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(2011)
                    .placeOfBirth("Eure-et-Loir")
                    .build()
            ),
            Arguments.arguments(
                "199072A228070 10",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1999)
                    .monthOfBirth(7)
                    .placeOfBirth("Corse-du-Sud")
                    .build()
            ),
            Arguments.arguments(
                "257092B844458 87",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1957)
                    .monthOfBirth(9)
                    .placeOfBirth("Haute-Corse")
                    .build()
            )
        );
    }

    @Test
    void getCitizen_shouldReturnEmpty_whenIdIsNotValid() {
        final FranceNationalId franceNationalId = new FranceNationalId(null);

        final Optional<Citizen> resultCitizen = franceNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "2820819398814 09";
        final FranceNationalId franceNationalId = new FranceNationalId(id);

        final String result = franceNationalId.toString();

        assertThat(result, is(id));
    }
}
