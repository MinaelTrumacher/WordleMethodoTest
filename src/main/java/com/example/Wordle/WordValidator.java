package com.example.Wordle;

public class WordValidator {

    public static boolean isValidEasy(String word) {
        return word.matches("^[a-zA-Z]{5}$") && WordProvider.isValidEasyWord(word);
    }

    public static boolean isValidNormal(String word) {
        return word.matches("^[a-zA-Z]{5,8}$") && WordProvider.isValidNormalWord(word);
    }

    public static boolean isValidHard(String word) {
        return word.matches("^[a-zA-Z]{10}$") && WordProvider.isValidHardWord(word);
    }
}
