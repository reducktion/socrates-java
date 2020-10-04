package com.github.reducktion.socrates.extractor;

import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Optional;

import com.github.reducktion.socrates.validator.IdValidator;

/**
 * {@link Citizen} extractor for France.
 *
 * This validation algorithm is based on documentation released in 22 of March of 2001:
 * http://resoo.org/docs/_docs/regles-numero-insee.pdf
 *
 * An english version is available in wikipedia: https://en.wikipedia.org/wiki/INSEE_code
 *
 * Information about the region codes can be found in
 * https://fr.wikipedia.org/wiki/Codes_g%C3%A9ographiques_de_la_France and
 * https://en.wikipedia.org/wiki/Departments_of_France
 */
class FranceCitizenExtractor implements CitizenExtractor {

    private static final String CHARACTER_MALE = "1";
    private static final int JANUARY = 1;
    private static final int DECEMBER = 12;
    private static final int PSEUDO_FICTITIOUS_JANUARY = 31;
    private static final int PSEUDO_FICTITIOUS_DECEMBER = 42;

    private final DateTimeFormatter twoYearFormatter = new DateTimeFormatterBuilder()
        .appendValueReduced(ChronoField.YEAR, 2, 2, Year.now().getValue() - 100) // change time window
        .toFormatter();

    @Override
    public Optional<Citizen> extractFromId(final String id, final IdValidator idValidator) {
        if (!idValidator.validate(id)) {
            return Optional.empty();
        }

        final String sanitizedId = sanitize(id);

        final Citizen citizen = new Citizen(
            extractGender(sanitizedId),
            extractYear(sanitizedId),
            extractMonth(sanitizedId),
            extractPlaceOfBirth(sanitizedId)
        );

        return Optional.of(citizen);
    }

    private String sanitize(final String id) {
        return id.replace(" ", "");
    }

    private String extractGender(final String id) {
        if (CHARACTER_MALE.equals(getGenderCharacter(id))) {
            return "M";
        } else {
            return "F";
        }
    }

    private String getGenderCharacter(final String id) {
        return id.substring(0, 1);
    }

    private Integer extractYear(final String id) {
        return twoYearFormatter
            .parse(getYearCharacters(id))
            .get(ChronoField.YEAR);
    }

    private String getYearCharacters(final String id) {
        return id.substring(1, 3);
    }

    private Integer extractMonth(final String id) {
        final int month = Integer.parseInt(getMonthCharacters(id));

        if (month >= JANUARY && month <= DECEMBER) {
            return month;
        } else if (month >= PSEUDO_FICTITIOUS_JANUARY && month <= PSEUDO_FICTITIOUS_DECEMBER) {
            return month - (PSEUDO_FICTITIOUS_JANUARY - 1);
        } else {
            return null;
        }
    }

    private String getMonthCharacters(final String id) {
        return id.substring(3, 5);
    }

    private String extractPlaceOfBirth(final String id) {
        final String region = getRegionForCode(getRegionTwoCharacters(id));

        return region != null ? region : getRegionForCode(getRegionThreeCharacters(id));
    }

    private String getRegionTwoCharacters(final String id) {
        return id.substring(5, 7);
    }

    private String getRegionThreeCharacters(final String id) {
        return id.substring(5, 8);
    }
    
    private String getRegionForCode(final String code) {
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
}
