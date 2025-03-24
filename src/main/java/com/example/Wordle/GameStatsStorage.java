package com.example.Wordle;

import java.io.*;

public class GameStatsStorage {
    private static final String FILE_NAME = "GameStats.txt";

    public static void save(GameStats stats) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(stats);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameStats load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (GameStats) in.readObject();
        } catch (Exception e) {
            return new GameStats();
        }
    }
}
