package com.github.reducktion.socrates.extractor;

import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Country;
import com.github.reducktion.socrates.validator.IdValidator;

/**
 * {@link Citizen} extractor.
 *
 * @deprecated Use class NationalId instead
 */
@Deprecated
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

    /**
     * Return a new instance of {@link CitizenExtractor}, that is specific for the country parameter.
     *
     * @param country the {@link Country}
     * @return a new instance of {@link CitizenExtractor}
     * @throws UnsupportedOperationException if the country is not supported
     */
    static CitizenExtractor newInstance(final Country country) {
        switch (country) {
            case BE: return new BelgiumCitizenExtractor();
            case DK: return new DenmarkCitizenExtractor();
            case FR: return new FranceCitizenExtractor();
            case IT: return new ItalyCitizenExtractor();
            case MX: return new MexicoCitizenExtractor();
            default: throw new UnsupportedOperationException("Country not supported.");
        }
    }
}
