package com.flappybird.ex;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.ex.entities.Menu;
import com.flappybird.ex.screens.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FlappyBirdEx extends Game {
    // Các hằng số liên quan đến kích thước màn hình
    public static final int WORLD_WIDTH = 768;
    public static final int WORLD_HEIGHT = 1433  ;

    // Hằng số liên quan đến tốc độ cuộn của background
    public static final float SCROLL_SPEED = 200f;
    private ScoreDatabase database;

    // Các thuộc tính cần thiết
    private SpriteBatch spriteBatch;

    public FlappyBirdEx(ScoreDatabase scoreDatabase) {
        super();
        this.database = scoreDatabase;
    }
    public FlappyBirdEx() {
        super();
    }

    // Tạo game screen và set màn hình
    @Override
    public void create(){
        init();
        setScreen(new GameScreen(this));
    }

    // Getters & Setters
    private void init() {
        spriteBatch = new SpriteBatch();
    }
    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
    public ScoreDatabase getDatabase() {
        return database;
    }
}
