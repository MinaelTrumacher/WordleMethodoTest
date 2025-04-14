package com.example.Wordle;

import java.io.Serializable;

public class GameStats implements Serializable {
    private int nbWin = 0;
    private int nbLose = 0;
    private int totalAttempt = 0;
    private int bestScore = 0;

    public void recordWin(int attempts) {
        nbWin++;
        totalAttempt += attempts;
    }

    public void recordScore(int score) {
        if (score > bestScore) {
            bestScore = score;
        }
    }

    public int getBestScore() {
        return bestScore;
    }

    public void recordLose() {
        nbLose++;
    }

    public int getNbWin() { return nbWin; }

    public int getNbLose() { return nbLose; }

    public double getAvgAttempt() {
        return nbWin == 0 ? 0 : (double) totalAttempt / nbWin;
    }
}
