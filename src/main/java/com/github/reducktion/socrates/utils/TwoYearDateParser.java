package com.github.reducktion.socrates.utils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;

/**
 * Date time parser that is capable of returning a year (e.g. 1999), given the last two digits of the year as a
 * String (e.g. "99").
 * <p>
 * It has a range of 100 years, meaning that in 2020 the string "20" will result in 1920 and the string "19" will
 * result in 2019.
 */
public class TwoYearDateParser {

    private final DateTimeFormatter twoYearFormatter;

    public TwoYearDateParser(final int currentYear) {
        twoYearFormatter = new DateTimeFormatterBuilder()
            .appendValueReduced(ChronoField.YEAR, 2, 2, currentYear - 100) // change time window
            .toFormatter();
    }

    /**
     * Returns the year, given the last two digits (of the year).
     *
     * @param yearLastTwoDigits the last two digits of the year
     * @return the respective year
     * @throws NullPointerException if {@code yearLastTwoDigits} is null
     * @throws DateTimeParseException if {@code yearLastTwoDigits} is less than zero
     */
    public int parse(final String yearLastTwoDigits) {
        return twoYearFormatter
            .parse(yearLastTwoDigits)
            .get(ChronoField.YEAR);
    }
}
