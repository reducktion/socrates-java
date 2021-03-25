package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.extractor.Gender;

public interface NationalId {

    boolean isValid();

    /**
     * May throw a {@link RuntimeException} exception if {@link #isValid()} returns false.
     */
    Optional<Integer> getYearOfBirth();

    /**
     * May throw a {@link RuntimeException} exception if {@link #isValid()} returns false.
     */
    Optional<Integer> getMonthOfBirth();

    /**
     * May throw a {@link RuntimeException} exception if {@link #isValid()} returns false.
     */
    Optional<Integer> getDayOfBirth();

    /**
     * May throw a {@link RuntimeException} exception if {@link #isValid()} returns false.
     */
    Optional<Gender> getGender();

    /**
     * May throw a {@link RuntimeException} exception if {@link #isValid()} returns false.
     */
    Optional<String> getPlaceOfBirth();
}
