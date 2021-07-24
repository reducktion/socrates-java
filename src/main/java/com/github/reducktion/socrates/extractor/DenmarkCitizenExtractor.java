package com.github.reducktion.socrates.extractor;

import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Gender;
import com.github.reducktion.socrates.validator.IdValidator;

/**
 * {@link Citizen} extractor for Denmark.
 *
 * Information about the ID can be found in this wikipedia article:
 * https://en.wikipedia.org/wiki/Unique_Population_Registry_Code
 *
 * @deprecated Use class DenmarkNationalId instead
 */
@Deprecated
class DenmarkCitizenExtractor implements CitizenExtractor {

    @Override
    public Optional<Citizen> extractFromId(final String id, final IdValidator idValidator) {
        if (!idValidator.validate(id)) {
            return Optional.empty();
        }

        final String sanitizedId = sanitize(id);

        final Citizen citizen = Citizen
            .builder()
            .gender(extractGender(sanitizedId))
            .yearOfBirth(extractYearOfBirth(sanitizedId))
            .monthOfBirth(extractMonthOfBirth(sanitizedId))
            .dayOfBirth(extractDayOfBirth(sanitizedId))
            .build();

        return Optional.of(citizen);
    }

    private String sanitize(final String id) {
        return id.replaceAll("[ -]", "");
    }

    private Gender extractGender(final String id) {
        final String genderCharacters = id.substring(8, 10);
        return Integer.parseInt(genderCharacters) % 2 == 0 ? Gender.MALE : Gender.FEMALE;
    }

    private Integer extractYearOfBirth(final String id) {
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

    private Integer extractMonthOfBirth(final String id) {
        final String monthOfBirthCharacters = id.substring(2, 4);
        return Integer.parseInt(monthOfBirthCharacters);
    }

    private Integer extractDayOfBirth(final String id) {
        final String dayOfBirthCharacters = id.substring(0, 2);
        return Integer.parseInt(dayOfBirthCharacters);
    }
}
