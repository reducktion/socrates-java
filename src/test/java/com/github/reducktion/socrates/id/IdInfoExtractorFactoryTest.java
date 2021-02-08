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

class IdInfoExtractorFactoryTest {

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1}")
    @MethodSource("idForCountry")
    void getNationalIdInfoExtractor_shouldReturnCorrectInstanceForCountry(final Country country, final Class clazz) {
        final IdInfoExtractor idInfoExtractor = NationalIdInfoExtractorFactory.getNationalIdInfoExtractor(null, country);

        assertThat(idInfoExtractor, is(instanceOf(clazz)));
    }

    private static List<Arguments> idForCountry() {
        return Arrays.asList(
            Arguments.arguments(Country.DK, DenmarkId.class)
        );
    }
}
