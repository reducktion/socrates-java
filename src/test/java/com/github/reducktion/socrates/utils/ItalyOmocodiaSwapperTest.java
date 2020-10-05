package com.github.reducktion.socrates.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class ItalyOmocodiaSwapperTest {

    @Test
    void swap_shouldReturnNull_whenIdIsNull() {
        assertThat(ItalyOmocodiaSwapper.swap(null), is(nullValue()));
    }

    @Test
    void swap_shouldReturnSameId_whenIdHasLengthGreaterThanSixteen() {
        final String id = "12345678901234567";
        assertThat(ItalyOmocodiaSwapper.swap(id), is(id));
    }

    @Test
    void swap_shouldReturnSameId_whenIdHasLengthLowerThanSixteen() {
        final String id = "123456789012345";
        assertThat(ItalyOmocodiaSwapper.swap(id), is(id));
    }

    @Test
    void swap_shouldSwapCorrectCharacters_whenIdHasLengthSixteenAndDoesNotHaveSpecialCharacters() {
        assertThat(ItalyOmocodiaSwapper.swap("MRCDRALMAMPALSRE"), is("MRCDRA01A13A065E"));
    }
}
