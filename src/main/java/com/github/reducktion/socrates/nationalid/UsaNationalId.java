package com.github.reducktion.socrates.nationalid;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Id for United States of America.
 *
 * Information about this national id can be found at:
 *  - https://www.ssa.gov/employer/randomization.html
 *  - https://en.wikipedia.org/wiki/Social_Security_number#Valid_SSNs
 *  - (Blacklisted SSN) https://www.ssa.gov/history/ssn/misused.html
 */
final class UsaNationalId extends NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;
    private static final List<String> BLACKLISTED_IDS = Arrays.asList("078051120", "219099999", "457555462");

    public UsaNationalId(final String id) {
        super(id);
    }

    @Override
    public boolean isValid() {
        return sanitizedId != null
            && sanitizedId.length() == ID_NUMBER_OF_CHARACTERS
            && StringUtils.isNumeric(sanitizedId)
            && !BLACKLISTED_IDS.contains(sanitizedId)
            && hasValidAreaCodes();
    }

    private boolean hasValidAreaCodes() {
        return hasValidFirstAreaCode() && hasValidSecondAreaCode() && hasValidThirdAreaCode();
    }

    private boolean hasValidFirstAreaCode() {
        final int firstAreaCode = Integer.parseInt(sanitizedId.substring(0, 3));
        return firstAreaCode != 0 && firstAreaCode != 666 && firstAreaCode < 900;
    }

    private boolean hasValidSecondAreaCode() {
        return Integer.parseInt(sanitizedId.substring(3, 5)) != 0;
    }

    private boolean hasValidThirdAreaCode() {
        return Integer.parseInt(sanitizedId.substring(5)) != 0;
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        return Optional.empty();
    }
}
