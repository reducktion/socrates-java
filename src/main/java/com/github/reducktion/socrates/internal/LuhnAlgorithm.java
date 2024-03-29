package com.github.reducktion.socrates.internal;

import java.util.Optional;

/**
 * Implementation of the Luhn algorithm.
 * <p>
 * Source: https://en.wikipedia.org/wiki/Luhn_algorithm
 */
public final class LuhnAlgorithm {

    private static final int DEFAULT_RADIX = 10;

    private LuhnAlgorithm() {}

    /**
     * Validates the Luhn Algorithm, with the radix 10, i.e. it only accepts numerical characters.
     *
     * @param id the id to be validated
     * @return true if the {@code id} is valid, false otherwise
     */
    public static boolean validate(final String id) {
        if (!StringUtils.isNumeric(id)) {
            return false;
        }

        return validate(id, DEFAULT_RADIX);
    }

    /**
     * Validates the Luhn Algorithm.
     * <p>
     * The radix is used to map characters to code-point (for more information check
     * https://en.wikipedia.org/wiki/Luhn_mod_N_algorithm).
     *
     * @param id the id to be validated
     * @param radix the radix used to map the characters
     * @return true if the {@code id} is valid, false otherwise
     */
    public static boolean validate(final String id, final int radix) {
        if (id == null) {
            return false;
        }

        return sum(id, radix) % 10 == 0;
    }

    private static int sum(final String id, final int radix) {
        int sum = 0;
        boolean everyOtherDigit = false;

        for (int i = id.length() - 1; i >= 0; --i) {
            int value = Character.digit(id.charAt(i), radix);

            if (everyOtherDigit) {
                value *= 2;

                if (value > 9) {
                    value -= 9;
                }
            }
            sum += value;
            everyOtherDigit = !everyOtherDigit;
        }
        return sum;
    }

    /**
     * Computes the check digit for the Luhn Algorithm, with the radix 10, i.e. it only accepts numerical characters.
     *
     * @param id the id to compute the check digit
     * @return the check digit
     */
    public static Optional<Integer> computeCheckDigit(final String id) {
        if (!StringUtils.isNumeric(id)) {
            return Optional.empty();
        }

        return computeCheckDigit(id, DEFAULT_RADIX);
    }

    /**
     * Computes the check digit for the Luhn Algorithm.
     * <p>
     * The radix is used to map characters to code-point (for more information check
     * https://en.wikipedia.org/wiki/Luhn_mod_N_algorithm).
     *
     * @param id the id to compute the check digit
     * @param radix the radix used to map the characters
     * @return the check digit
     */
    public static Optional<Integer> computeCheckDigit(final String id, final int radix) {
        if (id == null) {
            return Optional.empty();
        }

        final int sum = sum(id + "0", radix);
        final int checkDigit = (sum * 9) % 10;
        return Optional.of(checkDigit);
    }
}
