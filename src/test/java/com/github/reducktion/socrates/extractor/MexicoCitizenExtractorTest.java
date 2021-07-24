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

class MexicoCitizenExtractorTest {

    private MexicoCitizenExtractor mexicoCitizenExtractor;

    @BeforeEach
    void setup() {
        mexicoCitizenExtractor = new MexicoCitizenExtractor();
    }

    @Test
    void extractFromId_shouldReturnEmptyOptional_whenIdIsInvalid() {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(false);

        final Optional<Citizen> citizen = mexicoCitizenExtractor.extractFromId("1234567890123456", fakeIdValidator);

        assertThat(citizen, is(Optional.empty()));
    }

    @ParameterizedTest
    @MethodSource("citizensForIds")
    void extractFromId_shouldReturnCorrectCitizenData_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final FakeIdValidator fakeIdValidator = new FakeIdValidator(true);

        final Optional<Citizen> resultCitizen = mexicoCitizenExtractor.extractFromId(id, fakeIdValidator);

        assertThat(resultCitizen.get(), is(expectedCitizen));
    }

    private static List<Arguments> citizensForIds() {
        return Arrays.asList(
            Arguments.arguments(
                "AAIM901112MBCNMN08",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1990)
                    .monthOfBirth(11)
                    .dayOfBirth(12)
                    .placeOfBirth("BAJA CALIFORNIA")
                    .build()
            ),
            Arguments.arguments(
                "JOIM890106HHGSMN08",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1989)
                    .monthOfBirth(1)
                    .dayOfBirth(6)
                    .placeOfBirth("HIDALGO")
                    .build()
            ),
            Arguments.arguments(
                "JOTA950616HBCSWS03",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1995)
                    .monthOfBirth(6)
                    .dayOfBirth(16)
                    .placeOfBirth("BAJA CALIFORNIA")
                    .build()
            ),
            Arguments.arguments(
                "AAJM900827MGTDPS05",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1990)
                    .monthOfBirth(8)
                    .dayOfBirth(27)
                    .placeOfBirth("GUANAJUATO")
                    .build()
            )
        );
    }
}
