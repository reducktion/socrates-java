package com.github.reducktion.socrates.nationalid;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.github.reducktion.socrates.extractor.Citizen;
import com.github.reducktion.socrates.extractor.Gender;

class MexicoNationalIdTest {

    @Test
    void isValid_shouldReturnFalse_whenIdIsNull() {
        final MexicoNationalId mexicoNationalId = new MexicoNationalId(null);

        final boolean result = mexicoNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        "1234567890123456789",  // more than 18 digits
        "12345678901234567",    // less than 18 digits
        "AAIM901112VBCNMN08",   // V, instead of H or M
        "JOIMAAAAAAHHGSMN08",   // AAAAAA, instead of valid date with pattern yyyyMMdd
        "JOTA950616HBCSWSAA",   // AA, instead of numeric characters (at the end)
        "0000101109MHGNMN01",   // 0000, instead of alpha characters (at the start)
        "AAIT101109M1111101",   // 11111, instead of alpha characters (at the end)
        "AAIM901312MBCNMN08",   // month too high
        "AAIM901131MBCNMN08",   // November has 30 days
        "AAIM901232MBCNMN08",   // day too high
        "AAIM010229MBCNMN08",   // 29 February only exists in leap year
        "BMHM260906HCHQAN04",   // invalid control character
        // inappropriate words
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
    void isValid_shouldReturnFalse_whenIdIsNotValid(final String notValidId) {
        final MexicoNationalId mexicoNationalId = new MexicoNationalId(notValidId);

        final boolean result = mexicoNationalId.isValid();

        assertThat(result, is(false));
    }

    @ParameterizedTest(name = "#{index} - Test with Argument={0}")
    @ValueSource(strings = {
        " AAIM901112MBCNMN08 ",
        "JOIM890106HHGSMN08",
        "JOTA950616HBCSWS03",
        "AAIT101109MHGNMN01"
    })
    void isValid_shouldReturnTrue_whenIdIsValid(final String validId) {
        final MexicoNationalId mexicoNationalId = new MexicoNationalId(validId);

        final boolean result = mexicoNationalId.isValid();

        assertThat(result, is(true));
    }

    @ParameterizedTest(name = "#{index} - Test with Arguments={0},{1}")
    @MethodSource("citizenByIdProvider")
    void getCitizen_shouldReturnCorrectCitizenInfo_whenIdIsValid(final String id, final Citizen expectedCitizen) {
        final MexicoNationalId mexicoNationalId = new MexicoNationalId(id);

        final Optional<Citizen> resultCitizen = mexicoNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.of(expectedCitizen)));
    }

    private static List<Arguments> citizenByIdProvider() {
        return Arrays.asList(
            Arguments.arguments(
                "AAIM901112MBCNMN08",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1990)
                    .monthOfBirth(11)
                    .dayOfBirth(12)
                    .placeOfBirth("BAJA CALIFORNIA")
                    .build()
            ),
            Arguments.arguments(
                "JOIM890106HHGSMN08",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1989)
                    .monthOfBirth(1)
                    .dayOfBirth(6)
                    .placeOfBirth("HIDALGO")
                    .build()
            ),
            Arguments.arguments(
                "JOTA950616HBCSWS03",
                Citizen
                    .builder()
                    .gender(Gender.MALE)
                    .yearOfBirth(1995)
                    .monthOfBirth(6)
                    .dayOfBirth(16)
                    .placeOfBirth("BAJA CALIFORNIA")
                    .build()
            ),
            Arguments.arguments(
                "AAJM900827MGTDPS05",
                Citizen
                    .builder()
                    .gender(Gender.FEMALE)
                    .yearOfBirth(1990)
                    .monthOfBirth(8)
                    .dayOfBirth(27)
                    .placeOfBirth("GUANAJUATO")
                    .build()
            )
        );
    }

    @Test
    void getCitizen_shouldReturnEmpty_whenIdIsNotValid() {
        final MexicoNationalId mexicoNationalId = new MexicoNationalId(null);

        final Optional<Citizen> resultCitizen = mexicoNationalId.getCitizen();

        assertThat(resultCitizen, is(Optional.empty()));
    }

    @Test
    void toString_shouldReturnId() {
        final String id = "AAIT101109MHGNMN01";
        final MexicoNationalId mexicoNationalId = new MexicoNationalId(id);

        final String result = mexicoNationalId.toString();

        assertThat(result, is(id));
    }
}
