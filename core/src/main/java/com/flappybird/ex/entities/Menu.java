package com.flappybird.ex.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.screens.GameScreen;

public class Menu {
    private Texture menuBg;
    private boolean isShow;

    public Menu() {
        menuBg = new Texture("sprites/restart.png"); // file phải nằm trong android/assets/sprites/
    }

    public void render(SpriteBatch batch) {
        if (!isShow) return;
        batch.draw(menuBg, 0, 0);
        System.out.println("menu");
    }

    public void show() {
        isShow = true;
    }

    public void hide() {
        isShow = false;
    }

}
