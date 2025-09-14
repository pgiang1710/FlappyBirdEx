package com.flappybird.ex;

public interface ScoreDatabase {
    void insertScore(int score);
    int getHighScore();
}
