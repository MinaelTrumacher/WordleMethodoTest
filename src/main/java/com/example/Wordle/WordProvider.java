package com.example.Wordle;

import java.util.List;
import java.util.Random;

public class WordProvider {
    private static final List<String> EASY_WORDS = List.of(
        "APPLE", "PLANE", "MANGO", "LEMON", "BRAVE", "SNAKE", "HOUSE"
    );

    private static final List<String> NORMAL_WORDS = List.of(
            "Donaudampfschifffahrtsgesellschaftskapitänswitwe",
            "Rechtsschutzversicherungsgesellschaften",
            "Kraftfahrzeug-Haftpflichtversicherung",
            "Lebensabschnittspartner",
            "Freundschaftsbezeigungen",
            "Nahrungsmittelunverträglichkeit",
            "Verkehrsinfrastrukturfinanzierungsgesellschaft",
            "Bundesausbildungsförderungsgesetz",
            "Arbeitsunfähigkeitsbescheinigung",
            "Siebentausendzweihundertvierundfünfzig"
    );

    public static String getSecretWord() {
        Random rand = new Random();
        return EASY_WORDS.get(rand.nextInt(EASY_WORDS.size()));
    }

    public static String getDifficultWord() {
        Random rand = new Random();
        return NORMAL_WORDS.get(rand.nextInt(NORMAL_WORDS.size()));
    }
}
