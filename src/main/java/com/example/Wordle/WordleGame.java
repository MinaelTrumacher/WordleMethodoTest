package com.example.Wordle;

public class WordleGame {
    private final String secretWord;
    private int attempts;

    /**
     * constructor du jeu
     */
    public WordleGame(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
        this.attempts = 0;
    }

    /**
     * @return un mot secret
     */
    public String getSecretWord() {
        return secretWord;
    }

    /**
     * @return le nombre de tentative
     */
    public int getAttempts() {
        return attempts;
    }

    /**
     * String guess
     * @return Vérification des lettres
     */
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

    public record Feedback(char[] result) {

        @Override
        public String toString() {
            return new String(result);
        }
    }
}
