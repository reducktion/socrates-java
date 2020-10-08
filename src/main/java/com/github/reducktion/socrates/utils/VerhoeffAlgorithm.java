package com.github.reducktion.socrates.utils;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

/**
 * Implementation of the Verhoeff algorithm.
 *
 * Source: https://en.wikipedia.org/wiki/Verhoeff_algorithm
 */
public final class VerhoeffAlgorithm {

    private static final int BASE_10_RADIX = 10;

    private static final int[][] MULTIPLICATION_TABLE = new int[][] {
        { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
        { 1, 2, 3, 4, 0, 6, 7, 8, 9, 5 },
        { 2, 3, 4, 0, 1, 7, 8, 9, 5, 6 },
        { 3, 4, 0, 1, 2, 8, 9, 5, 6, 7 },
        { 4, 0, 1, 2, 3, 9, 5, 6, 7, 8 },
        { 5, 9, 8, 7, 6, 0, 4, 3, 2, 1 },
        { 6, 5, 9, 8, 7, 1, 0, 4, 3, 2 },
        { 7, 6, 5, 9, 8, 2, 1, 0, 4, 3 },
        { 8, 7, 6, 5, 9, 3, 2, 1, 0, 4 },
        { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 }
    };

    private static final int[][] PERMUTATION_TABLE = new int[][] {
        { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 },
        { 1, 5, 7, 6, 2, 8, 3, 0, 9, 4 },
        { 5, 8, 0, 3, 7, 9, 6, 1, 4, 2 },
        { 8, 9, 1, 6, 0, 4, 3, 5, 2, 7 },
        { 9, 4, 5, 3, 1, 2, 6, 8, 7, 0 },
        { 4, 2, 8, 6, 5, 7, 3, 9, 0, 1 },
        { 2, 7, 9, 3, 8, 0, 6, 4, 1, 5 },
        { 7, 0, 4, 6, 9, 1, 3, 2, 5, 8 }
    };

    private static final int[] INVERSE_TABLE = new int[] { 0, 4, 3, 2, 1, 5, 6, 7, 8, 9 };

    private VerhoeffAlgorithm() {}

    /**
     * Computes the check digit for the Verhoeff Algorithm.
     *
     * @param id the id to be validated
     * @return true if the {@code id} is valid, false otherwise
     */
    public static boolean validate(final String id) {
        if (!StringUtils.isNumeric(id)) {
            return false;
        }
        return computeChecksum(id) == 0;
    }

    /**
     * Computes the check digit for the Verhoeff Algorithm.
     *
     * @param id the id to compute the check digit
     * @return the check digit
     */
    public static Optional<Integer> computeCheckDigit(final String id) {
        if (!StringUtils.isNumeric(id)) {
            return Optional.empty();
        }

        final int checksum = computeChecksum(id + "0");
        return Optional.of(checksum);
    }

    private static int computeChecksum(final String id) {
        final String reversedId = new StringBuilder(id)
            .reverse()
            .toString();

        int c = 0;
        for (int i = 0; i < reversedId.length(); i++) {
            final int digit = Character.digit(reversedId.charAt(i), BASE_10_RADIX);
            final int p = PERMUTATION_TABLE[i % 8][digit];
            c = MULTIPLICATION_TABLE[c][p];
        }

        return INVERSE_TABLE[c];
    }
}
