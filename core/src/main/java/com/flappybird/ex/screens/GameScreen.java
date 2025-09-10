package com.flappybird.ex.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Background;
import com.flappybird.ex.entities.Bird;
import com.flappybird.ex.entities.Menu;
import com.flappybird.ex.managers.BackgroundManager;
import com.flappybird.ex.managers.BirdManager;
import com.flappybird.ex.managers.PipeManager;
import com.flappybird.ex.utils.GameUtils;

import java.util.Random;

public class GameScreen implements Screen {

    private final FlappyBirdEx game;
    public Menu menu;
    private final OrthographicCamera camera = new OrthographicCamera();
    private final Viewport viewport = new FitViewport(FlappyBirdEx.WORLD_WIDTH, FlappyBirdEx.WORLD_HEIGHT, camera);
    private Bird bird;
    Random rand = new Random();
    private PipeManager pipeManager;
    private BirdManager birdManager;
    private BackgroundManager backgroundManager;

    public GameScreen(FlappyBirdEx game) {
        this.game = game;
    }

    @Override
    public void show() {
        backgroundManager = new BackgroundManager();
        bird = new Bird(FlappyBirdEx.WORLD_WIDTH / 2.5f, FlappyBirdEx.WORLD_HEIGHT / 2f, backgroundManager.getGroundHeight());
        menu = new Menu();

        pipeManager = new PipeManager();
        pipeManager.addPipes(3);
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

        backgroundManager.renderWorld(batch);
        pipeManager.renderPipes(batch);
        bird.render(batch);
        menu.render(batch);
        batch.end();
    }

    private void update(float delta) {
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (bird.getState() == Bird.State.DEAD) {
                bird.setState(Bird.State.IDLE);
                bird.setPosition(FlappyBirdEx.WORLD_WIDTH / 3f, FlappyBirdEx.WORLD_HEIGHT / 2f);
                pipeManager.addPipes(3);
            }
            else if(bird.getState() == Bird.State.IDLE){
                bird.setState(Bird.State.FLAPPY);
            }
            bird.jump();
        }
        if(bird.getState() != Bird.State.DEAD){
            backgroundManager.updateWorld(delta);
        }
        if(bird.getState() == Bird.State.FLAPPY){
            pipeManager.checkCollisions(bird, delta);
            pipeManager.updatePipes(backgroundManager.getBackground(), rand);
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

    }
}
