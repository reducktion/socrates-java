package com.github.reducktion.socrates.nationalid;

import java.util.Optional;
import java.util.regex.Pattern;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.extractor.Gender;
import com.github.reducktion.socrates.internal.DateValidator;

/**
 * National Id for Denmark.
 *
 * Information about this national id can be found at:
 *  - https://en.wikipedia.org/wiki/Personal_identification_number_(Denmark)
 */
class DenmarkNationalId implements NationalId {

    private static final Pattern ID_PATTERN = Pattern.compile("\\d{10}");
    private static final int[] MULTIPLIERS = { 4, 3, 2, 7, 6, 5, 4, 3, 2, 1 };

    private final String id;
    private final String sanitizedId;

    public DenmarkNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null ? null : id.replaceAll("[ -]", "");
    }

    @Override
    public boolean isValid() {
        return sanitizedId != null
            && ID_PATTERN.matcher(sanitizedId).matches()
            && hasValidDateOfBirth()
            && hasValidChecksum();
    }

    private boolean hasValidDateOfBirth() {
        final Integer year = getYearOfBirth();
        final Integer month = getMonthOfBirth();
        final Integer day = getDayOfBirth();

        if (year == null || month == null || day == null) {
            return false;
        }

        return DateValidator.validate(year, month, day);
    }

    private boolean hasValidChecksum() {
        int sum = 0;
        for (int i = 0; i < sanitizedId.length() && i < MULTIPLIERS.length; i++) {
            final int digit = Character.getNumericValue(sanitizedId.charAt(i));
            sum += digit * MULTIPLIERS[i];
        }
        return sum % 11 == 0;
    }

    private Integer getYearOfBirth() {
        if (sanitizedId == null) {
            return null;
        }

        final int centuryDigit = Integer.parseInt(sanitizedId.substring(6, 7));
        final int twoDigitsYear = Integer.parseInt(sanitizedId.substring(4, 6));

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

    private Integer getMonthOfBirth() {
        if (sanitizedId == null) {
            return null;
        }

        return Integer.parseInt(sanitizedId.substring(2, 4));
    }

    private Integer getDayOfBirth() {
        if (sanitizedId == null) {
            return null;
        }

        return Integer.parseInt(sanitizedId.substring(0, 2));
    }

    @Override
    public Optional<Citizen> getCitizen() {
        if (!isValid()) {
            return Optional.empty();
        }

        return Optional.of(
            Citizen.builder()
                .gender(getGender())
                .yearOfBirth(getYearOfBirth())
                .monthOfBirth(getMonthOfBirth())
                .dayOfBirth(getDayOfBirth())
                .build()
        );
    }

    private Gender getGender() {
        if (sanitizedId == null) {
            return null;
        }

        final int genderInt = Integer.parseInt(sanitizedId.substring(8, 10));

        return genderInt % 2 == 0 ? Gender.MALE : Gender.FEMALE;
    }

    @Override
    public String toString() {
        return id;
    }
}
