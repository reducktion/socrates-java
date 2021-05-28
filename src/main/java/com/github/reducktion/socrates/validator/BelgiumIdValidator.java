package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.internal.DateValidator;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Identification Number validator for Belgium.
 *
 * This validation algorithm is based on the official documentation:
 * http://www.ibz.rrn.fgov.be/fileadmin/user_upload/nl/rr/instructies/IT-lijst/IT000_Rijksregisternummer.pdf
 */
@Deprecated // Use the class BelgiumNationalId instead
class BelgiumIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 11;

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (!StringUtils.isNumeric(sanitizedId)
            || sanitizedId.length() != ID_NUMBER_OF_CHARACTERS
            || !validateSequenceNumber(sanitizedId)) {
            return false;
        }

        boolean y2k = false;

        if (!validateChecksum(sanitizedId, y2k)) {
            y2k = true;
            if (!validateChecksum(sanitizedId, y2k)) {
                return false;
            }
        }
        return validateDateOfBirth(sanitizedId, y2k);
    }

    private String sanitize(final String id) {
        return id.replaceAll("[. -]+", "");
    }

    private boolean validateSequenceNumber(final String id) {
        final int sequenceNumber = Integer.parseInt(id.substring(6, 9));
        return sequenceNumber != 0 && sequenceNumber != 999; // range from 001 to 998
    }

    private boolean validateChecksum(final String id, final boolean y2k) {
        final String input = (y2k ? "2" : "") + id.substring(0, 9);
        final long providedChecksum = Integer.parseInt(id.substring(9));
        final long calculatedChecksum = 97 - (Long.parseLong(input) % 97);
        return providedChecksum == calculatedChecksum;
    }

    private boolean validateDateOfBirth(final String id, final boolean y2k) {
        int year = Integer.parseInt(id.substring(0, 2));
        final int month = Integer.parseInt(id.substring(2, 4)); // is allowed to be 00 if unknown
        final int day = Integer.parseInt(id.substring(4, 6)); // is allowed to be 00 if unknown

        if (month > 12 || day > 31) {
            return false;
        }

        year = y2k ? 2000 : 1900 + year;
        return DateValidator.validate(year, month, day);
    }
}
