package com.example.Wordle;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class WordleUI extends JFrame {
    private WordleGame game;
    private final JTextField inputField;
    private final JTextPane outputPane;
    private final GameStats stats;
    private final JButton newGameButton;
    private final JLabel timerLabel = new JLabel("Temps restant : ");
    private Timer gameTimer;
    private int timeRemaining;
    private GameMode currentMode = GameMode.EASY;
    private final JLabel scoreLabel = new JLabel("Score : 0");

    public WordleUI() {
        stats = GameStatsStorage.load();

        setTitle("Wordle Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 380);
        setLayout(new BorderLayout());

        newGameButton = new JButton("Nouvelle Partie");
        newGameButton.setEnabled(false);
        newGameButton.addActionListener(e -> startNewGame());

        JButton easyButton = new JButton("Mode Facile");
        JButton normalButton = new JButton("Mode Normal");
        JButton hardButton = new JButton("Mode Difficile");

        easyButton.addActionListener(e -> {
            currentMode = GameMode.EASY;
            startEasyGame();
        });

        normalButton.addActionListener(e -> {
            currentMode = GameMode.NORMAL;
            startNormalGame();
        });

        hardButton.addActionListener(e -> {
            currentMode = GameMode.HARD;
            startHardGame();
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(easyButton);
        bottomPanel.add(normalButton);
        bottomPanel.add(hardButton);
        bottomPanel.add(newGameButton);
        bottomPanel.add(timerLabel);
        bottomPanel.add(scoreLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        inputField = new JTextField();
        JButton guessButton = new JButton("Valider");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(guessButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);

        outputPane = new JTextPane();
        outputPane.setEditable(false);
        outputPane.setFont(new Font("Monospaced", Font.PLAIN, 16));
        JScrollPane outputScrollPane = new JScrollPane(outputPane);
        add(outputScrollPane, BorderLayout.CENTER);

        guessButton.addActionListener(e -> processGuess());
        inputField.addActionListener(e -> processGuess());

        setVisible(true);
    }

    private void startEasyGame() {
        cancelTimer();
        game = new WordleGame(WordProvider.getEasyWord());
        resetUI();
    }

    private void startNormalGame() {
        cancelTimer();
        game = new WordleGame(WordProvider.getNormalWord());
        resetUI();
        startTimer(30); // 30 minutes
    }

    private void startHardGame() {
        cancelTimer();
        game = new WordleGame(WordProvider.getHardWord());
        resetUI();
        startTimer(15); // 15 minutes
    }

    private void resetUI() {
        inputField.setText("");
        inputField.setEnabled(true);
        outputPane.setText("");
        newGameButton.setEnabled(false);
        timerLabel.setText(" ");
    }

    private void startTimer(int minutes) {
        timeRemaining = minutes * 60;
        timerLabel.setText("Temps restant : " + formatTime(timeRemaining));

        gameTimer = new Timer();
        gameTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    timeRemaining--;
                    if (timeRemaining <= 0) {
                        gameTimer.cancel();
                        inputField.setEnabled(false);
                        JOptionPane.showMessageDialog(null, "Temps écoulé !");
                        stats.recordLose();
                        GameStatsStorage.save(stats);
                        showStats();
                        newGameButton.setEnabled(true);
                        timerLabel.setText(" ");
                    } else {
                        timerLabel.setText("Temps restant : " + formatTime(timeRemaining));
                    }
                });
            }
        }, 0, 1000);
    }

    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

    private void cancelTimer() {
        if (gameTimer != null) {
            gameTimer.cancel();
        }
    }

    private void appendColoredFeedback(String guess, String feedbackStr, int remainingAttempts) {
        StyledDocument doc = outputPane.getStyledDocument();

        try {
            doc.insertString(doc.getLength(), guess.toUpperCase() + " → ", null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < feedbackStr.length(); i++) {
            char c = feedbackStr.charAt(i);
            Style style = outputPane.addStyle("colorStyle", null);
            switch (c) {
                case 'G' -> StyleConstants.setForeground(style, Color.GREEN);
                case 'Y' -> StyleConstants.setForeground(style, Color.ORANGE);
                case 'X' -> StyleConstants.setForeground(style, Color.RED);
                default -> StyleConstants.setForeground(style, Color.BLACK);
            }

            try {
                doc.insertString(doc.getLength(), String.valueOf(c), style);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            doc.insertString(doc.getLength(), "   Tentative(s) restante(s) : " + remainingAttempts + "\n", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processGuess() {
        String guess = inputField.getText().trim();

        if (currentMode == GameMode.EASY) {
            if (!WordValidator.isValidEasy(guess)) {
                JOptionPane.showMessageDialog(this, "Mot invalide !");
                if (guess.length() != game.getWordLength()) {
                    JOptionPane.showMessageDialog(this, "Le mot doit faire " + game.getWordLength() + " lettres.");
                    return;
                }
                return;
            }
        }
        if (currentMode == GameMode.NORMAL) {
            if (!WordValidator.isValidNormal(guess)) {
                JOptionPane.showMessageDialog(this, "Mot invalide !");
                if (guess.length() != game.getWordLength()) {
                    JOptionPane.showMessageDialog(this, "Le mot doit faire " + game.getWordLength() + " lettres.");
                    return;
                }
                return;
            }
        }

        if (currentMode == GameMode.HARD) {
            if (!WordValidator.isValidHard(guess)) {
                JOptionPane.showMessageDialog(this, "Mot invalide !");
                if (guess.length() != game.getWordLength()) {
                    JOptionPane.showMessageDialog(this, "Le mot doit faire " + game.getWordLength() + " lettres.");
                    return;
                }
                return;
            }
        }

        WordleGame.Feedback feedback = game.checkGuess(guess);
        scoreLabel.setText("Score : " + feedback.score());
        stats.recordScore(feedback.score());
        int nbAttempt = game.getAttempts();
        int remaining = 6 - nbAttempt;

        appendColoredFeedback(guess, feedback.toString(), remaining);
        inputField.setText("");

        if (feedback.toString().chars().allMatch(c -> c == 'G')) {
            JOptionPane.showMessageDialog(this, "Bravo ! Mot trouvé en " + nbAttempt + " tentative(s) !");
            stats.recordWin(nbAttempt);
            GameStatsStorage.save(stats);
            newGameButton.setEnabled(true);
            inputField.setEnabled(false);
            cancelTimer();
            showStats();
        } else if (nbAttempt >= 6) {
            JOptionPane.showMessageDialog(this, "Perdu ! Le mot était : " + game.getSecretWord());
            stats.recordLose();
            GameStatsStorage.save(stats);
            newGameButton.setEnabled(true);
            inputField.setEnabled(false);
            cancelTimer();
            showStats();
        }
    }

    private void showStats() {
        String message = String.format(
                "Statistiques :\nVictoires : %d\nDéfaites : %d\nMoyenne de tentatives : %.2f\nMeilleur score : %d",
                stats.getNbWin(), stats.getNbLose(), stats.getAvgAttempt(), stats.getBestScore()
        );
        JOptionPane.showMessageDialog(this, message);
    }

    private void startNewGame() {
        cancelTimer();

        switch (currentMode) {
            case EASY -> game = new WordleGame(WordProvider.getEasyWord());
            case NORMAL -> {
                game = new WordleGame(WordProvider.getNormalWord());
                startTimer(30);
            }
            case HARD -> {
                game = new WordleGame(WordProvider.getHardWord());
                startTimer(15);
            }
        }

        resetUI();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordleUI::new);
    }
}
