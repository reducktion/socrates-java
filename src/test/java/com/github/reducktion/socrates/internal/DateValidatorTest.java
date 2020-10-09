package com.github.reducktion.socrates.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DateValidatorTest {

    @Test
    void validate_shouldReturnTrue_whenDateIsValid() {
        assertThat(DateValidator.validate(2020, 8, 8), is(true));
    }

    @ParameterizedTest
    @MethodSource("invalidDates")
    void validate_shouldReturnFalse_whenDateIsInvalid(final int year, final int month, final int day) {
        assertThat(DateValidator.validate(year, month, day), is(false));
    }

    private static List<Arguments> invalidDates() {
        return Arrays.asList(
            Arguments.arguments(2001, 13, 16), // month too high
            Arguments.arguments(2001, 11, 31), // November has 30 days
            Arguments.arguments(2001, 13, 31), // day too high
            Arguments.arguments(2001, 2, 29)   // 29 February only exists in leap year
        );
    }
}
