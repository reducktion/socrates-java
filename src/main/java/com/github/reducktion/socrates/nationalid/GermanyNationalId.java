package com.github.reducktion.socrates.nationalid;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.internal.StringUtils;

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
final class GermanyNationalId extends NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 11;

    public GermanyNationalId(final String id) {
        super(id);
    }

    @Override
    public boolean isValid() {
        if (sanitizedId == null
            || sanitizedId.length() != ID_NUMBER_OF_CHARACTERS
            || !StringUtils.isNumeric(sanitizedId)
            || hasTestIdentifierPrefix()
        ) {
            return false;
        }

        return hasValidChecksum()
            && !hasMoreThan3EqualDigits()
            && !hasThreeOrMoreConsecutiveDigits();
    }

    // id's with leading zeros indicate a test and are not considered valid
    private boolean hasTestIdentifierPrefix() {
        return sanitizedId.charAt(0) == '0';
    }

    private boolean hasValidChecksum() {
        final String expectedChecksum = sanitizedId.substring(10, 11);
        final String computedChecksum = computeCheckDigit();
        return expectedChecksum.equals(computedChecksum);
    }

    /**
     * == this is the official source code from the document, please don't modify ==
     * <p>
     * This method computes the check digit for a German tax identification
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
     * @return computed check digit returned as an string value
     * @see <a href="https://www.zfa.deutsche-rentenversicherung-bund.de/de/Inhalt/public/4_ID/47_Pruefziffernberechnung/001_Pruefziffernberechnung.pdf">
     * Informations Technik Zentrum Bund
     * Steueridentifikationsnummer (IdNr) nach § 139b AO
     * Informationen zur Berechnung gültiger Prüfziffern
     * </a>
     */
    private String computeCheckDigit() {
        final String idNr = extractIdNr();
        final int ten = 10;
        final int eleven = 11;
        final char[] chars = idNr.toCharArray();
        int remainderModTen = 0;
        int remainderModEleven = ten;
        int digit = 0;
        final int length = idNr.length();
        for (int counter = 0; counter < length; counter++) {
            digit = Character.getNumericValue(chars[counter]);
            remainderModTen = (digit + remainderModEleven) % ten;
            if (remainderModTen == 0) {
                remainderModTen = ten;
            }
            remainderModEleven = (2 * remainderModTen) % eleven;
        } // for
        digit = eleven - remainderModEleven;
        if (digit == 10) {
            digit = 0;
        }
        return String.valueOf(digit);
    }

    /**
     * Return the German tax identification number (IdNr).
     *
     * @return the IdNr, which is a string consisting of ten numeric digits
     */
    private String extractIdNr() {
        return sanitizedId.substring(0, 10);
    }

    private boolean hasMoreThan3EqualDigits() {
        final Optional<Long> occurrences = computeDigitFrequency().values().stream()
            .filter(l -> l > 3)
            .findFirst();
        return occurrences.isPresent();
    }

    private Map<String, Long> computeDigitFrequency() {
        final String idNr = extractIdNr();
        return idNr.chars().mapToObj(String::valueOf)
            .collect(groupingBy(Function.identity(), counting()));
    }

    private boolean hasThreeOrMoreConsecutiveDigits() {
        final String idNr = extractIdNr();
        int counter = 0;
        char lastChar = '?';
        for (final char c : idNr.toCharArray()) {
            if (c == lastChar) {
                counter++;
            } else {
                counter = 1;
            }
            lastChar = c;
            if (counter >= 3) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        return Optional.empty();
    }
}
