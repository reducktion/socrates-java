package com.github.reducktion.socrates.extractor;

import com.github.reducktion.socrates.extractor.exceptions.InvalidIdException;
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
     * @return the {@link Citizen}, with the information extracted
     * @throws InvalidIdException if the {@code id} is invalid
     */
    Citizen extractFromId(String id, IdValidator idValidator) throws InvalidIdException;
}
