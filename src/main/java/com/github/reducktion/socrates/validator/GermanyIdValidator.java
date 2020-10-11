package com.github.reducktion.socrates.validator;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

/**
 * A national identification number does not really exists within Germany.
 * But there's a tax ident number, which is unique for each citizen and assigned by date of birth.
 * This one is restricted by law to be used as a marker/attribute for tax/finance relevant purposes only,
 * and not to identify people in general.
 * It's called 'steuerliche Identifikationsnummer' or abbreviated 'Steuer-IdNr.'.
 * There's no meta data, which could be extracted from this number.
 *
 * See https://de.wikipedia.org/wiki/Steuerliche_Identifikationsnummer
 * The algorithm is published in an official document
 * from: Informations Technik Zentrum Bund
 * title: Steueridentifikationsnummer (IdNr) nach § 139b AO; Informationen zur Berechnung gültiger Prüfziffern
 */
public class GermanyIdValidator implements IdValidator {

    private static final int ID_NUMBER_OF_CHARACTERS = 11;
    private static final int CHECKSUM_INDEX = 10;

    @Override
    public boolean validate(String value) {
        if (value == null) return false;
        if (value.length() != ID_NUMBER_OF_CHARACTERS) return false;
        if (containsNonDigits(value)) return false;
        if (hasTestIdentifierPrefix(value)) return false;
        String identifier = value.substring(0, 10);
        String calculatedChecksum = Integer.toString(calculateCheckDigit(identifier));
        String givenChecksum = Character.toString(value.charAt(CHECKSUM_INDEX));
        if (!calculatedChecksum.equals(givenChecksum)) return false;
        if (hasMoreThan3EqualDigits(identifier)) return false;
        return !hasThreeOrMoreConsecutiveDigits(identifier);
    }

    private boolean hasTestIdentifierPrefix(String id) {
        return id.charAt(0) == '0';
    }

    private boolean containsNonDigits(String id) {
        return !id.chars().allMatch(Character::isDigit);
    }

    /**
     * == this is the official source code from the document, please don't modify ==
     * <p>
     * This method calculates the check digit for a German tax identification
     * number (IdNr).
     * Based on finding the division remainder (modulo calculation) the check
     * digit depends on each preceding numeric digit on position 1 to 10.
     * The chosen way of calculation combines and applies two commonly used modulo
     * operations. For each digit on positions 1 to 10 the remainders of both
     * divisions by eleven and ten are used in consecutive arithmetic steps.
     * The calculation result must correspond with the numeric digit on position 11.
     * This approach considers each digit in a specific way for being able
     * to detect falsely edited IdNr in a reliable and efficient way.
     *
     * @param idnrString -- a string consisting of ten numeric digits
     * @return -- calculated check digit returned as an integer value
     * @see <a href="https://www.zfa.deutsche-rentenversicherung-bund.de/de/Inhalt/public/4_ID/47_Pruefziffernberechnung/001_Pruefziffernberechnung.pdf">
     * Informations Technik Zentrum Bund
     * Steueridentifikationsnummer (IdNr) nach § 139b AO
     * Informationen zur Berechnung gültiger Prüfziffern
     * </a>
     */
    private int calculateCheckDigit(String idnrString) {
        final int ten = 10;
        final int eleven = 11;
        char[] chars = idnrString.toCharArray();
        int remainder_mod_ten = 0;
        int remainder_mod_eleven = ten;
        int digit = 0;
        final int length = idnrString.length();
        for (int counter = 0; counter < length; counter++) {
            digit = Character.getNumericValue(chars[counter]);
            remainder_mod_ten = (digit + remainder_mod_eleven) % ten;
            if (remainder_mod_ten == 0)
                remainder_mod_ten = ten;
            remainder_mod_eleven = (2 * remainder_mod_ten) % eleven;
        } // for
        digit = eleven - remainder_mod_eleven;
        if (digit == 10)
            digit = 0;
        return digit;
    }

    private boolean hasMoreThan3EqualDigits(String id) {
        Optional<Long> occurrences = calculateDigitFrequency(id).values().stream()
            .filter(l -> l > 3)
            .findFirst();
        return occurrences.isPresent();
    }

    private Map<String, Long> calculateDigitFrequency(String id) {
        return id.chars().mapToObj(String::valueOf)
            .collect(groupingBy(Function.identity(), counting()));
    }

    private boolean hasThreeOrMoreConsecutiveDigits(String id) {
        int counter = 0;
        char lastChar = '?';
        for (char c : id.toCharArray()) {
            if (c == lastChar) {
                counter++;
            } else {
                counter = 1;
            }
            lastChar = c;
            if (counter >= 3) return true;
        }
        return false;
    }
}
