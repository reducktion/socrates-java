package com.github.reducktion.socrates.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
}
