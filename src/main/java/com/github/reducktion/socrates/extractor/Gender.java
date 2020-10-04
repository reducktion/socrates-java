package com.github.reducktion.socrates.extractor;

public enum Gender {
    FEMALE("F"),
    MALE("M");

    private final String identifier;

    Gender(final String identifier) {
        this.identifier = identifier;
    }

    /**
     * Gender identifier.
     *
     * @return "F" if gender is {@link #FEMALE} and "M" if gender if {@link #MALE}
     */
    public String getIdentifier() {
        return identifier;
    }
}
