package com.flappybird.ex.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.flappybird.ex.FlappyBirdEx;

public class Bird {
    public enum State {
        IDLE, FLAPPY, DEAD
    }

    public float stateTime = 0f;
    public State state = State.IDLE;
    public float x;
    public float y;
    public final int width;
    public final int height;
    public float velocityY = 0f;
    public float rotation;
    public final float gravity;
    public final float jumpForce;
    public final int groundHeight;
    public final Rectangle bounds;
    public float idleBaseY;

    public Bird(float centerX, float centerY, int groundHeight, int width, int height) {
        this.width = width;
        this.height = height;
        this.x = centerX;
        this.y = centerY;
        this.idleBaseY = centerY;
        this.groundHeight = groundHeight;
        this.bounds = new Rectangle(x - width/2f, y - height/2f, width, height);
        this.gravity = -height * 60f;
        this.jumpForce = height * 15f;
    }
}
