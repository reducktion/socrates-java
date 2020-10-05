package com.github.reducktion.socrates.extractor;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class GenderTest {

    @Test
    void getShortHand_shouldReturnF_whenGenderIsFemale() {
        assertThat(Gender.FEMALE.getShortHand(), is("F"));
    }

    @Test
    void getShortHand_shouldReturnM_whenGenderIsMale() {
        assertThat(Gender.MALE.getShortHand(), is("M"));
    }
}
