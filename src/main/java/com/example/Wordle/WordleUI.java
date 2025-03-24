package com.example.Wordle;

import javax.swing.*;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class WordleUI extends JFrame {
    private final WordleGame game;
    private final JTextField inputField;
    private final JTextPane outputPane;
    private final GameStats stats;
    private JButton newGameButton;

    public WordleUI() {
        game = new WordleGame(WordProvider.getSecretWord());
        stats = GameStatsStorage.load();

        setTitle("Wordle Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        newGameButton = new JButton("Nouvelle Partie");
        newGameButton.setEnabled(false);
        newGameButton.addActionListener(e -> startNewGame());
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(newGameButton);
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

    private void startDifficultNewGame() {
        game.reset(WordProvider.getDifficultWord());
        inputField.setText("");
        inputField.setEnabled(true);
        outputPane.setText("");
        newGameButton.setEnabled(false);
    }

    private void startNewGame() {
        game.reset(WordProvider.getSecretWord());
        inputField.setText("");
        inputField.setEnabled(true);
        outputPane.setText("");
        newGameButton.setEnabled(false);
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

        if (guess.length() != game.getSecretWord().length()) {
            JOptionPane.showMessageDialog(this, "Le mot doit faire " + game.getSecretWord().length() + " lettres.");
            return;
        }

        WordleGame.Feedback feedback = game.checkGuess(guess);
        int nbAttempt = game.getAttempts();
        int remaining = 6 - nbAttempt;

        appendColoredFeedback(guess, feedback.toString(), remaining);

        inputField.setText("");

        if (feedback.toString().equals("GGGGG")) {
            JOptionPane.showMessageDialog(this, "Bravo ! Mot trouvé en " + nbAttempt + " tentative(s) !");
            stats.recordWin(nbAttempt);
            GameStatsStorage.save(stats);
            newGameButton.setEnabled(true);
            inputField.setEnabled(false);
            String message = String.format(
                    "Statistiques :\nVictoires : %d\nDéfaites : %d\nMoyenne de tentatives : %.2f",
                    stats.getNbWin(), stats.getNbLose(), stats.getAvgAttempt()
            );
            JOptionPane.showMessageDialog(this, message);
        } else if (nbAttempt >= 6) {
            JOptionPane.showMessageDialog(this, "Perdu ! Le mot était : " + game.getSecretWord());
            stats.recordLose();
            GameStatsStorage.save(stats);
            newGameButton.setEnabled(true);
            inputField.setEnabled(false);
            String message = String.format(
                    "Statistiques :\nVictoires : %d\nDéfaites : %d\nMoyenne de tentatives : %.2f",
                    stats.getNbWin(), stats.getNbLose(), stats.getAvgAttempt()
            );
            JOptionPane.showMessageDialog(this, message);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordleUI::new);
    }
}
