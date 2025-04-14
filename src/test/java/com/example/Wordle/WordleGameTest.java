package com.example.Wordle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordleGameTest {

    @Test
    void testCorrectGuess() {
        WordleGame game = new WordleGame("APPLE");
        WordleGame.Feedback feedback = game.checkGuess("APPLE");

        assertArrayEquals(new char[]{'G', 'G', 'G', 'G', 'G'}, feedback.result());
    }

    @Test
    void testPartialMatch() {
        WordleGame game = new WordleGame("APPLE");
        WordleGame.Feedback feedback = game.checkGuess("PLANE");

        assertArrayEquals(new char[]{'Y', 'Y', 'Y', 'X', 'G'}, feedback.result());
    }

    @Test
    void testWrongGuess() {
        WordleGame game = new WordleGame("APPLE");
        WordleGame.Feedback feedback = game.checkGuess("BIRCH");

        assertArrayEquals(new char[]{'X', 'X', 'X', 'X', 'X'}, feedback.result());
    }

    @Test
    void testNumberOfAttempts() {
        WordleGame game = new WordleGame("APPLE");
        game.checkGuess("CRANE");
        game.checkGuess("MANGO");

        assertEquals(2, game.getAttempts());
    }

    @Test
    void testAttemptsIncrement() {
        WordleGame game = new WordleGame("apple");
        assertEquals(0, game.getAttempts());

        game.checkGuess("plane");
        assertEquals(1, game.getAttempts());

        game.checkGuess("apple");
        assertEquals(2, game.getAttempts());
    }

    @Test
    void testGuessWithRepeatedLetters() {
        WordleGame game = new WordleGame("lemon");
        WordleGame.Feedback feedback = game.checkGuess("hello");

        assertArrayEquals(new char[]{'X', 'G', 'Y', 'X', 'Y'}, feedback.result());
    }

    @Test
    void testFeedbackToString() {
        WordleGame game = new WordleGame("apple");
        WordleGame.Feedback feedback = game.checkGuess("apple");

        assertTrue(feedback.toString().contains("G"));
    }

    @Test
    void testSecretWord() {
        WordleGame game = new WordleGame("apple");
        String secretWord = game.getSecretWord();

        assertSame(secretWord, game.getSecretWord());
    }

    @Test
    void testResetFunctionality() {
        WordleGame game = new WordleGame("apple");
        game.checkGuess("mango");
        assertEquals(1, game.getAttempts());

        game.reset("lemon");
        assertEquals(0, game.getAttempts());
        assertEquals("LEMON", game.getSecretWord());
    }

    @Test
    void testGetWordLength() {
        WordleGame game = new WordleGame("BANANE");
        assertEquals(6, game.getWordLength(), "La longueur du mot devrait être 6");

        WordleGame shortGame = new WordleGame("CHAT");
        assertEquals(4, shortGame.getWordLength(), "La longueur du mot devrait être 4");
    }
}
