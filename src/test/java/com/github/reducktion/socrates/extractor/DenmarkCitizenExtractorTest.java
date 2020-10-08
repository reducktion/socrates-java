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

class DenmarkCitizenExtractorTest {

    private DenmarkCitizenExtractor denmarkCitizenExtractor;

    @BeforeEach
    void setup() {
        denmarkCitizenExtractor = new DenmarkCitizenExtractor();
    }

    @Test
    void extractFromId_shouldReturnEmptyOptional_whenIdIsInvalid() {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(false);

        final Optional<Citizen> citizen = denmarkCitizenExtractor.extractFromId("1234567890", fakeIdValidator);

        assertThat(citizen, is(Optional.empty()));
    }

    @ParameterizedTest
    @MethodSource("citizensForIds")
    void extractFromId_shouldReturnCorrectCitizenData_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(true);

        final Optional<Citizen> resultCitizen = denmarkCitizenExtractor.extractFromId(id, fakeIdValidator);

        assertThat(resultCitizen.get(), is(expectedCitizen));
    }

    private static List<Arguments> citizensForIds() {
        return Arrays.asList(
            Arguments.arguments(
                "090792-1395",
                Citizen
                    .builder()
                    .yearOfBirth(1992)
                    .monthOfBirth(7)
                    .dayOfBirth(9)
                    .build()
            ),
            Arguments.arguments(
                "070593-0600",
                Citizen
                    .builder()
                    .yearOfBirth(1993)
                    .monthOfBirth(5)
                    .dayOfBirth(7)
                    .build()
            ),
            Arguments.arguments(
                "150437-3068",
                Citizen
                    .builder()
                    .yearOfBirth(1937)
                    .monthOfBirth(4)
                    .dayOfBirth(15)
                    .build()
            ),
            Arguments.arguments(
                "160888-1995",
                Citizen
                    .builder()
                    .yearOfBirth(1988)
                    .monthOfBirth(8)
                    .dayOfBirth(16)
                    .build()
            ),
            Arguments.arguments(
                "040404-7094",
                Citizen
                    .builder()
                    .yearOfBirth(2004)
                    .monthOfBirth(4)
                    .dayOfBirth(4)
                    .build()
            )
        );
    }
}
