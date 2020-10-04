package com.github.reducktion.socrates.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class ItalyOmocodiaSwapperTest {

    @Test
    void swap_shouldSwapCorrectCharacters() {
        assertThat(ItalyOmocodiaSwapper.swap("MRCDRALMAMPALSRE"), is("MRCDRA01A13A065E"));
    }
}
