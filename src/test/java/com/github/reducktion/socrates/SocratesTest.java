package com.github.reducktion.socrates;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.extractor.Gender;

class SocratesTest {

    private Socrates socrates;

    @BeforeEach
    void setup() {
        socrates = new Socrates();
    }

    @Test
    void validateId_shouldReturnFalse_whenIdForFranceIsInvalid() {
        assertThat(socrates.validateId("103162989566972", Country.FR), is(false));
    }

    @Test
    void validateId_shouldReturnTrue_whenIdForFranceIsValid() {
        assertThat(socrates.validateId(" 2820819398814 09 ", Country.FR), is(true));
    }

    @Test
    void extractCitizenFromId_shouldReturnEmptyOptional_whenIdForItalyIsInvalid() {
        assertThat(socrates.extractCitizenFromId("MECDRE01A11A025E", Country.IT), is(Optional.empty()));
    }

    @Test
    void extractCitizenFromId_shouldReturnCitizen_whenIdForItalyIsValid() {
        final Citizen expectedCitizen = Citizen
            .builder()
            .gender(Gender.MALE)
            .yearOfBirth(1925)
            .monthOfBirth(4)
            .dayOfBirth(9)
            .placeOfBirth("MILANO (MI)")
            .build();

        final Optional<Citizen> result = socrates.extractCitizenFromId(" MRTMTT25D09F20 5Z ", Country.IT);

        assertThat(result.isPresent(), is(true));
        assertThat(result.get(), is(expectedCitizen));
    }

    @Test
    void generateIdFromCitizen_shouldReturnId_whenDenmarkCitizenIsValid() {
        final Citizen citizen = Citizen
            .builder()
            .gender(Gender.MALE)
            .yearOfBirth(1991)
            .monthOfBirth(6)
            .dayOfBirth(16)
            .gender(Gender.MALE)
            .build();

        final String id = socrates.generateId(citizen, Country.DK);

        assertThat(id, is("160691-3113"));
    }
}
