package com.example.Wordle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordValidatorTest {

    @Test
    void testValidEasyWord() {
        assertTrue(WordValidator.isValidEasy("apple"));
    }

    @Test
    void testTooShortEasyWord() {
        assertFalse(WordValidator.isValidEasy("cat"));
    }

    @Test
    void testNonAlphabeticEasyWord() {
        assertFalse(WordValidator.isValidEasy("a1b2c"));
    }

    @Test
    void testUnknownEasyWord() {
        assertFalse(WordValidator.isValidEasy("zzzzz"));
    }

    @Test
    void testValidNormalWord() {
        assertTrue(WordValidator.isValidNormal("investir"));
    }

    @Test
    void testTooShortNormalWord() {
        assertFalse(WordValidator.isValidNormal("roue"));
    }

    @Test
    void testNonAlphabeticNormalWord() {
        assertFalse(WordValidator.isValidNormal("a1b2c34"));
    }

    @Test
    void testUnknownNormalWord() {
        assertFalse(WordValidator.isValidNormal("zzzzzzz"));
    }

    @Test
    void testValidHardWord() {
        assertTrue(WordValidator.isValidHard("liebeslied"));
    }

    @Test
    void testTooShortHardWord() {
        assertFalse(WordValidator.isValidHard("arschloch"));
    }

    @Test
    void testNonAlphabeticHardWord() {
        assertFalse(WordValidator.isValidHard("a1b2c34bui"));
    }

    @Test
    void testUnknownHardWord() {
        assertFalse(WordValidator.isValidHard("zzzzzzzzzz"));
    }
}
