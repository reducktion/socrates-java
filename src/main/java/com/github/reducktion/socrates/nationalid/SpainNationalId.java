package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.internal.StringUtils;

/**
 * National Id for Spain.
 *
 * Information about this national id can be found at:
 *  - http://www.interior.gob.es/web/servicios-al-ciudadano/dni/calculo-del-digito-de-control-del-nif-nie
 */
class SpainNationalId implements NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 9;
    private static final String CONTROL_CHARACTERS = "TRWAGMYFPDXBNJZSQVHLCKE";

    private final String id;
    private final String sanitizedId;

    public SpainNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null
            ? null
            : id.trim()
            .replace("-", "")
            .toUpperCase();
    }

    @Override
    public boolean isValid() {
        if (sanitizedId == null || sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        final String partialId = stripControlCharacter(sanitizedId)
            .replace("X", "0")
            .replace("Y", "1")
            .replace("Z", "2");

        return StringUtils.isNumeric(partialId) && hasValidControlCharacter(partialId);
    }

    private String stripControlCharacter(final String id) {
        return id.substring(0, id.length() - 1);
    }

    private boolean hasValidControlCharacter(final String partialId) {
        final int result = Integer.parseInt(partialId) % CONTROL_CHARACTERS.length();
        final char computedControlCharacter = CONTROL_CHARACTERS.charAt(result);

        return extractControlCharacter() == computedControlCharacter;
    }

    private char extractControlCharacter() {
        return sanitizedId.substring(sanitizedId.length() - 1).charAt(0);
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return id;
    }
}
