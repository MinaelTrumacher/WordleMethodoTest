package com.example.Wordle;

public class WordleGame {
    private String secretWord;
    private int attempts;
    private int wordLength;

    /**
     * constructor du jeu
     */
    public WordleGame(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
        this.attempts = 0;
        this.wordLength = secretWord.length();
    }

    public void reset(String secretWord) {
        this.secretWord = secretWord.toUpperCase();
        this.attempts = 0;
        this.wordLength = secretWord.length();
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

    public int getWordLength() {
        return wordLength;
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
        int score = 0;

        for (int i = 0; i < guess.length(); i++) {
            if (guess.charAt(i) == secretWord.charAt(i)) {
                result[i] = 'G';
                secretUsed[i] = true;
                score += 1;
            } else {
                result[i] = '_';
            }
        }

        for (int i = 0; i < guess.length(); i++) {
            if (result[i] == '_') {
                for (int j = 0; j < secretWord.length(); j++) {
                    if (!secretUsed[j] && guess.charAt(i) == secretWord.charAt(j)) {
                        result[i] = 'Y';
                        secretUsed[j] = true;
                        score += -1;
                        break;
                    }
                }
                if (result[i] == '_') {
                    result[i] = 'X';
                    score += -2;
                }
            }
        }

        int penalty = (attempts - 1) * 2;
        int finalScore = Math.max(0, score - penalty);

        return new Feedback(result, finalScore);
    }

    public record Feedback(char[] result, int score) {

        @Override
        public String toString() {
            return new String(result);
        }
    }
}
