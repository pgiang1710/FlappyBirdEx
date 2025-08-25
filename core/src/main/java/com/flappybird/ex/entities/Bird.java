package com.flappybird.ex.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.flappybird.ex.FlappyBirdEx;

public class Bird implements Disposable {

    public enum State {
        IDLE,
        FLAPPY,
        DEAD
    }

    private final Texture spriteSheet;
    private final Animation<TextureRegion> flapAnimation;
    private float stateTime = 0f;

    private State state = State.IDLE;

    private float x;
    private float y;
    private final int width;
    private final int height;

    private float velocityY = 0f;
    private float rotation;
    private final float gravity;
    private final float jumpForce;

    private final int groundHeight;
    private final Rectangle bounds;

    public Bird(float centerX, float centerY, int groundHeight) {
        spriteSheet = new Texture("sprites/bird.png");
        TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteSheet.getWidth()/3, spriteSheet.getHeight());
        TextureRegion[] frames = new TextureRegion[3];
        System.arraycopy(tmp[0], 0, frames, 0, 3);
        flapAnimation = new Animation<>(0.1f, frames);
        flapAnimation.setPlayMode(Animation.PlayMode.LOOP);
        width = frames[0].getRegionWidth();
        height = frames[0].getRegionHeight();
        this.x = centerX;
        this.y = centerY;
        this.idleBaseY = centerY;
        this.groundHeight = groundHeight;
        bounds = new Rectangle(x - width/2f, y - height/2f, width, height);
        gravity = -height * 60f;
        jumpForce = height * 15f;
    }

    private static final float IDLE_AMPLITUDE = 5f;  // pixels
    private static final float IDLE_FREQUENCY = 2f;  // dao động 2 lần / giây
    private float idleBaseY; // lưu y gốc khi vào trạng thái IDLE

    public State getState() {
        return state;
    }

    public int getHeight() {
        return height;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void  setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float delta) {
        if (state == State.IDLE) {
            stateTime += delta;
            y = idleBaseY + (float)Math.sin(stateTime * Math.PI * IDLE_FREQUENCY) * IDLE_AMPLITUDE;
            rotation = 0f;
        }
        else if (state == State.DEAD) {
            velocityY += gravity * delta;
            y += velocityY * delta;
            float minY = groundHeight + height / 2f;
            if (y < minY) {
                y = minY;
            }
        }
        else {
            stateTime += delta;
            velocityY += gravity * delta;
            y += velocityY * delta;
            float minY = groundHeight + height / 2f;
            if (y < minY) {
                y = minY;
                velocityY = 0f;
                state = State.DEAD;
            }
            else if(y > FlappyBirdEx.WORLD_HEIGHT - groundHeight  - (float) height / 2) {
                y = FlappyBirdEx.WORLD_HEIGHT - groundHeight  - (float) height / 2;
                velocityY = 0f;
            }
            else {
                float maxUpAngle = 45f;
                float maxDownAngle = -90f;
                float maxVelocity = -gravity;

                float t = velocityY / maxVelocity;
                rotation = t > 0
                    ? t * maxUpAngle
                    : -t * maxDownAngle;
                rotation = Math.max(Math.min(rotation, maxUpAngle), maxDownAngle);
            }
        }

        bounds.setPosition(x - width/2f, y - height/2f);
    }


    public void jump() {
        velocityY = jumpForce;
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = flapAnimation.getKeyFrame(stateTime);
        batch.draw(
            currentFrame,
            x - width/2f, y - height/2f,
            width/2f, height/2f,
            width, height,
            1f, 1f,
            rotation
        );
    }

    public Rectangle getBounds() {
        return bounds;
    }

    @Override
    public void dispose() {
        spriteSheet.dispose();
    }
}
