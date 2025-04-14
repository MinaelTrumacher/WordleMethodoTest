package com.example.Wordle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameStatsTest {

    @Test
    void testRecordWinIncrementsWinAndAddsAttempts() {
        GameStats stats = new GameStats();
        stats.recordWin(4);

        assertEquals(1, stats.getNbWin());
        assertEquals(4.0, stats.getAvgAttempt());
    }

    @Test
    void testRecordLoseIncrementsLoseOnly() {
        GameStats stats = new GameStats();
        stats.recordLose();

        assertEquals(1, stats.getNbLose());
        assertEquals(0.0, stats.getAvgAttempt()); 
    }

    @Test
    void testMultipleWinsAverageAttempts() {
        GameStats stats = new GameStats();
        stats.recordWin(4);
        stats.recordWin(6);

        assertEquals(2, stats.getNbWin());
        assertEquals(5.0, stats.getAvgAttempt());
    }


    @Test
    void testBestScoreUpdatesCorrectly() {
        GameStats stats = new GameStats();

        assertEquals(0, stats.getBestScore());

        stats.recordScore(12);
        assertEquals(12, stats.getBestScore());

        stats.recordScore(8);
        assertEquals(12, stats.getBestScore());

        stats.recordScore(18);
        assertEquals(18, stats.getBestScore());
    }
}
