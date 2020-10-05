package com.github.reducktion.socrates.utils;

/**
 * When the alphanumeric expression relating to the first fifteen characters of the code is common to two or more
 * subjects, it is differentiated for each of the subjects following the first coded subject.
 * <p>
 * To this end, within the seven numerical characters contained in the code, systematic substitutions of one or
 * more digits starting from the right one are carried out with corresponding alphabetic characters.
 * <p>
 * The algorithm for Italy is explained in http://www.dossier.net/utilities/codice-fiscale/decreto1974_2227.html
 * (art. 6).
 */
public class ItalyOmocodiaSwapper {

    private static final int ID_NUMBER_OF_CHARACTERS = 16;
    private static final int[] NUMERICAL_CHARACTER_POSITIONS = {6, 7, 9, 10, 12, 13, 14};
    private static final String NUMERICAL_CHARACTER_SUBSTITUTIONS = "LMNPQRSTUV";

    private ItalyOmocodiaSwapper() {}

    /**
     * Swap characters according to the algorithm for Italy.
     * <p>
     * <b>Note:</b> The {@code id} must not contain spaces or other special characters and the alpha characters must be
     * upper case.
     *
     * @param id the identification number
     * @return the identification number with the characters swapped or {@code id} if the {@code id} is either null or
     *         does not have 16 characters
     */
    public static String swap(final String id) {
        if (id == null || id.length() != ID_NUMBER_OF_CHARACTERS) {
            return id;
        }

        final char[] idCharArray = id.toCharArray();

        for (final int i : NUMERICAL_CHARACTER_POSITIONS) {
            if (Character.isAlphabetic(idCharArray[i])) {
                idCharArray[i] = (char) (NUMERICAL_CHARACTER_SUBSTITUTIONS.indexOf(idCharArray[i]) + '0');
            }
        }
        return String.valueOf(idCharArray);
    }
}
