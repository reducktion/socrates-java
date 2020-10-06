package com.github.reducktion.socrates.validator;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * National Identification Number validator for Brazil.
 *
 * The validation algorithm was based on the following wikipedia source:
 * https://pt.wikipedia.org/wiki/Cadastro_de_pessoas_f%C3%ADsicas#Algoritmo
 */
class BrazilIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 11;
    private static final int BASE_10_RADIX = 10;

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (!StringUtils.isNumeric(sanitizedId) || sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        return validateChecksum(sanitizedId);
    }

    private String sanitize(final String id) {
        return id.replaceAll("[-. ]+", "");
    }

    private boolean validateChecksum(final String id) {
        final int checksum = Integer.parseInt(id.substring(id.length() - 2));

        final String partialId = id.substring(0, id.length() - 2);
        final String reversedPartialId = new StringBuilder(partialId)
            .reverse()
            .toString();

        int v1 = 0;
        int v2 = 0;

        for (int i = 0; i < reversedPartialId.length(); i++) {
            v1 = v1 + Character.digit(reversedPartialId.charAt(i), BASE_10_RADIX) * (9 - (i % 10));
            v2 = v2 + Character.digit(reversedPartialId.charAt(i), BASE_10_RADIX) * (9 - ((i + 1) % 10));
        }

        v1 = (v1 % 11) % 10;
        v2 = v2 + (v1 * 9);
        v2 = (v2 % 11) % 10;

        return checksum == ((v1 * 10) + v2);
    }
}
