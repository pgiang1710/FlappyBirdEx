package com.flappybird.ex.entities;

import static com.flappybird.ex.managers.TextureManager.groundTexture;
import static com.flappybird.ex.managers.TextureManager.backgroundTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.flappybird.ex.FlappyBirdEx;

public class Background {
    protected final TextureRegion groundTop;
    protected float groundOffsetX = 0;
    protected final int groundHeight;

    public Background() {
        groundTop = new TextureRegion(groundTexture);
        groundTop.flip(false, true);
        groundHeight = groundTexture.getHeight();
    }

    public void update(float delta) {
        groundOffsetX -= FlappyBirdEx.SCROLL_SPEED * delta;
        if (groundOffsetX <= -groundTexture.getWidth()) {
            groundOffsetX += groundTexture.getWidth();
        }
    }

    public TextureRegion getGroundTop() { return groundTop; }

    public float getGroundOffsetX() { return groundOffsetX; }

    public int getGroundHeight() {
        return groundHeight;
    }

    public void dispose() {
        backgroundTexture.dispose();
        groundTexture.dispose();
    }
}
