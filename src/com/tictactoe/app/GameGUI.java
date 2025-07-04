package com.tictactoe.app;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameGUI {

    JFrame frame;
    JTextField nameFieldX, nameFieldO;
    JButton registerButton;
    JLabel statusLabel;
    JPanel boardPanel;
    JButton[][] buttons = new JButton[3][3];
    JButton replayButton, exitButton;
    JTextArea historyBox; // ADDED
    String currentPlayer = "X";
    String playerX = "", playerO = "";
    private String mode;

    public GameGUI(String mode) {
        this.mode = mode;

        frame = new JFrame("Tic Tac Toe - " + (mode.equals("AI") ? "Play vs AI" : "2 Player Mode"));
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel(new GridLayout(4, 1));
        statusLabel = new JLabel("Enter player name(s) and click Register", SwingConstants.CENTER);

        if (mode.equals("AI")) {
            nameFieldX = new JTextField();
            registerButton = new JButton("Start Game");
            topPanel.add(new JLabel("Player Name:", SwingConstants.CENTER));
            topPanel.add(nameFieldX);
        } else {
            nameFieldX = new JTextField();
            nameFieldO = new JTextField();
            registerButton = new JButton("Start Game");
            topPanel.add(new JLabel("Player X:", SwingConstants.CENTER));
            topPanel.add(nameFieldX);
            topPanel.add(new JLabel("Player O:", SwingConstants.CENTER));
            topPanel.add(nameFieldO);
        }

        registerButton.addActionListener(e -> {
            if (mode.equals("AI")) {
                playerX = nameFieldX.getText().trim();
                if (playerX.isEmpty()) {
                    statusLabel.setText("⚠️ Please enter player name.");
                    return;
                }
                DBHandler.insertPlayer(playerX);
                statusLabel.setText("✅ Welcome, " + playerX + "! You're playing vs AI.");
            } else {
                playerX = nameFieldX.getText().trim();
                playerO = nameFieldO.getText().trim();
                if (playerX.isEmpty() || playerO.isEmpty()) {
                    statusLabel.setText("⚠️ Please enter both player names.");
                    return;
                }
                DBHandler.insertPlayer(playerX);
                DBHandler.insertPlayer(playerO);
                statusLabel.setText("✅ Let's play! " + playerX + " (X) vs " + playerO + " (O)");
            }
            clearBoard();
            displayRecentHistory();
        });

        topPanel.add(registerButton);
        topPanel.add(statusLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // Game Board
        boardPanel = new JPanel(new GridLayout(3, 3));
        Font buttonFont = new Font("Arial", Font.BOLD, 36);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(buttonFont);
                buttons[i][j] = btn;
                int row = i, col = j;
                btn.addActionListener(e -> handleMove(btn, row, col));
                boardPanel.add(btn);
            }
        }
        frame.add(boardPanel, BorderLayout.CENTER);

        // Controls
        JPanel bottomPanel = new JPanel();
        replayButton = new JButton("🔁 Replay");
        exitButton = new JButton("❌ Exit");
        replayButton.addActionListener(e -> clearBoard());
        exitButton.addActionListener(e -> System.exit(0));
        bottomPanel.add(replayButton);
        bottomPanel.add(exitButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // History Panel
        historyBox = new JTextArea();
        historyBox.setEditable(false);
        historyBox.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(historyBox);
        scrollPane.setPreferredSize(new Dimension(250, 0));
        frame.add(scrollPane, BorderLayout.EAST);

        frame.setVisible(true);
    }

    private void handleMove(JButton btn, int row, int col) {
        if (!btn.getText().isEmpty()) return;
        btn.setText(currentPlayer);

        String resultText = "";
        boolean gameOver = false;

        if (GameLogic.checkWin(buttons, currentPlayer)) {
            resultText = "Win";
            gameOver = true;
            statusLabel.setText("🎉 " + (mode.equals("AI") && currentPlayer.equals("O") ? "AI" : currentPlayer.equals("X") ? playerX : playerO) + " wins!");
        } else if (GameLogic.isDraw(buttons)) {
            resultText = "Draw";
            gameOver = true;
            statusLabel.setText("🤝 It's a draw!");
        }

        if (gameOver) {
            if (mode.equals("AI")) {
                DBHandler.updateStats(playerX, currentPlayer.equals("X"));
                DBHandler.logGame(playerX, "AI", resultText, "AI");
            } else {
                boolean xWon = currentPlayer.equals("X");
                DBHandler.updateStats(playerX, xWon);
                DBHandler.updateStats(playerO, !xWon);
                DBHandler.logGame(playerX, playerO, xWon ? "Win" : "Loss", "2P");
                DBHandler.logGame(playerO, playerX, !xWon ? "Win" : "Loss", "2P");
            }
            disableBoard();
            displayRecentHistory();
            return;
        }

        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        statusLabel.setText("Player " + currentPlayer + "'s turn");

        if (mode.equals("AI") && currentPlayer.equals("O")) {
            makeAIMove();
        }
    }

    // --- AI Logic ---
    private void makeAIMove() {
        if (tryToPlay("O")) return;
        if (tryToPlay("X")) return;
        if (buttons[1][1].getText().isEmpty()) {
            buttons[1][1].setText("O");
            finalizeAIMove();
            return;
        }
        int[][] corners = {{0,0},{0,2},{2,0},{2,2}};
        for (int[] c : corners) {
            if (buttons[c[0]][c[1]].getText().isEmpty()) {
                buttons[c[0]][c[1]].setText("O");
                finalizeAIMove();
                return;
            }
        }
        int[][] sides = {{0,1},{1,0},{1,2},{2,1}};
        for (int[] s : sides) {
            if (buttons[s[0]][s[1]].getText().isEmpty()) {
                buttons[s[0]][s[1]].setText("O");
                finalizeAIMove();
                return;
            }
        }
    }

    private boolean tryToPlay(String symbol) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().isEmpty()) {
                    String original = buttons[i][j].getText();
                    buttons[i][j].setText(symbol);
                    boolean win = GameLogic.checkWin(buttons, symbol);
                    buttons[i][j].setText(original);
                    if (win) {
                        buttons[i][j].setText("O");
                        finalizeAIMove();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void finalizeAIMove() {
        if (GameLogic.checkWin(buttons, "O")) {
            statusLabel.setText("🤖 AI wins!");
            DBHandler.updateStats(playerX, false);
            DBHandler.logGame(playerX, "AI", "Loss", "AI");
            disableBoard();
            displayRecentHistory();
        } else if (GameLogic.isDraw(buttons)) {
            statusLabel.setText("🤝 It's a draw!");
            DBHandler.updateStats(playerX, false);
            DBHandler.logGame(playerX, "AI", "Draw", "AI");
            displayRecentHistory();
        } else {
            currentPlayer = "X";
            statusLabel.setText("Player X's turn");
        }
    }

    private void clearBoard() {
        currentPlayer = "X";
        statusLabel.setText("New game started. Player X's turn.");
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                btn.setText("");
                btn.setEnabled(true);
            }
        }
    }

    private void disableBoard() {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                btn.setEnabled(false);
            }
        }
    }

    // ✅ NEW: Show recent games in historyBox
    private void displayRecentHistory() {
        StringBuilder text = new StringBuilder("📜 Last 10 games:\n\n");

        if (mode.equals("AI")) {
            List<String> recent = DBHandler.fetchLast10Games(playerX);
            for (String line : recent) {
                text.append("🧠 ").append(line).append("\n");
            }
        } else {
            List<String> xGames = DBHandler.fetchLast10Games(playerX);
            List<String> oGames = DBHandler.fetchLast10Games(playerO);

            text.append("👤 ").append(playerX).append(":\n");
            for (String line : xGames) {
                text.append("   ").append(line).append("\n");
            }

            text.append("\n👤 ").append(playerO).append(":\n");
            for (String line : oGames) {
                text.append("   ").append(line).append("\n");
            }
        }

        historyBox.setText(text.toString());
    }
}