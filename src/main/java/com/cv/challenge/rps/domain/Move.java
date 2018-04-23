package com.cv.challenge.rps.domain;

public class Move {

    private Player player;
    private String choice;

    public Move(Player player, String choice) {
        this.player = player;
        this.choice = choice;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
