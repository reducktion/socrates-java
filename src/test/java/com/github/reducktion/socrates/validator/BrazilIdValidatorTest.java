package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class BrazilIdValidatorTest {

    private BrazilIdValidator brazilIdValidator;

    @BeforeEach
    void setup() {
        brazilIdValidator = new BrazilIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(brazilIdValidator.validate(null), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasMoreThan11Characters() {
        assertThat(brazilIdValidator.validate("123456789012"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasLessThan11Characters() {
        assertThat(brazilIdValidator.validate("123456789"), is(false));
    }

    @Test
    void validate_shouldReturnFalse_whenIdHasAlphaCharacters() {
        assertThat(brazilIdValidator.validate("1234567AB"), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        // one per state
        "144-416-762.63",
        " 62363568400 ",
        "92205820230",
        "88958056231",
        "90701066555",
        "31098035348",
        "54271183148",
        "03860881795",
        "15777379117",
        "46959616360",
        "51861041675",
        "35823686102",
        "26319324120",
        "81036850463",
        "17188856443",
        "16556182451",
        "13369586347",
        "19319810940",
        "41120495792",
        "79950524482",
        "44667914068",
        "41947527240",
        "23554835234",
        "04008125922",
        "37025634581",
        "26363102820",
        "17758534112"
    })
    void validate_shouldReturnTrue_whenChecksumMatches(final String validId) {
        assertThat(brazilIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenChecksumDoesNotMatch() {
        assertThat(brazilIdValidator.validate("23294954040"), is(false));
    }
}
