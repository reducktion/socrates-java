package com.github.reducktion.socrates.validator;

/**
 * National Identification Number validator for Portugal.
 *
 * This validation algorithm is based on the official documentation released in 26 of January of 2009:
 * https://www.autenticacao.gov.pt/documents/20126/115760/Valida%C3%A7%C3%A3o+de+N%C3%BAmero+de+Documento+do+Cart%C3%A3o+de+Cidad%C3%A3o.pdf/bdc4eb37-7316-3ff4-164a-f869382b7053
 **/
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

        int sum = 0;
        boolean everyOtherDigit = false;

        for (int i = sanitizedId.length() - 1; i >= 0; --i) {
            int value = Character.digit(sanitizedId.charAt(i), BASE_36_RADIX);

            if (everyOtherDigit) {
                value *= 2;

                if (value > 9) {
                    value -= 9;
                }
            }
            sum += value;
            everyOtherDigit = !everyOtherDigit;
        }

        return (sum % 10) == 0;
    }

    private String sanitize(final String id) {
        return id
            .replace(" ", "")
            .toUpperCase();
    }
}
