package com.flappybird.ex.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.ex.entities.Pipe;

public class ScoreManager {
    int score = 0;
    public void updateScore(PipeManager pipeManager, BirdManager birdManager) {
        Pipe[] pipes = pipeManager.getPipes();
        float birdX = birdManager.getX();

        for (Pipe pipe : pipes) {
            // Kiểm tra null trước
            if (pipe == null) continue;

            float pipeRightEdge = pipe.getX() + pipe.getWidth();

            // Bird vượt qua ống mới được tính điểm
            if (birdX > pipeRightEdge && !pipe.isPassed()) {
                score++;
                pipe.setPassed(true);
                System.out.println("Score +1! Total: " + score);
            }
        }
    }
    public void renderScore(SpriteBatch batch, float x, float y) {
        BitmapFont font = new BitmapFont();
        font.setColor(Color.RED);
        font.getData().setScale(2);
        font.draw(batch, "SCORE: " + score, x, y);
    }
    public int getScore() {
        return score;
    }
    public void resetScore() {
        score = 0;
    }
}
