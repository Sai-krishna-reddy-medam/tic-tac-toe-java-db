package com.tictactoe.app;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ensure GUI runs on the Event Dispatch Thread (best practice)
        SwingUtilities.invokeLater(() -> {
            new StartScreen(); // Launch the game from the start menu
        });
    }
}
// This is the main entry point for the Tic Tac Toe game application.
