package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.extractor.Gender;
import com.github.reducktion.socrates.internal.DateValidator;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Id for Belgium.
 *
 * Information about this national id can be found at:
 *  - http://www.ibz.rrn.fgov.be/fileadmin/user_upload/nl/rr/instructies/IT-lijst/IT000_Rijksregisternummer.pdf
 */
class BelgiumNationalId implements NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 11;

    private final String id;
    private final String sanitizedId;

    public BelgiumNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null ? null : id.replaceAll("[. -]+", "");
    }

    @Override
    public boolean isValid() {
        if (!StringUtils.isNumeric(sanitizedId)
            || sanitizedId.length() != ID_NUMBER_OF_CHARACTERS
            || !hasValidSequenceNumber()) {
            return false;
        }

        boolean y2k = false;
        if (!hasValidChecksum(y2k)) {
            y2k = true;
            if (!hasValidChecksum(y2k)) {
                return false;
            }
        }

        return hasValidBirthOfDate(y2k);
    }

    private boolean hasValidSequenceNumber() {
        final int sequenceNumber = extractSequenceNumber();
        return sequenceNumber != 0 && sequenceNumber != 999; // range from 001 to 998
    }

    private int extractSequenceNumber() {
        return Integer.parseInt(sanitizedId.substring(6, 9));
    }

    private boolean hasValidChecksum(final boolean y2k) {
        final String input = (y2k ? "2" : "") + extractY2kChecksumCharacters();
        return extractChecksum() == computeChecksum(input);
    }

    private String extractY2kChecksumCharacters() {
        return sanitizedId.substring(0, 9);
    }

    private int extractChecksum() {
        return Integer.parseInt(sanitizedId.substring(9));
    }

    private long computeChecksum(final String input) {
        return 97 - (Long.parseLong(input) % 97);
    }

    private boolean hasValidBirthOfDate(final boolean y2k) {
        int year = Integer.parseInt(extractYearOfBirthCharacters());
        final int month = Integer.parseInt(extractMonthOfBirthCharacters());
        final int day = Integer.parseInt(extractDayOfBirthCharacters());

        if (month > 12 || day > 31) {
            return false;
        }

        year = y2k ? 2000 : 1900 + year;
        return DateValidator.validate(year, month, day);
    }

    private String extractYearOfBirthCharacters() {
        return sanitizedId.substring(0, 2);
    }

    // returns "00" if the month of birth is unknown
    private String extractMonthOfBirthCharacters() {
        return sanitizedId.substring(2, 4);
    }

    // returns "00" if the day of birth is unknown
    private String extractDayOfBirthCharacters() {
        return sanitizedId.substring(4, 6);
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        if (!isValid()) {
            return Optional.empty();
        }

        return Optional.of(
            Citizen
                .builder()
                .gender(extractGender())
                .yearOfBirth(extractYearOfBirth())
                .monthOfBirth(extractMonthOfBirth())
                .dayOfBirth(extractDayOfBirth())
                .build()
        );
    }

    private Gender extractGender() {
        final int sequenceNumber = extractSequenceNumber();
        return sequenceNumber % 2 == 0 ? Gender.FEMALE : Gender.MALE;
    }

    private Integer extractYearOfBirth() {
        final boolean y2k = extractChecksum() != computeChecksum(extractY2kChecksumCharacters());
        return Integer.parseInt((y2k ? "20" : "19") + extractYearOfBirthCharacters());
    }

    private Integer extractMonthOfBirth() {
        final String monthOfBirthCharacters = extractMonthOfBirthCharacters();
        return "00".equals(monthOfBirthCharacters) ? null : Integer.parseInt(monthOfBirthCharacters);
    }

    private Integer extractDayOfBirth() {
        final String dayOfBirthCharacters = extractDayOfBirthCharacters();
        return "00".equals(dayOfBirthCharacters) ? null : Integer.parseInt(dayOfBirthCharacters);
    }

    @Override
    public String toString() {
        return id;
    }
}
