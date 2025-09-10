package com.flappybird.ex.managers;

import static com.flappybird.ex.managers.TextureManager.backgroundTexture;
import static com.flappybird.ex.managers.TextureManager.groundTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Background;

public class BackgroundManager {
    private Background background;
    public BackgroundManager() {
        background = new Background();
    }

    public int getGroundHeight(){
        return background.getGroundHeight();
    }

    public Background getBackground() {
        return background;
    }

    public void renderWorld(SpriteBatch batch){
        batch.draw(backgroundTexture, 0, groundTexture.getHeight(), FlappyBirdEx.WORLD_WIDTH, FlappyBirdEx.WORLD_HEIGHT - groundTexture.getHeight() * 2);
        for (float x = background.getGroundOffsetX(); x <  FlappyBirdEx.WORLD_WIDTH; x += groundTexture.getWidth()) {
            batch.draw(groundTexture, x, 0);
            batch.draw(background.getGroundTop(), x, FlappyBirdEx.WORLD_HEIGHT - background.getGroundTop().getRegionHeight());
        }
    }

    public void updateWorld(float delta){
        background.update(delta);
    }
}
