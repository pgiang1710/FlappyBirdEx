package com.flappybird.ex.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flappybird.ex.FlappyBirdEx;

public class Background {

    private final Texture backgroundTexture;
    private final Texture groundTexture;
    private final TextureRegion groundTop;
    private float groundOffsetX = 0;

    private final int groundHeight;

    public Background(boolean isNight) {
        backgroundTexture = new Texture("sprites/background.png");
        groundTexture = new Texture("sprites/ground.png");
        groundTop = new TextureRegion(groundTexture);
        groundTop.flip(false, true);
        groundHeight = groundTexture.getHeight();
    }

    public int getGroundHeight() {
        return groundHeight;
    }

    public void update(float delta) {
        groundOffsetX -= FlappyBirdEx.SCROLL_SPEED * delta;
        if (groundOffsetX <= -groundTexture.getWidth()) {
            groundOffsetX += groundTexture.getWidth();
        }
    }

    public void renderWorld(SpriteBatch batch, float worldWidth, float worldHeight) {

        batch.draw(backgroundTexture, 0, groundTexture.getHeight(), worldWidth, worldHeight - groundTexture.getHeight() * 2);
        for (float x = groundOffsetX; x < worldWidth; x += groundTexture.getWidth()) {
            batch.draw(groundTexture, x, 0);
            batch.draw(groundTop, x, worldHeight - groundTop.getRegionHeight());
        }
    }

    public void dispose() {
        backgroundTexture.dispose();
        groundTexture.dispose();
    }
}
