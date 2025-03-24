package org.example;

import java.util.List;
import java.util.Random;

public class WordProvider {
    private static final List<String> WORDS = List.of(
        "APPLE", "PLANES", "MANGOS", "LEMONS", "BRAVES", "SNAKES", "HOUSES"
    );

    public static String getSecretWord() {
        Random rand = new Random();
        return WORDS.get(rand.nextInt(WORDS.size()));
    }
}
