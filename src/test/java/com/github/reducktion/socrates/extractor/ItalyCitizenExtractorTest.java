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
            Arguments.arguments(
                "MRTMTT25D09F205Z",
                Citizen
                    .builder()
                    .gender("M")
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
                    .gender("F")
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
                    .gender("M")
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
                    .gender("F")
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
                    .gender("M")
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
                    .gender("M")
                    .yearOfBirth(2001)
                    .monthOfBirth(1)
                    .dayOfBirth(13)
                    .placeOfBirth("AFRICO (RC)")
                    .build()
            )
        );
    }
}
