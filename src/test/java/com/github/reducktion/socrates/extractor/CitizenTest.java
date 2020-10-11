package com.github.reducktion.socrates.extractor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CitizenTest {

    private Citizen citizenOne;
    private Citizen citizenTwo;
    private Citizen citizenThree;

    @BeforeEach
    void setup() {
        citizenOne = Citizen
            .builder()
            .gender(Gender.MALE)
            .yearOfBirth(2000)
            .monthOfBirth(1)
            .dayOfBirth(3)
            .placeOfBirth("Place Of Birth")
            .build();

        citizenTwo = Citizen
            .builder()
            .gender(Gender.MALE)
            .yearOfBirth(2000)
            .monthOfBirth(1)
            .dayOfBirth(3)
            .placeOfBirth("Place Of Birth")
            .build();

        citizenThree = Citizen
            .builder()
            .gender(Gender.MALE)
            .yearOfBirth(2000)
            .monthOfBirth(1)
            .dayOfBirth(3)
            .placeOfBirth("Place Of Birth")
            .build();
    }

    @Test
    void equals_shouldReturnFalse_whenObjectIsNull() {
        assertThat(citizenOne.equals(null), is(false));
    }

    @Test
    void equals_shouldReturnFalse_whenClassIsDifferent() {
        assertThat(citizenOne.equals(""), is(false));
    }

    @ParameterizedTest
    @MethodSource("unequalCitizens")
    void equals_shouldReturnFalse_whenParameterIsDifferent(
        final Citizen unequalCitizenOne,
        final Citizen unequalCitizenTwo
    ) {
        assertThat(unequalCitizenOne.equals(unequalCitizenTwo), is(false));
    }

    private static List<Arguments> unequalCitizens() {
        return Arrays.asList(
            Arguments.arguments(
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .build(),
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .build()
                ),
            Arguments.arguments(
                Citizen
                    .builder()
                    .yearOfBirth(1999)
                    .build(),
                Citizen
                    .builder()
                    .yearOfBirth(2000)
                    .build()
            ),
            Arguments.arguments(
                Citizen
                    .builder()
                    .monthOfBirth(1)
                    .build(),
                Citizen
                    .builder()
                    .monthOfBirth(2)
                    .build()
            ),
            Arguments.arguments(
                Citizen
                    .builder()
                    .dayOfBirth(1)
                    .build(),
                Citizen
                    .builder()
                    .dayOfBirth(2)
                    .build()
            ),
            Arguments.arguments(
                Citizen
                    .builder()
                    .placeOfBirth("Place of Birth One")
                    .build(),
                Citizen
                    .builder()
                    .placeOfBirth("Place of Birth Two")
                    .build()
            )
        );
    }

    @Test
    void equals_shouldBeReflexive() {
        assertThat(citizenOne.equals(citizenOne), is(true));
    }

    @Test
    void equals_shouldBeSymmetric() {
        assertThat(citizenOne.equals(citizenTwo), is(true));
        assertThat(citizenTwo.equals(citizenOne), is(true));
    }

    @Test
    void equals_shouldBeTransitive() {
        assertThat(citizenOne.equals(citizenTwo), is(true));
        assertThat(citizenTwo.equals(citizenThree), is(true));
        assertThat(citizenOne.equals(citizenThree), is(true));
    }

    @Test
    void hashCode_shouldBeConsistentWithEquals() {
        assertThat(citizenOne.equals(citizenTwo), is(true));
        assertThat(citizenOne.hashCode(), is(citizenTwo.hashCode()));
    }
}
