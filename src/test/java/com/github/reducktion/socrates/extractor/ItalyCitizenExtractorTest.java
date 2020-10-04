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

class ItalyCitizenExtractorTest {

    private ItalyCitizenExtractor italyCitizenExtractor;

    @BeforeEach
    void setup() {
        italyCitizenExtractor = new ItalyCitizenExtractor();
    }

    @Test
    void extractFromId_shouldReturnEmptyOptional_whenIdIsInvalid() {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(false);

        final Optional<Citizen> citizen = italyCitizenExtractor.extractFromId("1234567890123456", fakeIdValidator);

        assertThat(citizen, is(Optional.empty()));
    }

    @ParameterizedTest
    @MethodSource("citizensForIds")
    void extractFromId_shouldReturnCorrectCitizenData_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(true);

        final Optional<Citizen> resultCitizen = italyCitizenExtractor.extractFromId(id, fakeIdValidator);

        assertThat(resultCitizen.get(), is(expectedCitizen));
    }

    private static List<Arguments> citizensForIds() {
        return Arrays.asList(
            Arguments.arguments("MRTMTT25D09F205Z", new Citizen("M", 1925, 4, 9, "MILANO (MI)")),
            Arguments.arguments("MLLSNT82P65Z404U", new Citizen("F", 1982, 9, 25, "STATI UNITI D'AMERICA")),
            Arguments.arguments("DLMCTG75B07H227Y", new Citizen("M", 1975, 2, 7, "REINO (BN)")),
            Arguments.arguments("BRSLSE08D50H987B", new Citizen("F", 2008, 4, 10, "SAN MARTINO ALFIERI (AT)")),
            Arguments.arguments("MRCDRA01A13A065E", new Citizen("M", 2001, 1, 13, "AFRICO (RC)")),
            Arguments.arguments("MRCDRALMAMPALSRE", new Citizen("M", 2001, 1, 13, "AFRICO (RC)"))
        );
    }
}
