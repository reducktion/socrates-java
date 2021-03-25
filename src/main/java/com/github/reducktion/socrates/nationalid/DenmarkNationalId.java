package com.github.reducktion.socrates.nationalid;

import java.util.Optional;
import java.util.regex.Pattern;

import com.github.reducktion.socrates.extractor.Gender;
import com.github.reducktion.socrates.internal.DateValidator;

/**
 * National Id for Denmark.
 *
 * Information about the Denmark's national id following wikipedia pages:
 *  - https://en.wikipedia.org/wiki/Personal_identification_number_(Denmark)
 *  - https://en.wikipedia.org/wiki/Unique_Population_Registry_Code
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
        if (id == null) {
            return false;
        }

        return ID_PATTERN.matcher(sanitizedId).matches()
            && isValidDateOfBirth()
            && isValidChecksum();
    }

    private boolean isValidDateOfBirth() {
        final Optional<Integer> year = getYearOfBirth();
        final Optional<Integer> month = getMonthOfBirth();
        final Optional<Integer> day = getDayOfBirth();

        if (!year.isPresent() || !month.isPresent() || !day.isPresent()) {
            return false;
        }

        return DateValidator.validate(year.get(), month.get(), day.get());
    }

    private boolean isValidChecksum() {
        int sum = 0;
        for (int i = 0; i < sanitizedId.length() && i < MULTIPLIERS.length; i++) {
            final int digit = Character.getNumericValue(sanitizedId.charAt(i));
            sum += digit * MULTIPLIERS[i];
        }
        return sum % 11 == 0;
    }

    @Override
    public Optional<Integer> getYearOfBirth() {
        if (sanitizedId == null) {
            return Optional.empty();
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

        return Optional.of(century + twoDigitsYear);
    }

    @Override
    public Optional<Integer> getMonthOfBirth() {
        if (sanitizedId == null) {
            return Optional.empty();
        }

        final int monthOfBirth = Integer.parseInt(sanitizedId.substring(2, 4));

        return Optional.of(monthOfBirth);
    }

    @Override
    public Optional<Integer> getDayOfBirth() {
        if (sanitizedId == null) {
            return Optional.empty();
        }

        final int dayOfBirth = Integer.parseInt(sanitizedId.substring(0, 2));

        return Optional.of(dayOfBirth);
    }

    @Override
    public Optional<Gender> getGender() {
        if (sanitizedId == null) {
            return Optional.empty();
        }

        final int genderInt = Integer.parseInt(sanitizedId.substring(8, 10));

        final Gender gender = genderInt % 2 == 0 ? Gender.MALE : Gender.FEMALE;
        return Optional.of(gender);
    }

    @Override
    public Optional<String> getPlaceOfBirth() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return id;
    }
}
