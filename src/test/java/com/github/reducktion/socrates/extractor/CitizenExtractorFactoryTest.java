package com.github.reducktion.socrates.extractor;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.reducktion.socrates.Country;

class CitizenExtractorFactoryTest {

    @ParameterizedTest
    @MethodSource("extractorsForCountries")
    void getValidator_shouldReturnCorrectValidatorForCountry(final Country country, final Class clazz) {
        final CitizenExtractor citizenExtractor = CitizenExtractorFactory.getExtractor(country);

        assertThat(citizenExtractor, is(instanceOf(clazz)));
    }

    private static List<Arguments> extractorsForCountries() {
        return Arrays.asList(Arguments.arguments(Country.FR, FranceCitizenExtractor.class));
    }
}
