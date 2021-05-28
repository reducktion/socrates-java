package com.github.reducktion.socrates.validator;

import java.util.regex.Pattern;

import com.github.reducktion.socrates.internal.DateValidator;

/**
 * National Identification Number validator for Denmark.
 *
 * This validation algorithm is based on the following wikipedia page:
 * https://en.wikipedia.org/wiki/Personal_identification_number_(Denmark)
 */
@Deprecated // Use the class DenmarkNationalId instead
class DenmarkIdValidator implements IdValidator {

    private static final Pattern ID_PATTERN = Pattern.compile("\\d{10}");
    private static final int[] MULTIPLIERS = { 4, 3, 2, 7, 6, 5, 4, 3, 2, 1 };

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        return ID_PATTERN.matcher(sanitizedId).matches()
            && validateDateOfBirth(sanitizedId)
            && validateChecksum(sanitizedId);
    }

    private String sanitize(final String id) {
        return id.replaceAll("[ -]", "");
    }

    private boolean validateChecksum(final String id) {
        int sum = 0;
        for (int i = 0; i < id.length() && i < MULTIPLIERS.length; i++) {
            final int digit = Character.getNumericValue(id.charAt(i));
            sum += digit * MULTIPLIERS[i];
        }
        return sum % 11 == 0;
    }

    private boolean validateDateOfBirth(final String id) {
        final int day = Integer.parseInt(id.substring(0, 2));
        final int month = Integer.parseInt(id.substring(2, 4));
        final int year = extractYearOfBirth(id);

        return DateValidator.validate(year, month, day);
    }

    private int extractYearOfBirth(final String id) {
        final int centuryDigit = Integer.parseInt(id.substring(6, 7));
        final int twoDigitsYear = Integer.parseInt(id.substring(4, 6));

        final int century;
        if (centuryDigit < 4) {
            century = 1900;
        } else if (centuryDigit == 4 || centuryDigit == 9) {
            if (twoDigitsYear <= 36) {
                century = 2000;
            } else {
                century = 1900;
            }
        } else {
            if (twoDigitsYear >= 58) {
                century = 1800;
            } else {
                century = 2000;
            }
        }

        return century + twoDigitsYear;
    }
}
