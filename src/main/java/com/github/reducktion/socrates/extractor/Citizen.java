package com.github.reducktion.socrates.extractor;

import java.util.Objects;
import java.util.Optional;

/**
 * Class that represents a citizen, with the information extracted from the National Identification Number.
 */
public class Citizen {

    private final String gender;
    private final Integer yearOfBirth;
    private final Integer monthOfBirth;
    private final String placeOfBirth;

    public Citizen(
        final String gender,
        final Integer yearOfBirth,
        final Integer monthOfBirth,
        final String placeOfBirth
    ) {
        this.gender = gender;
        this.yearOfBirth = yearOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.placeOfBirth = placeOfBirth;
    }

    public Optional<String> getGender() {
        return Optional.ofNullable(gender);
    }

    public Optional<Integer> getYearOfBirth() {
        return Optional.ofNullable(yearOfBirth);
    }

    public Optional<Integer> getMonthOfBirth() {
        return Optional.ofNullable(monthOfBirth);
    }

    public Optional<String> getPlaceOfBirth() {
        return Optional.ofNullable(placeOfBirth);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final Citizen other = (Citizen) obj;

        return Objects.equals(gender, other.gender)
            && Objects.equals(yearOfBirth, other.yearOfBirth)
            && Objects.equals(monthOfBirth, other.monthOfBirth)
            && Objects.equals(placeOfBirth, other.placeOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, yearOfBirth, monthOfBirth, placeOfBirth);
    }

    @Override
    public String toString() {
        return "Citizen{"
            + "gender='" + gender + '\''
            + ", yearOfBirth=" + yearOfBirth
            + ", monthOfBirth=" + monthOfBirth
            + ", placeOfBirth='" + placeOfBirth + '\''
            + '}';
    }
}
