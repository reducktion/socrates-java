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
            Arguments.arguments("2820819398814 09", new Citizen("F", 1982, 8, "Corrèze")),
            Arguments.arguments("1350455179061 16", new Citizen("M", 1935, 4, "Meuse")),
            Arguments.arguments("2381080214568 11", new Citizen("F", 1938, 10, "Somme")),
            Arguments.arguments("1880858704571 57", new Citizen("M", 1988, 8, "Nièvre")),
            Arguments.arguments("1030307795669 72", new Citizen("M", 2003, 3, "Ardèche")),
            Arguments.arguments("1820897401154 75", new Citizen("M", 1982, 8, "La Réunion")),
            Arguments.arguments("2041098718061 61", new Citizen("F", 2004, 10, "Polynésie française")),
            Arguments.arguments("1103442505781 11", new Citizen("M", 2010, 4, "Loire")),
            Arguments.arguments("2115028242370 20", new Citizen("F", 2011, null, "Eure-et-Loir")),
            Arguments.arguments("199072A228070 10", new Citizen("M", 1999, 7, "Corse-du-Sud")),
            Arguments.arguments("257092B844458 87", new Citizen("F", 1957, 9, "Haute-Corse"))
        );
    }
}
