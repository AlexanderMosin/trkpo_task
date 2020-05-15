package com.mosin.course;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

public class GameWord implements Serializable {
    String word;

    boolean isUsed = false;

    SortedMap<Integer, String> associations = new TreeMap<>();

    public static GameWord stringToGameWord(String dictLine){
        GameWord gameWord = new GameWord();
        gameWord.word = dictLine.split(":")[0];
        String[] assocArr = dictLine.split(":")[1].replace(";", "").split(",");
        for (String assoc : assocArr) {
            String[] strings = assoc.trim().split(" ");
            gameWord.associations.put(Integer.valueOf(strings[1]), strings[0]);
        }
        return gameWord;
    }

    @Override
    public String toString() {
        return "GameWord{" +
                "word='" + word + '\'' +
                ", isUsed=" + isUsed +
                ", associations=" + associations +
                '}';
    }
}
