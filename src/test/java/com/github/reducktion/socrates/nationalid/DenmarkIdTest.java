package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Gender;

class DenmarkIdTest {

    @Test
    void validate_shouldReturnFalse_whenIdIsNull() {
        final DenmarkId denmarkId = new DenmarkId(null);

        final boolean result = denmarkId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "123456789A",  // not numeric
        "12345678901", // more than 10 digits
        "123456789",   // less than 10 digits
        "161301-0001", // month too high
        "311101-0001", // November has 30 days
        "321201-0001", // day too high
        "290201-0001", // 29 February only exists in leap year
        "2343212454"   // bad checksum
    })
    void validate_shouldReturnFalse_whenIdNotValid(final String notValidId) {
        final DenmarkId denmarkId = new DenmarkId(notValidId);

        final boolean result = denmarkId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        " 090792-1395 ",
        "0705930600",
        "1504373068",
        "1608881995",
        "0404047094"
    })
    void validate_shouldReturnTrue_whenIdIsValid(final String validId) {
        final DenmarkId denmarkId = new DenmarkId(validId);

        final boolean result = denmarkId.isValid();

        assertThat(result, is(true));
    }

    @Test
    void getYearOfBirth_shouldReturnEmptyOptional_whenIdNotValid() {
        final DenmarkId denmarkId = new DenmarkId(null);

        final Optional<Integer> yearOfBirth = denmarkId.getYearOfBirth();

        assertThat(yearOfBirth, is(Optional.empty()));
    }

    @Test
    void getYearOfBirth_shouldReturnValidYear_whenIdIsValid() {
        final DenmarkId denmarkId = new DenmarkId("0705930600");

        final Optional<Integer> yearOfBirth = denmarkId.getYearOfBirth();

        assertThat(yearOfBirth, is(Optional.of(1993)));
    }

    @Test
    void getMonthOfBirth_shouldReturnEmptyOptional_whenIdNotValid() {
        final DenmarkId denmarkId = new DenmarkId(null);

        final Optional<Integer> monthOfBirth = denmarkId.getMonthOfBirth();

        assertThat(monthOfBirth, is(Optional.empty()));
    }

    @Test
    void getMonthOfBirth_shouldReturnValidMonth_whenIdIsValid() {
        final DenmarkId denmarkId = new DenmarkId("0705930600");

        final Optional<Integer> monthOfBirth = denmarkId.getMonthOfBirth();

        assertThat(monthOfBirth, is(Optional.of(5)));
    }

    @Test
    void getDayOfBirth_shouldReturnEmptyOptional_whenIdNotValid() {
        final DenmarkId denmarkId = new DenmarkId(null);

        final Optional<Integer> dayOfBirth = denmarkId.getDayOfBirth();

        assertThat(dayOfBirth, is(Optional.empty()));
    }

    @Test
    void getDayOfBirth_shouldReturnValidDay_whenIdIsValid() {
        final DenmarkId denmarkId = new DenmarkId("0705930600");

        final Optional<Integer> dayOfBirth = denmarkId.getDayOfBirth();

        assertThat(dayOfBirth, is(Optional.of(7)));
    }

    @Test
    void getGender_shouldReturnEmptyOptional_whenIdNotValid() {
        final DenmarkId denmarkId = new DenmarkId(null);

        final Optional<Gender> gender = denmarkId.getGender();

        assertThat(gender, is(Optional.empty()));
    }

    @Test
    void getGender_shouldReturnValidGender_whenIdIsValid() {
        final DenmarkId denmarkId = new DenmarkId("0705930600");

        final Optional<Gender> gender = denmarkId.getGender();

        assertThat(gender, is(Optional.of(Gender.MALE)));
    }

    @Test
    void getPlaceOfBirth_shouldReturnEmptyOptional() {
        final DenmarkId denmarkId = new DenmarkId(null);

        final Optional<String> placeOfBirth = denmarkId.getPlaceOfBirth();

        assertThat(placeOfBirth, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnIdString() {
        final String id = "";
        final DenmarkId denmarkId = new DenmarkId(id);

        final String result = denmarkId.toString();

        assertThat(result, is(id));
    }
}
