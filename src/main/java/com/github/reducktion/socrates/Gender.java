package com.github.reducktion.socrates;

public enum Gender {
    FEMALE("F"),
    MALE("M");

    private final String shortHand;

    Gender(final String shortHand) {
        this.shortHand = shortHand;
    }

    /**
     * Gender's short hand.
     *
     * @return "F" if gender is {@link #FEMALE} and "M" if gender if {@link #MALE}
     */
    public String getShortHand() {
        return shortHand;
    }
}
