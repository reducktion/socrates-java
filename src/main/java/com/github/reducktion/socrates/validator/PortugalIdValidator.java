package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.internal.LuhnAlgorithm;

/**
 * National Identification Number validator for Portugal.
 *
 * This validation algorithm is based on the official documentation released in 26 of January of 2009:
 * https://www.autenticacao.gov.pt/documents/20126/115760/Valida%C3%A7%C3%A3o+de+N%C3%BAmero+de+Documento+do+Cart%C3%A3o+de+Cidad%C3%A3o.pdf/bdc4eb37-7316-3ff4-164a-f869382b7053
 *
 * @deprecated Use class PortugalNationalId instead
 */
@Deprecated
class PortugalIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 12;
    private static final int BASE_36_RADIX = 36;

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = sanitize(id);

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        return LuhnAlgorithm.validate(sanitizedId, BASE_36_RADIX);
    }

    private String sanitize(final String id) {
        return id
            .replace(" ", "")
            .toUpperCase();
    }
}
