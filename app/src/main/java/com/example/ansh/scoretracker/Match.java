package com.example.ansh.scoretracker;

public class Match {
    private String winner;
    private String loser;
    private String setString;
    // private int number_of_sets;
    // private int points;
    // private String each_set_points_winner[];
    // private String each_set_points_loser[];

    public Match(String winner, String loser, String setString) {
        this.loser = loser;
        this.winner = winner;
        this.setString = setString;
        // this.number_of_sets = number_of_sets;
        // this.each_set_points_winner = ewinner;
        // this.each_set_points_loser = eloser;
    }

    public String get_loser() {
        return loser;
    }

    public String get_setString(){
        return setString;
    }

//    public void set_loser(String loser) {
//        this.loser = loser;
//    }

    public String get_winner() {
        return winner;
    }

//    public void set_winner(String winner) {
//        this.winner = winner;
//    }

    // public int get_number_of_sets() {
    //     return number_of_sets;
    // }

    // public void set_number_of_sets(int number_of_sets) {
    //     this.number_of_sets = number_of_sets;
    // }

    // public int get_points() {
    //     return points;
    // }

    // public void set_points(int points) {
    //     this.points = points;
    // }

    // public String get_each_set_points_winner(int index) {
    //     return each_set_points_winner[index];
    // }

    // public String get_each_set_points_loser(int index) {
    //     return each_set_points_loser[index];
    // }


}