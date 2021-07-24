package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.internal.LuhnAlgorithm;

/**
 * National Id for Portugal.
 *
 * Information about this national id can be found at:
 *  - https://www.autenticacao.gov.pt/documents/20126/115760/Valida%C3%A7%C3%A3o+de+N%C3%BAmero+de+Documento+do+Cart%C3%A3o+de+Cidad%C3%A3o.pdf/bdc4eb37-7316-3ff4-164a-f869382b7053
 */
final class PortugalNationalId extends NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 12;
    private static final int BASE_36_RADIX = 36;

    public PortugalNationalId(final String id) {
        super(id);
    }

    @Override
    public boolean isValid() {
        return sanitizedId != null
            && sanitizedId.length() == ID_NUMBER_OF_CHARACTERS
            && LuhnAlgorithm.validate(sanitizedId, BASE_36_RADIX);
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        return Optional.empty();
    }
}
