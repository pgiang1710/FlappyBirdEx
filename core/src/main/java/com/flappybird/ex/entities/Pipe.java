package com.flappybird.ex.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.flappybird.ex.FlappyBirdEx;

public class Pipe {

    private float x;
    private int minY;
    private int maxY;
    private int width;
    private int y1;
    private int y2;

    public Pipe(float spawnX, int gapHeight, int minY, int maxY, int width) {
        this.minY = minY;
        this.maxY = maxY;
        this.x = spawnX;

        int totalHeight = maxY - minY;
        int padding = totalHeight / 8;

        int gapY = minY + padding + (int)(Math.random() * (totalHeight - gapHeight - 2 * padding));

        y1 = gapY;
        y2 = gapY + gapHeight;
        this.width = width;
    }

    public boolean isOut() {
        return x < -width + 10;
    }

    public float getX() {
        return x;
    }

    public void render(SpriteBatch batch, Texture pipeTexture, Texture pipeGapTexture) {
        for (int i = minY; i < y1 - pipeGapTexture.getHeight(); i += pipeTexture.getHeight()) {
            batch.draw(pipeTexture, x, i);
        }
        batch.draw(pipeGapTexture, x, y1 - pipeGapTexture.getHeight());
        for (int i = y2; i < maxY; i += pipeTexture.getHeight()) {
            batch.draw(pipeTexture, x, i);
        }
        batch.draw(
            pipeGapTexture,
            x,
            y2,
            pipeGapTexture.getWidth(),
            pipeGapTexture.getHeight(),
            0, 0,
            pipeGapTexture.getWidth(), pipeGapTexture.getHeight(),
            false,
            true
        );
    }

    public void update(float delta) {
        x -= FlappyBirdEx.SCROLL_SPEED * delta;
    }

    public boolean collides(Rectangle rect) {
        Rectangle topPipe = new Rectangle(x, y2, width, maxY - y2);
        Rectangle bottomPipe = new Rectangle(x, minY, width, y1 - minY);

        return rect.overlaps(topPipe) || rect.overlaps(bottomPipe);
    }

    // ✅ Hàm vẽ hitbox để debug
    public void renderHitbox(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(x, minY, width, y1 - minY);
        shapeRenderer.rect(x, y2, width, maxY - y2);
    }
}
