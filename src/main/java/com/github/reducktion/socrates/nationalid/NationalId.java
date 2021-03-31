package com.github.reducktion.socrates.nationalid;

import com.github.reducktion.socrates.extractor.Citizen;

public interface NationalId {

    boolean isValid();

    /**
     * May throw a {@link RuntimeException} exception if {@link #isValid()} returns false.
     */
    Citizen getCitizen();
}
