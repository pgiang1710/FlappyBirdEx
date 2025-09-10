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
    // Các thuộc tính cần thiết
    private Bird bird;
    private BackgroundManager backgroundManager;
    protected Animation<TextureRegion> flapAnimation;
    protected TextureRegion[] frames;

    // Các hằng số liên quan đến animation
    private static final float IDLE_AMPLITUDE = 5f;
    private static final float IDLE_FREQUENCY = 2f;

    // Constructor
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

    // Khởi tạo texture
    private void initTexture(){
        TextureRegion[][] tmp = TextureRegion.split(birdTexture, birdTexture.getWidth()/3, birdTexture.getHeight());
        frames = new TextureRegion[3];
        System.arraycopy(tmp[0], 0, frames, 0, 3);
    }

    // Khởi tạo animation
    private void initAnimation(){
        flapAnimation = new Animation<>(0.1f, frames);
        flapAnimation.setPlayMode(Animation.PlayMode.LOOP);
    }

    // Getters và Setters
    public Bird.State getState() {
        return bird.state;
    }
    public void setState(Bird.State state) {
        bird.state = state;
    }
    public void setPosition(float x, float y) {
        bird.x = x;
        bird.y = y;
        bird.idleBaseY = y; // Cập nhật cả idleBaseY nếu cần
    }

    // Cập nhật trạng thái của Bird
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

    // Các phương thức cập nhật trạng thái cho từng trạng thái riêng biệt của Bird (IDLE- Đứng yên, FLAPPY- đang bay, DEAD- Đã chết)
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

    // Cập nhật vị trí và vận tốc rơi cho Bird
    private void updateVelocityAndPosition(float delta) {
        bird.velocityY += bird.gravity * delta;
        bird.y += bird.velocityY * delta;
    }

    // Xử lý biên giới hạn trên dưới cho Bird
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

    // Cập nhật góc xoay cho Bird
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

    // Cập nhật hitbox cho Bird
    private void updateBounds() {
        bird.bounds.setPosition(bird.x - bird.width/2f, bird.y - bird.height/2f);
    }

    // Hành động nhảy
    public void jump() {
        bird.velocityY = bird.jumpForce;
    }

    // Render Bird
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

    // Getter cho hitbox
    public Rectangle getBounds() {
        return bird.bounds;
    }

    // Loại bỏ texture khi không sử dụng
    @Override
    public void dispose() {
        birdTexture.dispose();
    }
}
