package com.example.Wordle;

import java.util.List;
import java.util.Random;

public class WordProvider {
    private static final List<String> WORDS = List.of(
        "APPLE", "PLANE", "MANGO", "LEMON", "BRAVE", "SNAKE", "HOUSE"
    );

    public static String getSecretWord() {
        Random rand = new Random();
        return WORDS.get(rand.nextInt(WORDS.size()));
    }

    public static boolean isValidWord(String word) {
        return WORDS.contains(word);
    }
}
