package com.github.reducktion.socrates.id;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.reducktion.socrates.Country;

class IdValidatorFactoryTest {

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("idForCountry")
    void getNationalIdValidator_shouldReturnCorrectInstanceForCountry(final Country country, final Class clazz) {
        final IdValidator idValidator = NationalIdValidatorFactory.getNationalIdValidator(null, country);

        assertThat(idValidator, is(instanceOf(clazz)));
    }

    private static List<Arguments> idForCountry() {
        return Arrays.asList(
            Arguments.arguments(Country.DK, DenmarkId.class)
        );
    }
}
