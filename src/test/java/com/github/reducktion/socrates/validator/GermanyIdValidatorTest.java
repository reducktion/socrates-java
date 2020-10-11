package com.github.reducktion.socrates.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GermanyIdValidatorTest {
    private GermanyIdValidator germanyIdValidator;

    @BeforeEach
    void setup() {
        germanyIdValidator = new GermanyIdValidator();
    }

    /**
     * These valid numbers are taken from the official document,
     * mentioned in the JavaDoc of $GermanyIdValidator#validate
     *
     * @param validId an ID to test
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "86095742719",
        "47036892816",
        "65929970489",
        "57549285017",
        "25768131411"
    })
    void these_valid_numbers_should_pass_the_test(String validId) {
        assertThat(germanyIdValidator.validate(validId), is(true));
    }

    @Test
    void a_leading_zero_indicates_a_test_ID_and_is_not_allowed() {
        assertThat(
            germanyIdValidator.validate("02476291358"),
            is(false)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "44441234560",
        "55555234569",
        "66666634561",
        "77777774564",
        "88888888566",
        "99999999960",
        "11111111119",
    })
    void no_digit_occurs_more_than_3_times(String id) {
        assertThat(germanyIdValidator.validate(id), is(false));
    }

    @Test
    void if_3_times_a_digit_exists_they_must_be_not_consecutive() {
        assertThat(
            germanyIdValidator.validate("11145678908"),
            is(false)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "11215678903",
        "12115678907",
    })
    void digits_can_occur_3_times_when_not_consecutive(String id) {
        assertThat(germanyIdValidator.validate(id), is(true));
    }
}
