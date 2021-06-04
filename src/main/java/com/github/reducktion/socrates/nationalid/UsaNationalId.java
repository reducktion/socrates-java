package com.github.reducktion.socrates.nationalid;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Id for United States of America.
 *
 * Information about this national id can be found at:
 *  - https://www.ssa.gov/employer/randomization.html
 *  - https://en.wikipedia.org/wiki/Social_Security_number#Valid_SSNs
 *  - (Blacklisted SSN) https://www.ssa.gov/history/ssn/misused.html
 */
class UsaNationalId implements NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;
    private static final List<String> BLACKLISTED_IDS = Arrays.asList("078051120", "219099999", "457555462");

    private final String id;
    private final String sanitizedId;

    public UsaNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null ? null : id.trim().replace("-", "");
    }

    @Override
    public boolean isValid() {
        return sanitizedId != null
            && sanitizedId.length() == ID_NUMBER_OF_CHARACTERS
            && StringUtils.isNumeric(sanitizedId)
            && !BLACKLISTED_IDS.contains(sanitizedId)
            && hasValidAreaCodes(sanitizedId);
    }

    private boolean hasValidAreaCodes(final String id) {
        return hasValidFirstAreaCode(id) && hasValidSecondAreaCode(id) && hasValidThirdAreaCode(id);
    }

    private boolean hasValidFirstAreaCode(final String id) {
        final int firstAreaCode = Integer.parseInt(id.substring(0, 3));
        return firstAreaCode != 0 && firstAreaCode != 666 && firstAreaCode < 900;
    }

    private boolean hasValidSecondAreaCode(final String id) {
        return Integer.parseInt(id.substring(3, 5)) != 0;
    }

    private boolean hasValidThirdAreaCode(final String id) {
        return Integer.parseInt(id.substring(5)) != 0;
    }

    @Override
    public Optional<Citizen> getCitizen() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return id;
    }
}
