package com.flappybird.ex.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.ex.FlappyBirdEx;


public class Menu {
    protected final FlappyBirdEx game;
    protected Texture menuBg;
    protected boolean isShow;

    public Menu() {
        menuBg = new Texture("sprites/restart.png");
        game = new FlappyBirdEx();
    }

    public void render(SpriteBatch batch) {
        if (!isShow) return;
        batch.draw(menuBg, (float) (game.WORLD_WIDTH / 2) - 107, (float) (game.WORLD_HEIGHT / 2) - 37.5f);
        System.out.println("menu");
    }

    public void show() {
        isShow = true;
    }

    public void hide() {
        isShow = false;
    }

}
