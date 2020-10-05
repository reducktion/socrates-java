package com.github.reducktion.socrates.validator;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * National Identification Number validator for United States of America.
 *
 * This validation algorithm is based on the official documentation: https://www.ssa.gov/employer/randomization.html
 *
 * A wikipedia source can be found in: https://en.wikipedia.org/wiki/Social_Security_number#Valid_SSNs
 *
 * Blacklisted SSN can be found in: https://www.ssa.gov/history/ssn/misused.html
 **/
class USAIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;
    private static final List<String> BLACKLISTED_IDS = Arrays.asList("078051120", "219099999", "457555462");

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS
            || !StringUtils.isNumeric(sanitizedId)
            || BLACKLISTED_IDS.contains(sanitizedId)
        ) {
            return false;
        }

        return validateAreaCodes(sanitizedId);
    }

    private String sanitize(final String id) {
        return id
            .trim()
            .replace("-", "");
    }

    private boolean validateAreaCodes(final String id) {
        return validateFirstAreaCode(id) && validateSecondAreaCode(id) && validateThirdAreaCode(id);
    }

    private boolean validateFirstAreaCode(final String id) {
        final int firstAreaCode = Integer.parseInt(id.substring(0, 3));
        return firstAreaCode != 0 && firstAreaCode != 666 && firstAreaCode < 900;
    }

    private boolean validateSecondAreaCode(final String id) {
        return Integer.parseInt(id.substring(3, 5)) != 0;
    }

    private boolean validateThirdAreaCode(final String id) {
        return Integer.parseInt(id.substring(5)) != 0;
    }
}
