package com.example.Wordle;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordProviderTest {

    @Test
    void testGetEasyWordReturnsValidWord() {
        String word = WordProvider.getEasyWord();
        assertEquals(5, word.length());
        assertTrue(WordProvider.isValidEasyWord(word));
    }

    @Test
    void testGetNormalWordLengthGreaterThanOrEqualToFive() {
        String word = WordProvider.getNormalWord();
        assertTrue(word.length() >= 5);
    }

    @Test
    void testGetHardWordLengthGreaterThanEight() {
        String word = WordProvider.getHardWord();
        assertTrue(word.length() > 8);
    }

    @Test
    void testIsValidWordReturnsTrueForKnownWord() {
        assertTrue(WordProvider.isValidEasyWord("apple"));
        assertFalse(WordProvider.isValidEasyWord("zzzzz"));
    }

    @RepeatedTest(10) // pour vérifier plusieurs tirages aléatoires
    void testGetEasyWord() {
        String word = WordProvider.getEasyWord();
        assertNotNull(word, "Le mot EASY ne doit pas être null");
        assertTrue(WordProvider.isValidEasyWord(word), "Le mot EASY doit être dans la liste EASY_WORDS");
        assertEquals(word.toUpperCase(), word, "Le mot EASY doit être en majuscules");
    }

    @RepeatedTest(10)
    void testGetNormalWord() {
        String word = WordProvider.getNormalWord();
        assertNotNull(word, "Le mot NORMAL ne doit pas être null");
        assertTrue(WordProvider.isValidNormalWord(word), "Le mot NORMAL doit être dans la liste NORMAL_WORDS");
        assertEquals(word.toUpperCase(), word, "Le mot NORMAL doit être en majuscules");
    }

    @RepeatedTest(10)
    void testGetHardWord() {
        String word = WordProvider.getHardWord();
        assertNotNull(word, "Le mot HARD ne doit pas être null");
        assertTrue(WordProvider.isValidHardWord(word), "Le mot HARD doit être dans la liste HARD_WORDS");
        assertEquals(word.toUpperCase(), word, "Le mot HARD doit être en majuscules");
    }
}
