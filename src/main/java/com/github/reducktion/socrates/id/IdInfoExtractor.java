package com.github.reducktion.socrates.id;

import java.util.Optional;

import com.github.reducktion.socrates.extractor.Gender;

public interface IdInfoExtractor {

    Optional<Integer> getYearOfBirth();

    Optional<Integer> getMonthOfBirth();

    Optional<Integer> getDayOfBirth();

    Optional<Gender> getGender();

    Optional<String> getPlaceOfBirth();
}
