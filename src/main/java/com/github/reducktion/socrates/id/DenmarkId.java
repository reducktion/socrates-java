package com.github.reducktion.socrates.id;

import java.util.Optional;
import java.util.regex.Pattern;

import com.github.reducktion.socrates.extractor.Gender;
import com.github.reducktion.socrates.internal.DateValidator;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Id for Denmark.
 *
 * Information about the Denmark's national id following wikipedia pages:
 *  - https://en.wikipedia.org/wiki/Personal_identification_number_(Denmark)
 *  - https://en.wikipedia.org/wiki/Unique_Population_Registry_Code
 */
class DenmarkId implements IdValidator, IdInfoExtractor {

    private static final Pattern ID_PATTERN = Pattern.compile("\\d{10}");
    private static final int[] MULTIPLIERS = { 4, 3, 2, 7, 6, 5, 4, 3, 2, 1 };

    private final String id;
    private final String sanitizedId;

    public DenmarkId(final String id) {
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

        final Optional<Integer> centuryDigit = StringUtils.parseInt(sanitizedId.substring(6, 7));
        final Optional<Integer> twoDigitsYear = StringUtils.parseInt(sanitizedId.substring(4, 6));

        if (!centuryDigit.isPresent() || !twoDigitsYear.isPresent()) {
            return Optional.empty();
        }

        final int century;
        if (centuryDigit.get() < 4) {
            century = 1900;
        } else if (centuryDigit.get() == 4 || centuryDigit.get() == 9) {
            if (twoDigitsYear.get() <= 36) {
                century = 2000;
            } else {
                century = 1900;
            }
        } else {
            if (twoDigitsYear.get() >= 58) {
                century = 1800;
            } else {
                century = 2000;
            }
        }

        return Optional.of(century + twoDigitsYear.get());
    }

    @Override
    public Optional<Integer> getMonthOfBirth() {
        if (sanitizedId == null) {
            return Optional.empty();
        }
        return StringUtils.parseInt(sanitizedId.substring(2, 4));
    }

    @Override
    public Optional<Integer> getDayOfBirth() {
        if (sanitizedId == null) {
            return Optional.empty();
        }
        return StringUtils.parseInt(sanitizedId.substring(0, 2));
    }

    @Override
    public Optional<Gender> getGender() {
        if (sanitizedId == null) {
            return Optional.empty();
        }

        final Optional<Integer> genderInt = StringUtils.parseInt(sanitizedId.substring(8, 10));

        if (!genderInt.isPresent()) {
            return Optional.empty();
        }

        final Gender gender = genderInt.get() % 2 == 0 ? Gender.MALE : Gender.FEMALE;
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
