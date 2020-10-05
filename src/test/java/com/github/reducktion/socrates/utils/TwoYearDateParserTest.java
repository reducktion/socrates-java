package com.github.reducktion.socrates.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwoYearDateParserTest {

    private TwoYearDateParser twoYearDateParser;

    @BeforeEach
    void setup() {
        twoYearDateParser = new TwoYearDateParser(2020);
    }

    @Test
    void parse_shouldReturnEmptyOptional_whenArgumentIsNull() {
        assertThat(twoYearDateParser.parse(null), is(Optional.empty()));
    }

    @Test
    void parse_shouldReturnEmptyOptional_whenArgumentIsNotNumeric() {
        assertThat(twoYearDateParser.parse("AB"), is(Optional.empty()));
    }

    @Test
    void parse_shouldReturnEmptyOptional_whenYearLastTwoDigitsIsNegativeNumber() {
        assertThat(twoYearDateParser.parse("-1"), is(Optional.empty()));
    }

    @Test
    void parse_shouldReturnPastCentury_whenTheLastTwoDigitsAreEqualOrGreaterThanTheLastTwoDigitsOfTheCurrentYear() {
        assertThat(twoYearDateParser.parse("20").get(), is(1920));
    }

    @Test
    void parse_shouldReturnCurrentCentury_whenTheLastTwoDigitsAreLessThanTheLastTwoDigitsOfTheCurrentYear() {
        assertThat(twoYearDateParser.parse("19").get(), is(2019));
    }
}
