package com.flappybird.ex.entities;

import static com.flappybird.ex.managers.TextureManager.groundTexture;
import static com.flappybird.ex.managers.TextureManager.backgroundTexture;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.flappybird.ex.FlappyBirdEx;

public class Background implements Disposable {
    // Các thuộc tính cần thiết
    protected final TextureRegion groundTop;
    protected float groundOffsetX = 0;
    protected final int groundHeight;

    // Constructor
    public Background() {
        groundTop = new TextureRegion(groundTexture);
        groundTop.flip(false, true);
        groundHeight = groundTexture.getHeight();
    }

    // Cập nhật vị trí của mặt đất
    public void update(float delta) {
        groundOffsetX -= FlappyBirdEx.SCROLL_SPEED * delta;
        if (groundOffsetX <= -groundTexture.getWidth()) {
            groundOffsetX += groundTexture.getWidth();
        }
    }

    // Getters
    public TextureRegion getGroundTop() { return groundTop; }
    public float getGroundOffsetX() { return groundOffsetX; }
    public int getGroundHeight() {
        return groundHeight;
    }

    // Loại bỏ texture khi không sử dụng
    public void dispose() {
        backgroundTexture.dispose();
        groundTexture.dispose();
    }
}
