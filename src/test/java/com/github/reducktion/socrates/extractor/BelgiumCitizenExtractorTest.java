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

class BelgiumCitizenExtractorTest {

    private BelgiumCitizenExtractor belgiumCitizenExtractor;

    @BeforeEach
    void setup() {
        belgiumCitizenExtractor = new BelgiumCitizenExtractor();
    }

    @Test
    void extractFromId_shouldReturnEmptyOptional_whenIdIsInvalid() {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(false);

        final Optional<Citizen> citizen = belgiumCitizenExtractor.extractFromId("1234567890", fakeIdValidator);

        assertThat(citizen, is(Optional.empty()));
    }

    @ParameterizedTest
    @MethodSource("citizensForIds")
    void extractFromId_shouldReturnCorrectCitizenData_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(true);

        final Optional<Citizen> resultCitizen = belgiumCitizenExtractor.extractFromId(id, fakeIdValidator);

        assertThat(resultCitizen.get(), is(expectedCitizen));
    }

    private static List<Arguments> citizensForIds() {
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
                "01 11 16-001 05",
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
}
