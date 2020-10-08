package com.github.reducktion.socrates.extractor;

import java.time.Year;
import java.util.Optional;

import com.github.reducktion.socrates.utils.TwoYearDateParser;
import com.github.reducktion.socrates.validator.IdValidator;

/**
 * {@link Citizen} extractor for Denmark.
 *
 * Information about the ID can be found in this wikipedia article:
 * https://en.wikipedia.org/wiki/Unique_Population_Registry_Code
 */
class DenmarkCitizenExtractor implements CitizenExtractor {

    private final TwoYearDateParser twoYearDateParser = new TwoYearDateParser(Year.now().getValue());

    @Override
    public Optional<Citizen> extractFromId(final String id, final IdValidator idValidator) {
        if (!idValidator.validate(id)) {
            return Optional.empty();
        }

        final String sanitizedId = sanitize(id);

        final Citizen citizen = Citizen
            .builder()
            .yearOfBirth(extractYearOfBirth(sanitizedId))
            .monthOfBirth(extractMonthOfBirth(sanitizedId))
            .dayOfBirth(extractDayOfBirth(sanitizedId))
            .build();

        return Optional.of(citizen);
    }

    private String sanitize(final String id) {
        return id.replaceAll("[ -]", "");
    }

    private Integer extractYearOfBirth(final String id) {
        final String yearOfBirthCharacters = id.substring(4, 6);
        return twoYearDateParser
            .parse(yearOfBirthCharacters)
            .orElse(null);
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
