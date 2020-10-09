package com.github.reducktion.socrates.internal;

import java.time.LocalDate;

/**
 * Class that validates if a date is a valid date.
 */
public final class DateValidator {

    /**
     * Validate the date, given the {@code year}, {@code month} and {@code day}.
     *
     * @param year the year
     * @param month the month
     * @param day the day
     * @return true if the date is a valid date, false otherwise.
     */
    public static boolean validate(final int year, final int month, final int day) {
        if (month > 12 || day > 31) {
            return false;
        }

        // sanity check
        if (day != 0 && month != 0 && year != 0) {
            try {
                LocalDate.of(year, month, day);
            } catch (final Exception e) {
                return false;
            }
        }
        return true;
    }
}
