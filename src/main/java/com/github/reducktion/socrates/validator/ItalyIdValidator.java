package com.github.reducktion.socrates.validator;

import com.github.reducktion.socrates.internal.ItalyOmocodiaSwapper;

/**
 * National Identification Number validator for Italy.
 *
 * This validation algorithm is based on the Italy's Ministry of Finance Decree of 12/03/1974 n. 2227:
 * http://www.dossier.net/utilities/codice-fiscale/decreto1974_2227.html
 *
 * An english version is available in wikipedia: https://en.wikipedia.org/wiki/Italian_fiscal_code
 */
class ItalyIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 16;

    @Override
    public boolean validate(final String id) {
        if (id == null) {
            return false;
        }

        final String sanitizedId = ItalyOmocodiaSwapper.swap(sanitize(id));

        if (sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        return validateControlCharacter(sanitizedId);
    }

    private String sanitize(final String id) {
        return id
            .replace(" ", "")
            .toUpperCase();
    }

    private boolean validateControlCharacter(final String id) {
        final String expectedControlCharacter = getControlCharacter(id);
        final String computedControlCharacter = computeControlCharacter(id);

        return expectedControlCharacter.equals(computedControlCharacter);
    }

    private String getControlCharacter(final String id) {
        return id.substring(id.length() - 1);
    }

    private String computeControlCharacter(final String id) {
        boolean isOdd = true;
        int sum = 0;

        final String partialId = stripControlCharacter(id);

        for (final String character : partialId.split("")) {
            if (isOdd) {
                sum += getValueForOddCharacter(character);
            } else {
                sum += getValueForEvenCharacter(character);
            }

            isOdd = !isOdd;
        }

        return getRemainderCharacterForValue(sum % 26);
    }

    private String stripControlCharacter(final String id) {
        return id.substring(0, id.length() - 1);
    }

    private int getValueForOddCharacter(final String oddCharacter) {
        switch (oddCharacter) {
            case "0": case "A": return 1;
            case "1": case "B": return 0;
            case "2": case "C": return 5;
            case "3": case "D": return 7;
            case "4": case "F": return 9;
            case "5": case "E": return 13;
            case "6": case "G": return 15;
            case "7": case "H": return 17;
            case "8": case "I": return 19;
            case "9": case "J": return 21;
            case "K": return 2;
            case "L": return 4;
            case "M": return 18;
            case "N": return 20;
            case "O": return 11;
            case "P": return 3;
            case "Q": return 6;
            case "R": return 8;
            case "S": return 12;
            case "T": return 14;
            case "U": return 16;
            case "V": return 10;
            case "W": return 22;
            case "X": return 25;
            case "Y": return 24;
            case "Z": return 23;
            default: return -1; // will make validation fail
        }
    }

    private int getValueForEvenCharacter(final String evenCharacter) {
        final int value = Character.getNumericValue(evenCharacter.charAt(0));

        return value > 9 ? value - 10 : value;
    }

    private String getRemainderCharacterForValue(final int value) {
        return String.valueOf((char) (value + 65));
    }
}
