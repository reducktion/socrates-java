package com.github.reducktion.socrates.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    void isNumeric_shouldReturnFalse_whenStringIsNull() {
        assertThat(StringUtils.isNumeric(null), is(false));
    }

    @Test
    void isNumeric_shouldReturnFalse_whenStringIsEmpty() {
        assertThat(StringUtils.isNumeric(""), is(false));
    }

    @Test
    void isNumeric_shouldReturnFalse_whenStringHasBlankSpaces() {
        assertThat(StringUtils.isNumeric(" "), is(false));
    }

    @Test
    void isNumeric_shouldReturnFalse_whenStringHasAlphaCharacters() {
        assertThat(StringUtils.isNumeric("123ABC"), is(false));
    }

    @Test
    void isNumeric_shouldReturnTrue_whenStringHasOnlyNumericCharacters() {
        assertThat(StringUtils.isNumeric("123"), is(true));
    }

    @Test
    void parseInt_shouldReturnEmptyOptional_whenStringIsNull() {
        assertThat(StringUtils.parseInt(null), is(Optional.empty()));
    }

    @Test
    void parseInt_shouldReturnEmptyOptional_whenStringNotNumeric() {
        assertThat(StringUtils.parseInt("A"), is(Optional.empty()));
    }

    @Test
    void parseInt_shouldReturnInteger_whenStringIsNumeric() {
        assertThat(StringUtils.parseInt("12"), is(Optional.of(12)));
    }
}
