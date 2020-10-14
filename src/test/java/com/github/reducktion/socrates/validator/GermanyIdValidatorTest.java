package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class GermanyIdValidatorTest {
    private GermanyIdValidator germanyIdValidator;

    @BeforeEach
    void setup() {
        germanyIdValidator = new GermanyIdValidator();
    }

    /**
     * These valid numbers are taken from the official document,
     * mentioned in the JavaDoc of {@link GermanyIdValidator#validate}.
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
    void validate_shouldReturnTrue_whenIdMatchesDocumentedOne(final String validId) {
        assertThat(germanyIdValidator.validate(validId), is(true));
    }

    @Test
    // a leading zero indicates a test and it is not allowed
    void validate_shouldReturnFalse_whenIdContainsLeadingZero() {
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
    void validate_shouldReturnFalse_whenNoDigitOccursMoreThan3Times(final String id) {
        assertThat(germanyIdValidator.validate(id), is(false));
    }

    @Test
    void validate_shouldReturnFalse_when3TimesaDigitExistsTheyMustBeNotConsecutive() {
        assertThat(
            germanyIdValidator.validate("11145678908"),
            is(false)
        );
    }

    @Test
    void validate_shouldReturnFalse_whenNullIsGiven() {
        assertThat(
            germanyIdValidator.validate(null),
            is(false)
        );
    }

    @Test
    void validate_shouldReturnFalse_whenCharactersAreGiven() {
        assertThat(
            germanyIdValidator.validate("random string with characters"),
            is(false)
        );
    }

    @Test
    void validate_shouldReturnFalse_whenMoreThan11DigitsAreGiven() {
        assertThat(
            germanyIdValidator.validate("123456789012"),
            is(false)
        );
    }

    @Test
    void validate_shouldReturnTrue_whenTrailingOrLeadingSpacesAreGiven() {
        assertThat(
            germanyIdValidator.validate(" 86095742719 "),
            is(true)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "11215678903",
        "12115678907",
    })
    void validate_shouldReturnTrue_whenDigitsOccur3TimesNotConsecutive(final String id) {
        assertThat(germanyIdValidator.validate(id), is(true));
    }
}
