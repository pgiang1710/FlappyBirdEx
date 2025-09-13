package com.flappybird.ex.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.managers.SoundManager;

public class Pipe {
    // Các thuộc tính cần thiết
    private float x;
    private final int minY;
    private final int maxY;
    private final int width;
    private final int y1; // Vị trí dưới cùng của ống trên
    private final int y2; // Vị trí trên cùng của ống dưới
    private boolean passed = false;
    // Constructor
    public Pipe(float spawnX, int gapHeight, int minY, int maxY, int width) {
        this.minY = minY;
        this.maxY = maxY;
        this.x = spawnX;
        this.width = width;

        int totalHeight = maxY - minY;
        int padding = totalHeight / 8;
        int gapY = minY + padding + (int)(Math.random() * (totalHeight - gapHeight - 2 * padding));

        this.y1 = gapY;
        this.y2 = gapY + gapHeight;
    }

    // Kiểm tra ống đã ra khỏi màn hình
    public boolean isOut() {
        return x < -width;
    }
    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }
    // Getter
    public float getX() {
        return x;
    }
    public float getWidth(){
        return width;
    }

    // Vẽ ống
    public void render(SpriteBatch batch, Texture pipeTexture, Texture pipeGapTexture) {
        // Vẽ ống trên
        for (int i = minY; i < y1 - pipeGapTexture.getHeight(); i += pipeTexture.getHeight()) {
            batch.draw(pipeTexture, x, i);
        }
        batch.draw(pipeGapTexture, x, y1 - pipeGapTexture.getHeight());

        // Vẽ ống dưới
        for (int i = y2; i < maxY; i += pipeTexture.getHeight()) {
            batch.draw(pipeTexture, x, i);
        }
        batch.draw(pipeGapTexture, x, y2, width, pipeGapTexture.getHeight(),
            0, 0, width, pipeGapTexture.getHeight(), false, true);
    }

    // Cập nhật ống theo delta time
    public void update(float delta) {
        x -= FlappyBirdEx.SCROLL_SPEED * delta;
    }

    // Thực hiện va chạm với Bird
    public boolean collides(Rectangle rect) {
        Rectangle topPipe = new Rectangle(x, y2, width, maxY - y2);
        Rectangle bottomPipe = new Rectangle(x, minY, width, y1 - minY);
        return rect.overlaps(topPipe) || rect.overlaps(bottomPipe);
    }

    // Thực hiện vẽ hitbox để debug
    public void renderHitbox(ShapeRenderer shapeRenderer) {
        shapeRenderer.rect(x, minY, width, y1 - minY);    // Ống dưới
        shapeRenderer.rect(x, y2, width, maxY - y2);      // Ống trên
    }
}
