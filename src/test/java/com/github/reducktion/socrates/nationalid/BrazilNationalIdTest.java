package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Citizen;

class BrazilNationalIdTest {

    @Test
    void isValid_shouldReturnFalse_whenIdIsNull() {
        final BrazilNationalId brazilNationalId = new BrazilNationalId(null);

        final boolean result = brazilNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "1234567AB",    // not numeric
        "123456789012", // more than 11 digits
        "1234567890",   // less than 11 digits
        "23294954040"   // bad checksum
    })
    void isValid_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final BrazilNationalId brazilNationalId = new BrazilNationalId(notValidId);

        final boolean result = brazilNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
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
    void isValid_shouldReturnTrue_whenIdIsValid(final String validId) {
        final BrazilNationalId brazilNationalId = new BrazilNationalId(validId);

        final boolean result = brazilNationalId.isValid();

        assertThat(result, is(true));
    }

    @Test
    void extractCitizen_shouldReturnEmpty() {
        final BrazilNationalId brazilNationalId = new BrazilNationalId("17758534112");

        final Optional<Citizen> extractedCitizen = brazilNationalId.extractCitizen();

        assertThat(extractedCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "17758534112";
        final BrazilNationalId brazilNationalId = new BrazilNationalId(id);

        final String result = brazilNationalId.toString();

        assertThat(result, is(id));
    }
}
