package com.flappybird.ex.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Bird;
import com.flappybird.ex.managers.BackgroundManager;
import com.flappybird.ex.managers.BirdManager;
import com.flappybird.ex.managers.PipeManager;

import java.util.Random;

public class GameScreen implements Screen {

    // Các thuộc tính cần thiết
    private final FlappyBirdEx game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private Random rand;

    // Các class quản lý các đối tượng của game
    private PipeManager pipeManager;
    private BirdManager birdManager;
    private BackgroundManager backgroundManager;

    // Constructor
    public GameScreen(FlappyBirdEx game) {
        this.game = game;
        rand = new Random();
        camera = new OrthographicCamera();
        viewport = new FitViewport(FlappyBirdEx.WORLD_WIDTH, FlappyBirdEx.WORLD_HEIGHT, camera);
    }

    // Khởi tạo các đối tượng và thiết lập màn hình
    @Override
    public void show() {
        backgroundManager = new BackgroundManager();
        birdManager = new BirdManager(backgroundManager);
        pipeManager = new PipeManager(birdManager,backgroundManager);
        pipeManager.addPipes(3);
    }

    // Render các đối tượng lên màn hình
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
        birdManager.render(batch);
        batch.end();
    }

    // Cập nhật game theo delta time
    private void update(float delta) {
        // Xử lý sự kiện người dùng chạm vào màn hình và cập nhật trạng thái trò chơi <<pgiang, hieppham>>
        if (Gdx.input.justTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (birdManager.getState() == Bird.State.DEAD) {
                birdManager.setState(Bird.State.IDLE);
                birdManager.setPosition(FlappyBirdEx.WORLD_WIDTH / 3f, FlappyBirdEx.WORLD_HEIGHT / 2f);
                pipeManager.addPipes(3);
            }
            else if(birdManager.getState() == Bird.State.IDLE){
                birdManager.setState(Bird.State.FLAPPY);
            }
            birdManager.jump();
        }
        if(birdManager.getState() != Bird.State.DEAD){
            backgroundManager.updateWorld(delta);
        }
        if(birdManager.getState() == Bird.State.FLAPPY){
            pipeManager.checkCollisions();
            pipeManager.updatePipes(delta);
        }
        birdManager.update(delta);
    }

    // Cập nhật kích thước theo màn hình
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    // Pause game
    @Override
    public void pause() {

    }

    // Resume game
    @Override
    public void resume() {

    }

    // Hủy game
    @Override
    public void hide() {

    }

    // Loại bỏ các đối tượng khỏi bộ nhớ (optional)
    @Override
    public void dispose() {

    }
}
