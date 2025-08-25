package com.flappybird.ex.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Background;
import com.flappybird.ex.entities.Bird;
import com.flappybird.ex.entities.Menu;
import com.flappybird.ex.entities.Pipe;
import com.flappybird.ex.utils.GameUtils;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen {

    private final FlappyBirdEx game;
    public Menu menu;

    private final OrthographicCamera camera = new OrthographicCamera();

    private final Viewport viewport = new FitViewport(FlappyBirdEx.WORLD_WIDTH, FlappyBirdEx.WORLD_HEIGHT, camera);

    private Background background;

    private Bird bird;

    private Pipe[] pipes = new Pipe[3];

    private Texture pipeTexture;

    private Texture pipeGapTexture;

    private ShapeRenderer shapeRenderer = new ShapeRenderer()   ;

    Random rand = new Random();

    public GameScreen(FlappyBirdEx game) {
        this.game = game;

    }

    @Override
    public void show() {
        background = new Background(GameUtils.isNight());
        bird = new Bird(FlappyBirdEx.WORLD_WIDTH / 2.5f, FlappyBirdEx.WORLD_HEIGHT / 2f, background.getGroundHeight(), this);
        pipeTexture = new Texture("sprites/pipe.png");
        pipeGapTexture = new Texture("sprites/pipe_gap.png");
        menu = new Menu();
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(222 / 255f, 216/ 255f, 149/ 255f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        SpriteBatch batch = game.getSpriteBatch();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        background.renderWorld(batch, FlappyBirdEx.WORLD_WIDTH, FlappyBirdEx.WORLD_HEIGHT);

       for (Pipe pipe : pipes) {
           if (pipe != null) {
               pipe.render(batch, pipeTexture, pipeGapTexture);
           }
       }
        bird.render(batch);
        menu.render(batch);
        batch.end();
    }

    private void update(float delta) {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (bird.getState() == Bird.State.DEAD) {
                bird.setState(Bird.State.IDLE);
                bird.setPosition(FlappyBirdEx.WORLD_WIDTH / 3f, FlappyBirdEx.WORLD_HEIGHT / 2f);
                pipes = new Pipe[3];
            }
            else if(bird.getState() == Bird.State.IDLE){
                bird.setState(Bird.State.FLAPPY);
            }
            bird.jump();
        }
        if(bird.getState() != Bird.State.DEAD){
            background.update(delta);
        }
        if(bird.getState() == Bird.State.FLAPPY){
            float maxPipeX = FlappyBirdEx.WORLD_WIDTH / 2f;
            for(int i = 0; i < pipes.length; i++){
                Pipe pipe = pipes[i];
                if (pipe != null) {
                    if(pipe.isOut()) {
                        pipes[i] = null;
                        continue;
                    }
                    pipe.update(delta);
                    if(pipe.collides(bird.getBounds())) {
                        bird.setState(Bird.State.DEAD);
                    }
                    maxPipeX = Math.max(maxPipeX, pipe.getX());
                }
            }
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
        bird.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
    }
}
