package com.flappybird.ex.managers;

import static com.flappybird.ex.managers.TextureManager.birdTexture;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Bird;

public class BirdManager implements Disposable {
    private Bird bird;
    private BackgroundManager backgroundManager;
    protected Animation<TextureRegion> flapAnimation;
    protected TextureRegion[] frames;
    // Các hằng số từ Bird
    private static final float IDLE_AMPLITUDE = 5f;
    private static final float IDLE_FREQUENCY = 2f;

    public BirdManager(BackgroundManager backgroundManager) {
        this.backgroundManager = backgroundManager;
        initTexture();
        initAnimation();

        // Khởi tạo Bird với các thông số cần thiết
        int birdWidth = frames[0].getRegionWidth();
        int birdHeight = frames[0].getRegionHeight();
        bird = new Bird(
            FlappyBirdEx.WORLD_WIDTH / 2.5f,
            FlappyBirdEx.WORLD_HEIGHT / 2f,
            backgroundManager.getGroundHeight(),
            birdWidth,
            birdHeight
        );
    }

    private void initTexture(){
        TextureRegion[][] tmp = TextureRegion.split(birdTexture, birdTexture.getWidth()/3, birdTexture.getHeight());
        frames = new TextureRegion[3];
        System.arraycopy(tmp[0], 0, frames, 0, 3);
    }

    private void initAnimation(){
        flapAnimation = new Animation<>(0.1f, frames);
        flapAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    // Các phương thức getter
    public Bird.State getState() {
        return bird.state;
    }

    public int getHeight() {
        return bird.height;
    }

    public float getX() {
        return bird.x;
    }

    public float getY() {
        return bird.y;
    }

    public int getWidth() {
        return bird.width;
    }

    public void setState(Bird.State state) {
        bird.state = state;
    }

    public void setPosition(float x, float y) {
        bird.x = x;
        bird.y = y;
        bird.idleBaseY = y; // Cập nhật cả idleBaseY nếu cần
    }

    // Các thao tác chính được xử lý trong Manager
    public void update(float delta) {
        switch (bird.state) {
            case IDLE:
                updateIdleState(delta);
                break;
            case DEAD:
                updateDeadState(delta);
                break;
            default:
                updateFlyingState(delta);
                break;
        }
        updateBounds();
    }

    private void updateIdleState(float delta) {
        bird.stateTime += delta;
        bird.y = bird.idleBaseY + (float)Math.sin(bird.stateTime * Math.PI * IDLE_FREQUENCY) * IDLE_AMPLITUDE;
        bird.rotation = 0f;
    }

    private void updateDeadState(float delta) {
        bird.velocityY += bird.gravity * delta;
        bird.y += bird.velocityY * delta;
        float minY = bird.groundHeight + bird.height / 2f;
        if (bird.y < minY) {
            bird.y = minY;
        }
    }

    private void updateFlyingState(float delta) {
        bird.stateTime += delta;
        updateVelocityAndPosition(delta);
        handleVerticalBounds();
        updateRotation();
    }

    private void updateVelocityAndPosition(float delta) {
        bird.velocityY += bird.gravity * delta;
        bird.y += bird.velocityY * delta;
    }

    private void handleVerticalBounds() {
        float minY = bird.groundHeight + bird.height / 2f;
        float maxY = FlappyBirdEx.WORLD_HEIGHT - bird.groundHeight - bird.height / 2f;

        if (bird.y < minY) {
            bird.y = minY;
            bird.velocityY = 0f;
            bird.state = Bird.State.DEAD;
        } else if (bird.y > maxY) {
            bird.y = maxY;
            bird.velocityY = 0f;
        }
    }

    private void updateRotation() {
        if (bird.state == Bird.State.DEAD) {
            return;
        }

        float maxUpAngle = 45f;
        float maxDownAngle = -90f;
        float maxVelocity = -bird.gravity;

        float t = bird.velocityY / maxVelocity;
        bird.rotation = t > 0
            ? t * maxUpAngle
            : -t * maxDownAngle;
        bird.rotation = Math.max(Math.min(bird.rotation, maxUpAngle), maxDownAngle);
    }

    private void updateBounds() {
        bird.bounds.setPosition(bird.x - bird.width/2f, bird.y - bird.height/2f);
    }

    public void jump() {
        bird.velocityY = bird.jumpForce;
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = flapAnimation.getKeyFrame(bird.stateTime);
        batch.draw(
            currentFrame,
            bird.x - bird.width/2f, bird.y - bird.height/2f,
            bird.width/2f, bird.height/2f,
            bird.width, bird.height,
            1f, 1f,
            bird.rotation
        );
    }

    public Rectangle getBounds() {
        return bird.bounds;
    }

    @Override
    public void dispose() {
        birdTexture.dispose();
    }
}
