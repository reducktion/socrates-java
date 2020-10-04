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
    private final Integer dayOfBirth;
    private final String placeOfBirth;

    public Optional<String> getGender() {
        return Optional.ofNullable(gender);
    }

    public Optional<Integer> getYearOfBirth() {
        return Optional.ofNullable(yearOfBirth);
    }

    public Optional<Integer> getMonthOfBirth() {
        return Optional.ofNullable(monthOfBirth);
    }

    public Optional<Integer> getDayOfBirth() {
        return Optional.ofNullable(dayOfBirth);
    }

    public Optional<String> getPlaceOfBirth() {
        return Optional.ofNullable(placeOfBirth);
    }

    public static Citizen.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String gender;
        private Integer yearOfBirth;
        private Integer monthOfBirth;
        private Integer dayOfBirth;
        private String placeOfBirth;

        public Builder gender(final String gender) {
            this.gender = gender;
            return this;
        }

        public Builder yearOfBirth(final Integer yearOfBirth) {
            this.yearOfBirth = yearOfBirth;
            return this;
        }

        public Builder monthOfBirth(final Integer monthOfBirth) {
            this.monthOfBirth = monthOfBirth;
            return this;
        }

        public Builder dayOfBirth(final Integer dayOfBirth) {
            this.dayOfBirth = dayOfBirth;
            return this;
        }

        public Builder placeOfBirth(final String placeOfBirth) {
            this.placeOfBirth = placeOfBirth;
            return this;
        }

        public Citizen build() {
            return new Citizen(this);
        }
    }

    private Citizen(final Builder builder) {
        gender = builder.gender;
        yearOfBirth = builder.yearOfBirth;
        monthOfBirth = builder.monthOfBirth;
        dayOfBirth = builder.dayOfBirth;
        placeOfBirth = builder.placeOfBirth;
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
            && Objects.equals(dayOfBirth, other.dayOfBirth)
            && Objects.equals(placeOfBirth, other.placeOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gender, yearOfBirth, monthOfBirth, dayOfBirth, placeOfBirth);
    }

    @Override
    public String toString() {
        return "Citizen{"
            + "gender='" + gender + '\''
            + ", yearOfBirth=" + yearOfBirth
            + ", monthOfBirth=" + monthOfBirth
            + ", dayOfBirth=" + dayOfBirth
            + ", placeOfBirth='" + placeOfBirth + '\''
            + '}';
    }
}
