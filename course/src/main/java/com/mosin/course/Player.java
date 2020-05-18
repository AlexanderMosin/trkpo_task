package com.mosin.course;

import java.io.Serializable;
import java.util.List;

public class Player implements Serializable {
    private String nickName;

    private int score = 0;

    public Player(String nickName) {
        this.nickName = nickName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return nickName + ", " + score + " балла(ов)";
    }

    public static String printPlayers(List<Player> players) {
        String names = "";
        for (Player player : players) {
            names += player.nickName + ", ";
        }
        names = names.substring(0, names.length() - 2);
        return names;
    }
}
