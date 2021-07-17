package com.github.reducktion.socrates.generator;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.reducktion.socrates.Country;

class NationalIdGeneratorFactoryTest {

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1}")
    @MethodSource("nationalIdGeneratorForCountryProvider")
    void newInstance_shouldReturnCorrectInstanceForCountry(final Country country, final Class clazz) {
        final NationalIdGenerator nationalIdGenerator = NationalIdGeneratorFactory.newInstance(country);

        assertThat(nationalIdGenerator, is(instanceOf(clazz)));
    }

    private static List<Arguments> nationalIdGeneratorForCountryProvider() {
        return Arrays.asList(
            Arguments.arguments(Country.DK, DenmarkNationalIdGenerator.class)
        );
    }
}
