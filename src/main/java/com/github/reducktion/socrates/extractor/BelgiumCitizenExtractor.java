package com.github.reducktion.socrates.extractor;

import java.util.Optional;

import com.github.reducktion.socrates.validator.IdValidator;

/**
 * {@link Citizen} extractor for Belgium.
 * <p>
 * Information about the ID can be found here:
 * http://www.ibz.rrn.fgov.be/fileadmin/user_upload/nl/rr/instructies/IT-lijst/IT000_Rijksregisternummer.pdf
 */
class BelgiumCitizenExtractor implements CitizenExtractor {

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
        return id.replaceAll("[. -]+", "");
    }

    private Gender extractGender(final String id) {
        final int sequenceNumber = Integer.parseInt(id.substring(6, 9));
        return sequenceNumber % 2 == 0 ? Gender.FEMALE : Gender.MALE;
    }

    private Integer extractYearOfBirth(final String id) {
        final int checksum = Integer.parseInt(id.substring(9));
        final int nonY2kChecksumInput = Integer.parseInt(id.substring(0, 9));
        final boolean y2k = checksum != (97 - (nonY2kChecksumInput % 97));
        final String yearOfBirthCharacters = id.substring(0, 2);
        return Integer.parseInt((y2k ? "20" : "19") + yearOfBirthCharacters);
    }

    private Integer extractMonthOfBirth(final String id) {
        final String monthOfBirthCharacters = id.substring(2, 4);
        return monthOfBirthCharacters.equals("00") ? null
            : Integer.parseInt(monthOfBirthCharacters);
    }

    private Integer extractDayOfBirth(final String id) {
        final String dayOfBirthCharacters = id.substring(4, 6);
        return dayOfBirthCharacters.equals("00") ? null
            : Integer.parseInt(dayOfBirthCharacters);
    }
}
