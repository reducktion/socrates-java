package com.github.reducktion.socrates.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class VerhoeffAlgorithmTest {

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(VerhoeffAlgorithm.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasAlphaCharacters() {
        assertThat(VerhoeffAlgorithm.validate("AB"), is(false));
    }

    @Test
    void validate_shouldReturnTrue_whenIdIsValid() {
        assertThat(VerhoeffAlgorithm.validate("2363"), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsInvalid() {
        assertThat(VerhoeffAlgorithm.validate("5971654782313"), is(false));
    }

    @Test
    void computeCheckDigit_shouldReturnEmptyOptional_whenIdHasAlphaCharacters() {
        assertThat(VerhoeffAlgorithm.computeCheckDigit("AB"), is(Optional.empty()));
    }

    @Test
    void computeCheckDigit_shouldReturnCorrectCheckDigit() {
        assertThat(VerhoeffAlgorithm.computeCheckDigit("236").get(), is(3));
    }
}
