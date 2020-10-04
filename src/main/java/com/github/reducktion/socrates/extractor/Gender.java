package com.github.reducktion.socrates.extractor;

public enum Gender {
    FEMALE("F"),
    MALE("M");

    private final String identifier;

    Gender(final String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }
}
