package com.github.reducktion.socrates.extractor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Gender;

class FranceCitizenExtractorTest {

    private FranceCitizenExtractor franceCitizenExtractor;

    @BeforeEach
    void setup() {
        franceCitizenExtractor = new FranceCitizenExtractor();
    }

    @Test
    void extractFromId_shouldReturnEmptyOptional_whenIdIsInvalid() {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(false);

        final Optional<Citizen> citizen = franceCitizenExtractor.extractFromId("123456789012345", fakeIdValidator);

        assertThat(citizen, is(Optional.empty()));
    }

    @ParameterizedTest
    @MethodSource("citizensForIds")
    void extractFromId_shouldReturnCorrectCitizenData_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(true);

        final Optional<Citizen> resultCitizen = franceCitizenExtractor.extractFromId(id, fakeIdValidator);

        assertThat(resultCitizen.get(), is(expectedCitizen));
    }

    private static List<Arguments> citizensForIds() {
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
}
