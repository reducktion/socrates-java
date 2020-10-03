package com.github.reducktion.socrates.extractor;

import com.github.reducktion.socrates.Country;

public class CitizenExtractorFactory {

    private CitizenExtractorFactory() {}

    public static CitizenExtractor getExtractor(final Country country) {
        switch (country) {
            case FR: return new FranceCitizenExtractor();
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
