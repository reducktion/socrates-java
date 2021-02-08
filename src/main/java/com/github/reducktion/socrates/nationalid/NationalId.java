package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.extractor.Gender;

public interface NationalId {

    boolean isValid();

    Optional<Integer> getYearOfBirth();

    Optional<Integer> getMonthOfBirth();

    Optional<Integer> getDayOfBirth();

    Optional<Gender> getGender();

    Optional<String> getPlaceOfBirth();
}
