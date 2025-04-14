package com.example.Wordle;

import java.util.List;
import java.util.Random;

public class WordProvider {
    private static final List<String> EASY_WORDS = List.of(
        "APPLE",
        "PLANE",
        "MANGO",
        "LEMON",
        "BRAVE",
        "SNAKE",
        "HOUSE"
    );

    private static final List<String> NORMAL_WORDS = List.of(
            "ACCORD",
            "BALLON",
            "DOUTER",
            "CHOISI",
            "FIGURE",
            "JARDIN",
            "ABEILLE",
            "CRAVATE",
            "FRAICHE",
            "GRENIER",
            "JOURNEE",
            "HERITAGE",
            "INVESTIR",
            "JONGLEUR",
            "CHANTAGE"
    );

    private static final List<String> HARD_WORDS = List.of(
            "ABSCHIEDET",
            "ARBEITSLOS",
            "BLUMENSTRA",
            "FLUGTICKET",
            "KATZENFELL",
            "LIEBESLIED",
            "RECHENBUCH",
            "TASCHENLAM"
    );

    public static String getEasyWord() {
        Random rand = new Random();
        return EASY_WORDS.get(rand.nextInt(EASY_WORDS.size()));
    }

    public static String getNormalWord() {
        Random rand = new Random();
        return NORMAL_WORDS.get(rand.nextInt(NORMAL_WORDS.size()));
    }

    public static String getHardWord() {
        Random rand = new Random();
        return HARD_WORDS.get(rand.nextInt(HARD_WORDS.size()));
    }

    public static boolean isValidEasyWord(String word) {
        return EASY_WORDS.contains(word.toUpperCase());
    }

    public static boolean isValidNormalWord(String word) {
        return NORMAL_WORDS.contains(word.toUpperCase());
    }

    public static boolean isValidHardWord(String word) {
        return HARD_WORDS.contains(word.toUpperCase());
    }
}
