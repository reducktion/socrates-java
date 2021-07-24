package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Id for Brazil.
 *
 * Information about this national id can be found at:
 *  - https://pt.wikipedia.org/wiki/Cadastro_de_pessoas_f%C3%ADsicas#Algoritmo
 */
final class BrazilNationalId extends NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 11;

    public BrazilNationalId(final String id) {
        super(id);
    }

    @Override
    public boolean isValid() {
        return StringUtils.isNumeric(sanitizedId)
            && sanitizedId.length() == ID_NUMBER_OF_CHARACTERS
            && validateChecksum();
    }

    private boolean validateChecksum() {
        final int checksum = Integer.parseInt(sanitizedId.substring(sanitizedId.length() - 2));

        final String partialId = sanitizedId.substring(0, sanitizedId.length() - 2);
        final String reversedPartialId = new StringBuilder(partialId)
            .reverse()
            .toString();

        int v1 = 0;
        int v2 = 0;

        for (int i = 0; i < reversedPartialId.length(); i++) {
            v1 = v1 + Character.getNumericValue(reversedPartialId.charAt(i)) * (9 - (i % 10));
            v2 = v2 + Character.getNumericValue(reversedPartialId.charAt(i)) * (9 - ((i + 1) % 10));
        }

        v1 = (v1 % 11) % 10;
        v2 = v2 + (v1 * 9);
        v2 = (v2 % 11) % 10;

        return checksum == ((v1 * 10) + v2);
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        return Optional.empty();
    }
}
