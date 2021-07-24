package com.github.reducktion.socrates.nationalid;

import java.time.Year;
import java.util.Optional;

import com.github.reducktion.socrates.Citizen;
import com.github.reducktion.socrates.Gender;
import com.github.reducktion.socrates.internal.StringUtils;
import com.github.reducktion.socrates.internal.TwoYearDateParser;

/**
 * National Id for France.
 *
 * Information about this national id can be found at:
 *  - http://resoo.org/docs/_docs/regles-numero-insee.pdf
 *  - https://en.wikipedia.org/wiki/INSEE_code
 *  - https://fr.wikipedia.org/wiki/Num%C3%A9ro_de_s%C3%A9curit%C3%A9_sociale_en_France
 *  - (Geographic codes) https://fr.wikipedia.org/wiki/Codes_g%C3%A9ographiques_de_la_France
 *  - (Departments) https://en.wikipedia.org/wiki/Departments_of_France
 */
class FranceNationalId implements NationalId {

    private static final int ID_NUMBER_OF_CHARACTERS = 15;
    private static final int CONTROL_DIGIT_MAX_VALUE = 97;
    private static final String CHARACTER_MALE = "1";
    private static final int JANUARY = 1;
    private static final int DECEMBER = 12;
    private static final int PSEUDO_FICTITIOUS_JANUARY = 31;
    private static final int PSEUDO_FICTITIOUS_DECEMBER = 42;

    private final String id;
    private final String sanitizedId;

    private final TwoYearDateParser twoYearDateParser = new TwoYearDateParser(Year.now().getValue());

    public FranceNationalId(final String id) {
        this.id = id;
        sanitizedId = sanitize(id);
    }

    private String sanitize(final String id) {
        return id == null ? null : id.replace(" ", "");
    }

    @Override
    public boolean isValid() {
        if (sanitizedId == null || sanitizedId.length() != ID_NUMBER_OF_CHARACTERS) {
            return false;
        }

        final String sanitizedIdForCorsica = sanitizedId
            .replace("2A", "19")
            .replace("2B", "18");

        if (!StringUtils.isNumeric(sanitizedIdForCorsica)) {
            return false;
        }

        return validateControlDigit(sanitizedIdForCorsica);
    }

    private boolean validateControlDigit(final String id) {
        final long controlDigit = getControlDigit();
        final String partialId = stripControlDigit(id);
        return controlDigit == CONTROL_DIGIT_MAX_VALUE - (Long.parseLong(partialId) % CONTROL_DIGIT_MAX_VALUE);
    }

    private String stripControlDigit(final String id) {
        return id.substring(0, id.length() - 2);
    }

    private long getControlDigit() {
        final String controlDigit = sanitizedId.substring(sanitizedId.length() - 2);
        return Integer.parseInt(controlDigit);
    }

    @Override
    public Optional<Citizen> extractCitizen() {
        if (!isValid()) {
            return Optional.empty();
        }

        return Optional.of(
            Citizen
                .builder()
                .gender(extractGender())
                .yearOfBirth(extractYearOfBirth())
                .monthOfBirth(extractMonthOfBirth())
                .placeOfBirth(extractPlaceOfBirth())
                .build()
        );
    }

    private Gender extractGender() {
        if (CHARACTER_MALE.equals(sanitizedId.substring(0, 1))) {
            return Gender.MALE;
        } else {
            return Gender.FEMALE;
        }
    }

    private Integer extractYearOfBirth() {
        return twoYearDateParser
            .parse(sanitizedId.substring(1, 3))
            .orElse(null);
    }

    private Integer extractMonthOfBirth() {
        final int month = Integer.parseInt(sanitizedId.substring(3, 5));

        if (month >= JANUARY && month <= DECEMBER) {
            return month;
        } else if (month >= PSEUDO_FICTITIOUS_JANUARY && month <= PSEUDO_FICTITIOUS_DECEMBER) {
            return month - (PSEUDO_FICTITIOUS_JANUARY - 1);
        } else {
            return null;
        }
    }

    private String extractPlaceOfBirth() {
        final String placeOfBirthTwoCharacters = sanitizedId.substring(5, 7);
        final String placeOfBirthThreeCharacters = sanitizedId.substring(5, 8);

        final String region = getRegionByCode(placeOfBirthTwoCharacters);

        return region != null ? region : getRegionByCode(placeOfBirthThreeCharacters);
    }

    private String getRegionByCode(final String code) {
        switch (code) {
            case "01": return "Ain";
            case "02": return "Aisne";
            case "03": return "Allier";
            case "04": return "Alpes-de-Haute-Provence";
            case "05": return "Hautes-Alpes";
            case "06": return "Alpes-Maritimes";
            case "07": return "Ardèche";
            case "08": return "Ardennes";
            case "09": return "Ariège";
            case "10": return "Aube";
            case "11": return "Aude";
            case "12": return "Aveyron";
            case "13": return "Bouches-du-Rhône";
            case "14": return "Calvados";
            case "15": return "Cantal";
            case "16": return "Charente";
            case "17": return "Charente-Maritime";
            case "18": return "Cher";
            case "19": return "Corrèze";
            case "2A": return "Corse-du-Sud";
            case "2B": return "Haute-Corse";
            case "21": return "Côte-d’Or";
            case "22": return "Côtes-d’Armor";
            case "23": return "Creuse";
            case "24": return "Dordogne";
            case "25": return "Doubs";
            case "26": return "Drôme";
            case "27": return "Eure";
            case "28": return "Eure-et-Loir";
            case "29": return "Finistère";
            case "30": return "Gard";
            case "31": return "Haute-Garonne";
            case "32": return "Gers";
            case "33": return "Gironde";
            case "34": return "Hérault";
            case "35": return "Ille-et-Vilaine";
            case "36": return "Indre";
            case "37": return "Indre-et-Loire";
            case "38": return "Isère";
            case "39": return "Jura";
            case "40": return "Landes";
            case "41": return "Loir-et-Cher";
            case "42": return "Loire";
            case "43": return "Haute-Loire";
            case "44": return "Loire-Atlantique";
            case "45": return "Loiret";
            case "46": return "Lot";
            case "47": return "Lot-et-Garonne";
            case "48": return "Lozère";
            case "49": return "Maine-et-Loire";
            case "50": return "Manche";
            case "51": return "Marne";
            case "52": return "Haute-Marne";
            case "53": return "Mayenne";
            case "54": return "Meurthe-et-Moselle";
            case "55": return "Meuse";
            case "56": return "Morbihan";
            case "57": return "Moselle";
            case "58": return "Nièvre";
            case "59": return "Nord";
            case "60": return "Oise";
            case "61": return "Orne";
            case "62": return "Pas-de-Calais";
            case "63": return "Puy-de-Dôme";
            case "64": return "Pyrénées-Atlantiques";
            case "65": return "Hautes-Pyrénées";
            case "66": return "Pyrénées-Orientales";
            case "67": return "Bas-Rhin";
            case "68": return "Haut-Rhin";
            case "69": return "Rhône";
            case "70": return "Haute-Saône";
            case "71": return "Saône-et-Loire";
            case "72": return "Sarthe";
            case "73": return "Savoie";
            case "74": return "Haute-Savoie";
            case "75": return "Paris";
            case "76": return "Seine-Maritime";
            case "77": return "Seine-et-Marne";
            case "78": return "Yvelines";
            case "79": return "Deux-Sèvres";
            case "80": return "Somme";
            case "81": return "Tarn";
            case "82": return "Tarn-et-Garonne";
            case "83": return "Var";
            case "84": return "Vaucluse";
            case "85": return "Vendée";
            case "86": return "Vienne";
            case "87": return "Haute-Vienne";
            case "88": return "Vosges";
            case "89": return "Yonne";
            case "90": return "Territoire de Belfort";
            case "91": return "Essonne";
            case "92": return "Hauts-de-Seine";
            case "93": return "Seine-Saint-Denis";
            case "94": return "Val-de-Marne";
            case "95": return "Val-d'Oise";
            case "971": return "Guadeloupe";
            case "972": return "Martinique";
            case "973": return "Guyane (française)";
            case "974": return "La Réunion";
            case "975": return "Saint Pierre and Miquelon";
            case "976": return "Mayotte";
            case "977": return "Saint-Barthélemy";
            case "978": return "Saint-Martin";
            case "984": return "Terres australes et antarctiques françaises";
            case "986": return "Wallis-et-Futuna";
            case "987": return "Polynésie française";
            case "988": return "Nouvelle-Calédonie";
            case "989": return "Île de Clipperton";
            default: return null;
        }
    }

    @Override
    public String toString() {
        return id;
    }
}
