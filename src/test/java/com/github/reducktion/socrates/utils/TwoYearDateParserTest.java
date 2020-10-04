package com.github.reducktion.socrates.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TwoYearDateParserTest {

    private TwoYearDateParser twoYearDateParser;

    @BeforeEach
    void setup() {
        twoYearDateParser = new TwoYearDateParser(2020);
    }

    @Test
    void parse_shouldReturnPastCentury_whenTheLastTwoDigitsAreEqualOrGreaterThanTheLastTwoDigitsOfTheCurrentYear() {
        assertThat(twoYearDateParser.parse("20"), is(1920));
    }

    @Test
    void parse_shouldReturnCurrentCentury_whenTheLastTwoDigitsAreLessThanTheLastTwoDigitsOfTheCurrentYear() {
        assertThat(twoYearDateParser.parse("19"), is(2019));
    }
}
