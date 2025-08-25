package com.flappybird.ex;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.ex.entities.Menu;
import com.flappybird.ex.screens.GameScreen;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FlappyBirdEx extends Game {

    public static final int WORLD_WIDTH = 768;
    public static final int WORLD_HEIGHT = 1433  ;
    public static final float SCROLL_SPEED = 200f;

    private SpriteBatch spriteBatch;

    @Override
    public void create(){
        init();
        setScreen(new GameScreen(this));
    }

    private void init() {
        spriteBatch = new SpriteBatch();
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}
