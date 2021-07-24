package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.github.reducktion.socrates.Country;

/**
 * Used to test base final methods in superclass {@link NationalId}.
 */
class NationalIdTest {

    @ParameterizedTest(name = "#{index} - Test with Arguments={0}")
    @MethodSource("countryProvider")
    void toString_shouldReturnNull_whenIdIsNull(final Country country) {
        final NationalId nationalId = NationalIdFactory.newInstance(null, country);

        final String result = nationalId.toString();

        assertThat(result, is(nullValue()));
    }

    private static List<Arguments> countryProvider() {
        return Arrays.asList(
            Arguments.arguments(Country.BE),
            Arguments.arguments(Country.BR),
            Arguments.arguments(Country.CA),
            Arguments.arguments(Country.DE),
            Arguments.arguments(Country.DK),
            Arguments.arguments(Country.FR),
            Arguments.arguments(Country.IT),
            Arguments.arguments(Country.LU),
            Arguments.arguments(Country.MX),
            Arguments.arguments(Country.PT),
            Arguments.arguments(Country.ES),
            Arguments.arguments(Country.US)
        );
    }

    @ParameterizedTest(name = "#{index} - Test with Arguments={0},{1}")
    @MethodSource("countryByIdProvider")
    void toString_shouldReturnTrimmedId_whenIdNotNull(final String id, final Country country) {
        final NationalId nationalId = NationalIdFactory.newInstance(id, country);

        final String result = nationalId.toString();

        assertThat(result, is(id.trim()));
    }

    private static List<Arguments> countryByIdProvider() {
        return Arrays.asList(
            Arguments.arguments(" 01.11.16-001.05 ", Country.BE),
            Arguments.arguments(" 144-416-762.63 ", Country.BR),
            Arguments.arguments(" 046 454 286 ", Country.CA),
            Arguments.arguments(" 25768131411 ", Country.DE),
            Arguments.arguments(" 040404-7094 ", Country.DK),
            Arguments.arguments(" 2820819398814 09 ", Country.FR),
            Arguments.arguments(" MRCDRALMAMPALSRE ", Country.IT),
            Arguments.arguments(" 198-308-124-6785 ", Country.LU),
            Arguments.arguments(" AAIT101109MHGNMN01 ", Country.MX),
            Arguments.arguments(" 14898475 4 ZY5 ", Country.PT),
            Arguments.arguments(" 843-456-42L ", Country.ES),
            Arguments.arguments(" 167-38-1265 ", Country.US)
        );
    }

    @ParameterizedTest(name = "#{index} - Test with Arguments={0},{1}")
    @MethodSource("countryByIdProvider")
    void equals_shouldReturnFalse_whenOtherIsNull(final String id, final Country country) {
        final NationalId nationalId = NationalIdFactory.newInstance(id, country);

        final boolean result = nationalId.equals(null);

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Arguments={0},{1}")
    @MethodSource("countryByIdProvider")
    void equals_shouldBeReflexive(final String id, final Country country) {
        final NationalId nationalId = NationalIdFactory.newInstance(id, country);

        final boolean result = nationalId.equals(nationalId);

        assertThat(result, is(true));
    }

    @ParameterizedTest(name = "#{index} - Test with Arguments={0},{1}")
    @MethodSource("countryBySymmetricIdsProvider")
    void equals_shouldBeSymmetric(final String id, final String symmetricId, final Country country) {
        final NationalId nationalId = NationalIdFactory.newInstance(id, country);
        final NationalId symmetricNationalId = NationalIdFactory.newInstance(symmetricId, country);

        final boolean result1 = nationalId.equals(symmetricNationalId);
        final boolean result2 = symmetricNationalId.equals(nationalId);

        assertThat(result1, is(result2));
    }

    private static List<Arguments> countryBySymmetricIdsProvider() {
        return Arrays.asList(
            Arguments.arguments("01.11.16-001.05", "01111600105", Country.BE),
            Arguments.arguments("144-416-762.63", "14441676263", Country.BR),
            Arguments.arguments("046 454 286", "046454286", Country.CA),
            Arguments.arguments("25768131411", " 25768131411 ", Country.DE),
            Arguments.arguments("040404-7094", "0404047094", Country.DK),
            Arguments.arguments("2820819398814 09", "282081939881409", Country.FR),
            Arguments.arguments("MRCDRALMAMPALSRE", " MRCDRALMAMPALSRE ", Country.IT),
            Arguments.arguments("198-308-124-6785", "1983081246785", Country.LU),
            Arguments.arguments("AAIT101109MHGNMN01", " AAIT101109MHGNMN01 ", Country.MX),
            Arguments.arguments("14898475 4 ZY5", "148984754ZY5", Country.PT),
            Arguments.arguments("843-456-42L", "84345642L", Country.ES),
            Arguments.arguments("167-38-1265", "167381265", Country.US)
        );
    }
}
