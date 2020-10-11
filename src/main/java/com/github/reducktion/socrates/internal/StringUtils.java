package com.github.reducktion.socrates.internal;

/**
 * Utility class for managing Strings.
 */
public final class StringUtils {

    private StringUtils() {}

    /**
     * Checks if the CharSequence contains only Unicode digits.
     * A decimal point is not a Unicode digit and returns {@code false}.
     * <p>
     * {@code null} will return {@code false}.
     * An empty CharSequence (length()=0) will return {@code false}.
     * <p>
     * Note that the method does not allow for a leading sign, either positive or negative.
     * Also, if a String passes the numeric test, it may still generate a NumberFormatException
     * when parsed by Integer.parseInt or Long.parseLong, e.g. if the value is outside the range
     * for int or long respectively.
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if only contains digits and is non-null, false otherwise
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if a CharSequence is empty ("") or null.
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null, false otherwise
     */
    private static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
