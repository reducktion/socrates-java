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

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.extractor.Gender;

class DenmarkIdGeneratorTest {

    private DenmarkIdGenerator denmarkIdGenerator;

    @BeforeEach
    void setup() {
        denmarkIdGenerator = new DenmarkIdGenerator();
    }

    @Test
    void validate_exceptionIsThrown_withInvalidCitizen() {
        assertThrows(IllegalArgumentException.class, () -> {
            denmarkIdGenerator.generate(new Citizen.Builder().build());
        });
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("testCitizenProvider")
    void validate_cprIsReturned_withValidCitizen(final int year, final int month, final int day,
         final Gender gender, final String cpr) {
        assertThat(denmarkIdGenerator.generate(
            new Citizen.Builder()
                .yearOfBirth(year)
                .monthOfBirth(month)
                .dayOfBirth(day)
                .gender(gender)
                .build()
        ), is(cpr));
    }

    static Stream<Arguments> testCitizenProvider() {
        return Stream.of(
            Arguments.arguments(1991, 6, 16, Gender.MALE, "160691-3113"),
            Arguments.arguments(1984, 10, 8, Gender.FEMALE, "081084-3012")
        );
    }
}
