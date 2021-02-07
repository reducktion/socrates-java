package com.github.reducktion.socrates.nationalid;

import java.util.Optional;

import com.github.reducktion.socrates.Country;
import com.github.reducktion.socrates.extractor.Gender;

public interface NationalId {

    boolean isValid();

    Optional<Integer> getYearOfBirth();

    Optional<Integer> getMonthOfBirth();

    Optional<Integer> getDayOfBirth();

    Optional<Gender> getGender();

    Optional<String> getPlaceOfBirth();

    /**
     * Return a new instance of {@link NationalId}, that is specific for the country parameter.
     *
     * @param id the national id
     * @param country the {@link Country}
     * @return a new instance of {@link NationalId}
     * @throws UnsupportedOperationException if the country is not supported
     */
    static NationalId newInstance(final String id, final Country country) {
        switch (country) {
            case DK: return new DenmarkId(id);
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
