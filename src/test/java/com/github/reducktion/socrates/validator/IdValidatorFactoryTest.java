package com.github.reducktion.socrates.validator;

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

    @ParameterizedTest
    @MethodSource("validatorsForCountries")
    void getValidator_shouldReturnCorrectValidatorForCountry(final Country country, final Class clazz) {
        final IdValidator idValidator = IdValidatorFactory.getValidator(country);

        assertThat(idValidator, is(instanceOf(clazz)));
    }

    private static List<Arguments> validatorsForCountries() {
        return Arrays.asList(Arguments.arguments(Country.ES, SpainIdValidator.class));
    }
}
