package com.example.ansh.scoretracker;

public class MyItem {
    private String setIndex;
    private String winnerScore;
    private String loserScore;

    public MyItem(String setIndex, String winnerScore, String loserScore) {
        this.setIndex = setIndex;
        this.winnerScore = winnerScore;
        this.loserScore = loserScore;
    }

    public String getSetIndex() {
        return setIndex;
    }

    public String getLoserScore() {
        return loserScore;
    }

    public String getWinnerScore() {
        return winnerScore;
    }
}
