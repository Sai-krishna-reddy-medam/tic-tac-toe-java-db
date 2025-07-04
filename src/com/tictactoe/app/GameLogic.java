package com.tictactoe.app;

import javax.swing.JButton;

public class GameLogic {
    public static boolean checkWin(JButton[][] buttons, String symbol) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(symbol) &&
                buttons[i][1].getText().equals(symbol) &&
                buttons[i][2].getText().equals(symbol)) return true;

            if (buttons[0][i].getText().equals(symbol) &&
                buttons[1][i].getText().equals(symbol) &&
                buttons[2][i].getText().equals(symbol)) return true;
        }

        if (buttons[0][0].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][2].getText().equals(symbol)) return true;

        if (buttons[0][2].getText().equals(symbol) &&
            buttons[1][1].getText().equals(symbol) &&
            buttons[2][0].getText().equals(symbol)) return true;

        return false;
    }

    public static boolean isDraw(JButton[][] buttons) {
        for (JButton[] row : buttons)
            for (JButton btn : row)
                if (btn.getText().isEmpty()) return false;
        return true;
    }
}
