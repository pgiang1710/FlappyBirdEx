package com.flappybird.ex.managers;

import static com.flappybird.ex.managers.TextureManager.num0;
import static com.flappybird.ex.managers.TextureManager.num1;
import static com.flappybird.ex.managers.TextureManager.num2;
import static com.flappybird.ex.managers.TextureManager.num3;
import static com.flappybird.ex.managers.TextureManager.num4;
import static com.flappybird.ex.managers.TextureManager.num5;
import static com.flappybird.ex.managers.TextureManager.num6;
import static com.flappybird.ex.managers.TextureManager.num7;
import static com.flappybird.ex.managers.TextureManager.num8;
import static com.flappybird.ex.managers.TextureManager.num9;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.flappybird.ex.entities.Score;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Pipe;

public class ScoreManager {
    int score = 0;
    private Texture[] numberTextures = new Texture[]{num0, num1, num2, num3, num4, num5, num6, num7, num8, num9};

    public ScoreManager(){

    }
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
                System.out.println("Score: " + score);
                SoundManager.playScore();
            }
        }
    }
//    public void renderScore(SpriteBatch batch, float x, float y) {
//        BitmapFont font = new BitmapFont();
//        font.setColor(Color.RED);
//        font.getData().setScale(2);
//        font.draw(batch, "SCORE: " + score, x, y);
//    }
    public void renderScore(SpriteBatch batch) {
        String scoreStr = String.valueOf(this.score); // ví dụ rawScore = 123 → "123"
        float x = FlappyBirdEx.WORLD_WIDTH / 2f;
        float y = FlappyBirdEx.WORLD_HEIGHT / 2f + (num0.getHeight() * 10);
        float textWidth = 0;
        for (int i = 0; i < scoreStr.length(); i++) {
            char c = scoreStr.charAt(i);
            int digit = c - '0';
            Texture numTex = numberTextures[digit];
            textWidth += numTex.getWidth() * 3.5f;
        }
        for (int i = 0; i < scoreStr.length(); i++) {
            char c = scoreStr.charAt(i);
            int digit = c - '0'; // chuyển '1' → 1, '0' → 0

            Texture numTex = numberTextures[digit];
            batch.draw(numTex, x-textWidth/2f, y, numTex.getWidth() * 3, numTex.getHeight() * 3);

            // dịch x sang phải để vẽ số tiếp theo
            x += numTex.getWidth() * 3.5f;
        }
    }
    public void renderHighestScore(SpriteBatch batch, FlappyBirdEx game){
        BitmapFont font = new BitmapFont();
        font.getData().setScale(2);
        font.setColor(Color.RED);
        font.draw(batch, "HIGHEST SCORE: " + game.getDatabase().getHighScore(), 50, FlappyBirdEx.WORLD_HEIGHT );
    }
    public int getScore() {
        return score;
    }
    public void resetScore() {
        score = 0;
    }
}
