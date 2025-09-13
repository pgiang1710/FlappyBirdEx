package com.flappybird.ex.entities;

public class Score{
    public String name;
    public int score;

    public Score() {} // cần cho Firebase

    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
