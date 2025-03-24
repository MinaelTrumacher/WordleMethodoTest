package com.example.Wordle;

public class WordleGame {
    private final String secretWord;
    private int attempts;

    public WordleGame(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
        this.attempts = 0;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public int getAttempts() {
        return attempts;
    }

    public Feedback checkGuess(String guess) {
        guess = guess.toUpperCase();
        attempts++;

        char[] result = new char[guess.length()];
        boolean[] secretUsed = new boolean[guess.length()];

        // First pass: good letters (green)
        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                result[i] = 'G';
                secretUsed[i] = true;
            } else {
                result[i] = '_';
            }
        }

        // Second pass: present but misplaced (yellow)
        for (int i = 0; i < guess.length(); i++) {
            if (result[i] == '_') {
                for (int j = 0; j < secretWord.length(); j++) {
                    if (!secretUsed[j] && guess.charAt(i) == secretWord.charAt(j)) {
                        result[i] = 'Y';
                        secretUsed[j] = true;
                        break;
                    }
                }
                if (result[i] == '_') {
                    result[i] = 'X'; // Not in word
                }
            }
        }

        return new Feedback(result);
    }

    public static class Feedback {
        private final char[] result;

        public Feedback(char[] result) {
            this.result = result;
        }

        public char[] getResult() {
            return result;
        }

        @Override
        public String toString() {
            return new String(result);
        }
    }
}
