package com.github.reducktion.socrates;

import java.util.Optional;

import com.github.reducktion.socrates.generator.NationalIdGenerator;
import com.github.reducktion.socrates.generator.NationalIdGeneratorFactory;
import com.github.reducktion.socrates.nationalid.NationalId;
import com.github.reducktion.socrates.nationalid.NationalIdFactory;

/**
 * Socrates allows you to validate and retrieve personal data from National Identification Numbers across the world.
 */
public class Socrates {

    /**
     * Validates the National Identification Number.
     *
     * @param id the national identification number
     * @param country the country of the national identification number
     * @return true if the {@code id} is valid, false otherwise
     * @throws UnsupportedOperationException if the country is not supported
     */
    public boolean validateId(final String id, final Country country) {
        final NationalId nationalId = NationalIdFactory.newInstance(id, country);
        return nationalId.isValid();
    }

    /**
     * Extracts the {@link Citizen} from the National Identification Number.
     *
     * @param id the national identification number
     * @param country the country of the national identification number
     * @return the {@link Citizen} wrapped in an {@link Optional}, if the {@code id} is valid
     * @throws UnsupportedOperationException if the country is not supported
     */
    public Optional<Citizen> extractCitizenFromId(final String id, final Country country) {
        final NationalId nationalId = NationalIdFactory.newInstance(id, country);
        return nationalId.extractCitizen();
    }

    /**
     * Generates an National Identification Number based on a citizen information.
     *
     * @param citizen the citizen information
     * @param country the country of the national identification number
     * @return national identifier string
     * @throws UnsupportedOperationException if the country is not supported
     */
    public String generateId(final Citizen citizen, final Country country) {
        final NationalIdGenerator nationalIdGenerator = NationalIdGeneratorFactory.newInstance(country);
        return nationalIdGenerator.generate(citizen);
    }
}
