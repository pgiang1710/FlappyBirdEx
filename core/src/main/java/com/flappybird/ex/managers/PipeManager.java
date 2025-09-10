package com.flappybird.ex.managers;

import static com.flappybird.ex.managers.TextureManager.pipeTexture;
import static com.flappybird.ex.managers.TextureManager.pipeGapTexture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Background;
import com.flappybird.ex.entities.Bird;
import com.flappybird.ex.entities.Pipe;

import java.util.Random;

public class PipeManager {
    private Pipe[] pipes;
    private float maxPipeX;
    private BirdManager birdManager;
    private BackgroundManager backgroundManager;

    public PipeManager(BirdManager birdManager, BackgroundManager backgroundManager) {
        this.birdManager = birdManager;
        this.backgroundManager = backgroundManager;
        maxPipeX = FlappyBirdEx.WORLD_WIDTH / 2f;
    }

    public void renderPipes(SpriteBatch batch) {
        for (Pipe pipe : pipes) {
            if (pipe != null) {
                pipe.render(batch,pipeTexture, pipeGapTexture);
            }
        }
    }
    public void addPipes(int quantity){
        pipes = new Pipe[quantity];
    }
    public void updatePipes(Background background, Random rand){
        for(int i = 0; i < pipes.length; i++){
            Pipe pipe = pipes[i];
            if (pipe != null) {
                continue;
            }
            maxPipeX += (float) (FlappyBirdEx.WORLD_WIDTH / 2.5 + rand.nextInt(FlappyBirdEx.WORLD_WIDTH - FlappyBirdEx.WORLD_WIDTH / 2 + 1));
            //pipes[i] = new Pipe(maxPipeX, (int) (bird.getHeight() * (4.5f + rand.nextInt(3))), background.getGroundHeight(), FlappyBirdEx.WORLD_HEIGHT - background.getGroundHeight(), pipeGapTexture.getWidth());
            pipes[i] = new Pipe(maxPipeX, 300, background.getGroundHeight(), FlappyBirdEx.WORLD_HEIGHT - background.getGroundHeight(), pipeGapTexture.getWidth());
        }
    }
    public void checkCollisions(float delta){
        maxPipeX = FlappyBirdEx.WORLD_WIDTH / 2f;
        for(int i = 0; i < pipes.length; i++){
            Pipe pipe = pipes[i];
            if (pipe != null) {
                if(pipe.isOut()) {
                    pipes[i] = null;
                    continue;
                }
                pipe.update(delta);
                if(pipe.collides(birdManager.getBounds())) {
                    birdManager.setState(Bird.State.DEAD);
                }
                maxPipeX = Math.max(maxPipeX, pipe.getX());
            }
        }
    }
}
