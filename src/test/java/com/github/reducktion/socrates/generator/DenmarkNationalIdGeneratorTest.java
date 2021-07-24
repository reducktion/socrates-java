package com.github.reducktion.socrates.generator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Gender;

class DenmarkNationalIdGeneratorTest {

    private DenmarkNationalIdGenerator denmarkNationalIdGenerator;

    @BeforeEach
    void setup() {
        denmarkNationalIdGenerator = new DenmarkNationalIdGenerator();
    }

    @Test
    void generate_shouldThrowException_whenCitizenIsNotValid() {
        assertThrows(
            IllegalArgumentException.class,
            () -> denmarkNationalIdGenerator.generate(new Citizen.Builder().build())
        );
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("citizenProvider")
    void generate_shouldReturnCpr_whenCitizenIsValid(
        final int year,
        final int month,
        final int day,
        final Gender gender,
        final String cpr
    ) {
        assertThat(denmarkNationalIdGenerator.generate(
            new Citizen.Builder()
                .yearOfBirth(year)
                .monthOfBirth(month)
                .dayOfBirth(day)
                .gender(gender)
                .build()
        ), is(cpr));
    }

    static Stream<Arguments> citizenProvider() {
        return Stream.of(
            Arguments.arguments(1991, 6, 16, Gender.MALE, "160691-3113"),
            Arguments.arguments(1984, 10, 8, Gender.FEMALE, "081084-3012")
        );
    }
}
