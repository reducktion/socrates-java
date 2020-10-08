package com.github.reducktion.socrates.validator;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MexicoValidatorTest {

    private MexicoIdValidator mexicoIdValidator;

    @BeforeEach
    void setup() {
        mexicoIdValidator = new MexicoIdValidator();
    }

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        assertThat(mexicoIdValidator.validate(null), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "12345678901234567",
        "1234567890123456789"
    })
    void validate_shouldReturnFalse_whenIdLengthIsNot18(final String idOfInvalidLength) {
        assertThat(mexicoIdValidator.validate(idOfInvalidLength), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "BUEI000000AAAAAA01",
        "CACA000000AAAAAA01",
        "CAGA000000AAAAAA01",
        "CAKA000000AAAAAA01",
        "COGE000000AAAAAA01",
        "COJE000000AAAAAA01",
        "COJO000000AAAAAA01",
        "FETO000000AAAAAA01",
        "JOTO000000AAAAAA01",
        "KACO000000AAAAAA01",
        "KAGO000000AAAAAA01",
        "KOJO000000AAAAAA01",
        "KULO000000AAAAAA01",
        "MAMO000000AAAAAA01",
        "MEAS000000AAAAAA01",
        "MION000000AAAAAA01",
        "MULA000000AAAAAA01",
        "PEDO000000AAAAAA01",
        "PUTA000000AAAAAA01",
        "RUIN000000AAAAAA01",
        "BUEY000000AAAAAA01",
        "CACO000000AAAAAA01",
        "CAGO000000AAAAAA01",
        "CAKO000000AAAAAA01",
        "COJA000000AAAAAA01",
        "COJI000000AAAAAA01",
        "CULO000000AAAAAA01",
        "GUEY000000AAAAAA01",
        "KACA000000AAAAAA01",
        "KAGA000000AAAAAA01",
        "KOGE000000AAAAAA01",
        "KAKA000000AAAAAA01",
        "MAME000000AAAAAA01",
        "MEAR000000AAAAAA01",
        "MEON000000AAAAAA01",
        "MOCO000000AAAAAA01",
        "PEDA000000AAAAAA01",
        "PENE000000AAAAAA01",
        "PUTO000000AAAAAA01",
        "RATA000000AAAAAA01",
    })
    void validate_shouldReturnFalse_whenIdContainsInappropriateWord(final String idWithInappropriateWord) {
        assertThat(mexicoIdValidator.validate(idWithInappropriateWord), is(false));
    }

    @ParameterizedTest
    @ValueSource(strings = {
        " AAIM901112MBCNMN08 ",
        "JOIM890106HHGSMN08",
        "JOTA950616HBCSWS03",
        "AAIT101109MHGNMN01"
    })
    void validate_shouldReturnTrue_whenControlDigitMatches(final String validId) {
        assertThat(mexicoIdValidator.validate(validId), is(true));
    }

    @Test
    void validate_shouldReturnFalse_whenControlCharacterDoNotMatch() {
        assertThat(mexicoIdValidator.validate("BMHM260906HCHQAN04"), is(false));
    }
}
