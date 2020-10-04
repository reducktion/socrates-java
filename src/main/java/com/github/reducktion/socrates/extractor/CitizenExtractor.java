package com.github.reducktion.socrates.extractor;

import java.util.Optional;

import com.github.reducktion.socrates.validator.IdValidator;

/**
 * {@link Citizen} extractor.
 */
public interface CitizenExtractor {

    /**
     * Extracts a {@link Citizen} from the National Identification Number.
     *
     * @param id the national identification number
     * @param idValidator the national identification number validator
     * @return the {@link Citizen} wrapped in an {@link Optional}, if the {@code id} is considered valid
     *         by the {@code idValidator}
     */
    Optional<Citizen> extractFromId(String id, IdValidator idValidator);
}
