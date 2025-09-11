package com.flappybird.ex.managers;

import static com.flappybird.ex.managers.TextureManager.pipeTexture;
import static com.flappybird.ex.managers.TextureManager.pipeGapTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Background;
import com.flappybird.ex.entities.Bird;
import com.flappybird.ex.entities.Pipe;

import java.util.Random;

public class PipeManager {
    private Pipe[] pipes;
    private float maxPipeX;
    private final BirdManager birdManager;
    private final BackgroundManager backgroundManager;
    private final Random rand;

    // Constructor
    public PipeManager(BirdManager birdManager, BackgroundManager backgroundManager) {
        this.birdManager = birdManager;
        this.backgroundManager = backgroundManager;
        this.rand = new Random();
        this.maxPipeX = FlappyBirdEx.WORLD_WIDTH / 2f;
        this.pipes = new Pipe[0];
    }

    // Render các ống
    public void renderPipes(SpriteBatch batch) {
        for (Pipe pipe : pipes) {
            if (pipe != null) {
                pipe.render(batch, pipeTexture, pipeGapTexture);
            }
        }
    }

    // Thêm các ống mới
    public void addPipes(int quantity) {
       pipes = new Pipe[quantity];
    }

    public void updatePipes(float delta) {
        Background background = backgroundManager.getBackground();

        // Cập nhật và tạo ống mới
        for (int i = 0; i < pipes.length; i++) {
            if (pipes[i] == null) {
                createNewPipe(i, background);
            } else {
                pipes[i].update(delta);
            }
        }
    }

    // Tạo ống mới
    private void createNewPipe(int index, Background background) {
        maxPipeX += FlappyBirdEx.WORLD_WIDTH / 2.5f + rand.nextInt(FlappyBirdEx.WORLD_WIDTH / 2);

        int gapHeight = 300; // Có thể random hoặc tính toán
        int groundHeight = background.getGroundHeight();
        int worldHeight = FlappyBirdEx.WORLD_HEIGHT - groundHeight;
        int gapWidth = pipeGapTexture.getWidth();

        pipes[index] = new Pipe(maxPipeX, gapHeight, groundHeight, worldHeight, gapWidth);
    }

    // Kiểm tra va chạm với Bird
    public void checkCollisions() {
        maxPipeX = FlappyBirdEx.WORLD_WIDTH / 2f;

        for (int i = 0; i < pipes.length; i++) {
            Pipe pipe = pipes[i];
            if (pipe != null) {
                if (pipe.isOut()) {
                    pipes[i] = null; // Xóa pipe đã ra khỏi màn hình
                    continue;
                }

                if (pipe.collides(birdManager.getBounds())) {
                    birdManager.setState(Bird.State.DEAD);
                }

                maxPipeX = Math.max(maxPipeX, pipe.getX());
            }

        }
    }
    public Pipe[] getPipes() {
        return pipes;
    }
    // Phương thức helper để debug
    public void renderHitboxes(ShapeRenderer shapeRenderer) {
        for (Pipe pipe : pipes) {
            if (pipe != null) {
                pipe.renderHitbox(shapeRenderer);
            }
        }
    }
}
