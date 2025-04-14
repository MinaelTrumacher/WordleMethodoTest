package com.example.Wordle;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameStatsStorageTest {

    private static String FILE_NAME = "GameStats.txt";
    static class NonSerializableStats {
        private int someValue = 42;
    }

    @AfterEach
    void cleanup() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void testLoadReturnsSavedObject() {
        GameStats stats = new GameStats();
        stats.recordWin(3);
        GameStatsStorage.save(stats);

        GameStats loaded = GameStatsStorage.load();
        assertEquals(1, loaded.getNbWin());
    }

    @Test
    void testLoadWhenFileDoesNotExistReturnsDefault() {
        File file = new File(FILE_NAME);
        if (file.exists()) file.delete();

        GameStats stats = GameStatsStorage.load();
        assertEquals(0, stats.getNbWin());
        assertEquals(0, stats.getNbLose());
        assertEquals(0.0, stats.getAvgAttempt());
    }

    @Test
    void testLoadWithCorruptedFileReturnsDefault() throws Exception {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            writer.write("Not a serialized object");
        }

        GameStats stats = GameStatsStorage.load();
        assertEquals(0, stats.getNbWin());
        assertEquals(0, stats.getNbLose());
    }

    @Test
    void testSaveCatchIOException() {
        GameStats stats = new GameStats();

        String invalidPath = File.separator + "this" + File.separator + "is" + File.separator + "not" + File.separator + "writable" + File.separator + "stats.txt";

        assertDoesNotThrow(() -> GameStatsStorage.saveWithFilename(stats, invalidPath));
    }

    @Test
    void testSaveCatchIOExceptionInOriginalSaveMethod() {
        GameStats stats = new GameStats();

        String original = GameStatsStorage.FILE_NAME;
        GameStatsStorage.FILE_NAME = "/invalid/path/GameStats.txt";

        assertDoesNotThrow(() -> GameStatsStorage.save(stats));

        GameStatsStorage.FILE_NAME = original;
    }

    @Test
    void testWriteObjectThrowsException() {
        String tempFile = "temp_test_stats.txt";
        NonSerializableStats invalidStats = new NonSerializableStats();

        assertDoesNotThrow(() -> {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(tempFile))) {
                out.writeObject(invalidStats);
            } catch (IOException e) {
                System.err.println("Erreur attendue lors du writeObject : " + e.getMessage());
            }
        });

        new File(tempFile).delete();
    }

    @Test
    void testLoadTryBlockReallyCalled() {
        GameStats stats = new GameStats();
        stats.recordWin(1);
        GameStatsStorage.save(stats);

        GameStats loaded = GameStatsStorage.load();
        assertEquals(1, loaded.getNbWin());

        // BONUS : on vérifie que le chargement correspond à l'objet sauvegardé
        assertTrue(loaded instanceof GameStats);
    }


}
