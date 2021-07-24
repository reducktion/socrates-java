package com.github.reducktion.socrates.generator;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Gender;

/**
 * Generates a new CPR for the provided information
 *
 * CPR logic:
 *  * https://en.wikipedia.org/wiki/Personal_identification_number_(Denmark)
 *  * https://da.wikipedia.org/wiki/CPR-nummer
 */
class DenmarkNationalIdGenerator implements NationalIdGenerator, IdGenerator {
    private static final int[] MULTIPLIERS = { 4, 3, 2, 7, 6, 5, 4, 3, 2, 1 };

    @Override
    public String generate(final Citizen citizen) {
        if (!isRequiredDataPresent(citizen)) {
            throw new IllegalArgumentException(
                "Date of birth and gender information is necessary to generate a Danish CPR"
            );
        }

        final String dateOfBirth = String.format("%02d", citizen.getDayOfBirth().get())
            + String.format("%02d", citizen.getMonthOfBirth().get())
            + getYearString(citizen.getYearOfBirth().get());

        final String centuryDigit = getCenturyDigit(citizen.getYearOfBirth().get());
        final String checkDigit = getCheckDigit(citizen.getGender().get());

        final int sum = calculateCheckSum(dateOfBirth + centuryDigit + "00" + checkDigit);
        final int ceilingValue = (int) Math.ceil(((double) sum) / 11);
        final int remainder = (ceilingValue * 11) - sum;
        final String generatedDigits = findFinalDigits(remainder);

        return dateOfBirth + "-" + centuryDigit + generatedDigits + checkDigit;
    }

    private static boolean isRequiredDataPresent(final Citizen citizen) {
        return citizen.getYearOfBirth().isPresent()
            && citizen.getMonthOfBirth().isPresent()
            && citizen.getDayOfBirth().isPresent()
            && citizen.getGender().isPresent();
    }

    private static String getYearString(final Integer yearOfBirth) {
        final int lastDigits = Integer.parseInt(yearOfBirth.toString().substring(2));
        return String.format("%02d", lastDigits);
    }

    private static String getCenturyDigit(final Integer yearOfBirth) {
        if (yearOfBirth < 1999) {
            return "3";
        }

        return yearOfBirth < 2036 ? "4" : "5";
    }

    private static String getCheckDigit(final Gender gender) {
        return Gender.FEMALE == gender ? "2" : "3";
    }

    private static int calculateCheckSum(final String cpr) {
        int sum = 0;
        for (int i = 0; i < cpr.length() && i < MULTIPLIERS.length; i++) {
            final int digit = Character.getNumericValue(cpr.charAt(i));
            sum += digit * MULTIPLIERS[i];
        }
        return sum;
    }

    private static String findFinalDigits(final double targetSum) {
        if (targetSum / MULTIPLIERS[7] < 1 && targetSum / MULTIPLIERS[8] < 1) {
            return findFinalDigits(targetSum + 11);
        }

        if (targetSum % MULTIPLIERS[7] == 0) {
            return (int) targetSum / MULTIPLIERS[7] + "0";
        }

        if (targetSum % MULTIPLIERS[8] == 0) {
            return "0" + (int) targetSum / MULTIPLIERS[8];
        }

        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 9; j++) {
                if (targetSum == MULTIPLIERS[7] * i + MULTIPLIERS[8] * j) {
                    return String.valueOf(i) + j;
                }
            }
        }

        throw new ArithmeticException("Could not generate a valid cpr for this data. Please open an issue in https://github.com/reducktion/socrates-java/issues");
    }
}
