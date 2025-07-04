package com.tictactoe.app;

public class Player {
    private String username;
    private int gamesPlayed;
    private int wins;

    public Player(String username) {
        this.username = username;
        this.gamesPlayed = 0;
        this.wins = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void incrementGamesPlayed() {
        gamesPlayed++;
    }

    public void incrementWins() {
        wins++;
    }
}
