package com.tictactoe.app;

import javax.swing.*;
import java.awt.*;

public class StartScreen {

    public StartScreen() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome to Tic Tac Toe!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 20));

        JButton singlePlayerBtn = new JButton("🎯 Play vs AI");
        JButton twoPlayerBtn = new JButton("🤝 2 Player Mode");
        JButton exitButton = new JButton("❌ Exit");

        singlePlayerBtn.addActionListener(e -> {
            frame.dispose();
            new GameGUI("AI"); // Launch GameGUI in AI mode
        });

        twoPlayerBtn.addActionListener(e -> {
            frame.dispose();
            new GameGUI("2P"); // Launch GameGUI in Two-Player mode
        });

        exitButton.addActionListener(e -> System.exit(0));

        frame.add(welcomeLabel);
        frame.add(singlePlayerBtn);
        frame.add(twoPlayerBtn);
        frame.add(exitButton);

        frame.setVisible(true);
    }
}
