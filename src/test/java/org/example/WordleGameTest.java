package org.example;

import com.example.Wordle.WordleGame;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

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
}
