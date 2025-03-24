package com.example.Wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordleUI extends JFrame {
    private final WordleGame game;
    private final JTextField inputField;
    private final JTextArea outputArea;

    public WordleUI() {
        game = new WordleGame(WordProvider.getSecretWord());

        setTitle("Wordle Java");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Entrée utilisateur
        inputField = new JTextField();
        JButton guessButton = new JButton("Valider");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(guessButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.NORTH);

        // Zone d’affichage
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        // Action bouton
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        // Entrée clavier (ENTER)
        inputField.addActionListener(e -> processGuess());

        setVisible(true);
    }

    private void processGuess() {
        String guess = inputField.getText();
        if (guess.length() != game.getSecretWord().length()) {
            JOptionPane.showMessageDialog(this, "Le mot doit faire " + game.getSecretWord().length() + " lettres.");
            return;
        }

        WordleGame.Feedback feedback = game.checkGuess(guess);
        int nbAttempt = game.getAttempts();
        outputArea.append(guess.toUpperCase() + " → " + feedback.toString() + "\n");
        //outputArea.append(guess.toUpperCase() + " → " + feedback.toString() + "\n" + "Tentative(s) restante(s): " + (6 - nbAttempt) + "\n");

        inputField.setText("");

        if (feedback.toString().equals("GGGGG")) {
            JOptionPane.showMessageDialog(this, "Bravo ! Mot trouvé en " + game.getAttempts() + " tentative(s) !");
            inputField.setEnabled(false);
        } else if (game.getAttempts() >= 6) {
            JOptionPane.showMessageDialog(this, "Perdu ! Le mot était : " + game.getSecretWord());
            inputField.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WordleUI::new);
    }
}
