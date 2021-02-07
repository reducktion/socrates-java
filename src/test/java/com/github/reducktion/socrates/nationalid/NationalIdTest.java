package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.reducktion.socrates.Country;

class NationalIdTest {

    @ParameterizedTest(name = "#{index} - Test with Argument={0},{1},{2}")
    @MethodSource("nationalIdForCountries")
    void newInstance_shouldReturnCorrectInstanceForCountry(final String id, final Country country, final Class clazz) {
        final NationalId nationalId = NationalId.newInstance(id, country);

        assertThat(nationalId, is(instanceOf(clazz)));
    }

    private static List<Arguments> nationalIdForCountries() {
        return Arrays.asList(
            Arguments.arguments(null, Country.DK, DenmarkId.class)
        );
    }
}
