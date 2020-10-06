package com.github.reducktion.socrates.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class LuhnAlgorithmTest {

    private static final int BASE_36_RADIX = 36;

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(LuhnAlgorithm.validate(null), is(false));
        assertThat(LuhnAlgorithm.validate(null, BASE_36_RADIX), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenItUsesDefaultRadixAndIdHasAlphaCharacters() {
        assertThat(LuhnAlgorithm.validate("AB"), is(false));
    }

    @Test
    void validate_shouldReturnTrue_whenIdIsValid() {
        assertThat(LuhnAlgorithm.validate("1983081246783"), is(true));
        assertThat(LuhnAlgorithm.validate("79927398713"), is(true));
        assertThat(LuhnAlgorithm.validate("110841298ZX8", BASE_36_RADIX), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsInvalid() {
        assertThat(LuhnAlgorithm.validate("79927398712"), is(false));
        assertThat(LuhnAlgorithm.validate("154A03556ZX9", BASE_36_RADIX), is(false));
    }

    @Test
    void computeCheckDigit_shouldReturnEmptyOptional_whenIdIsNull() {
        assertThat(LuhnAlgorithm.computeCheckDigit(null), is(Optional.empty()));
        assertThat(LuhnAlgorithm.computeCheckDigit(null, BASE_36_RADIX), is(Optional.empty()));
    }

    @Test
    void computeCheckDigit_shouldReturnEmptyOptional_whenItUsesDefaultRadixAndIdHasAlphaCharacters() {
        assertThat(LuhnAlgorithm.computeCheckDigit("AB"), is(Optional.empty()));
    }

    @Test
    void computeCheckDigit_shouldReturnCorrectCheckDigit() {
        assertThat(LuhnAlgorithm.computeCheckDigit("7992739871").get(), is(3));
        assertThat(LuhnAlgorithm.computeCheckDigit("154203556ZX9", BASE_36_RADIX).get(), is(9));
    }
}
