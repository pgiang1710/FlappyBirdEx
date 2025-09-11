package com.flappybird.ex.managers;

import static com.flappybird.ex.managers.TextureManager.restart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Bird;

public class MenuManager implements Disposable {
    private BirdManager birdManager;
    private Camera camera;
    private Rectangle restartRec;
    private final float restartX = FlappyBirdEx.WORLD_WIDTH / 2f - restart.getWidth() / 2f;
    private final float restartY = FlappyBirdEx.WORLD_HEIGHT / 2f - restart.getHeight() / 2f;

    public MenuManager(BirdManager birdManager, Camera camera){
        this.birdManager = birdManager;
        this.camera = camera;
        restartRec = new Rectangle(
            restartX,
            restartY,
            restart.getWidth(),
            restart.getHeight());
    }

    public void renderRestartBtn(SpriteBatch batch){
        if(birdManager.getState() == Bird.State.DEAD){
            batch.draw(
                restart,
                restartRec.x, restartRec.y,
                restartRec.width, restartRec.height);
        }
    }

    public boolean isRestartClicked(float screenX, float screenY) {
        if (birdManager.getState() != Bird.State.DEAD) {
            return false;
        }
        Vector3 worldTouch = camera.unproject(new Vector3(screenX, screenY, 0));
//        System.out.println("worldTouchX: " + worldTouch.x + " worldTouchY: " + worldTouch.y
//            + "\nrestartX: " + restartX + " restartY: " + restartY);
        return restartRec.contains(worldTouch.x, worldTouch.y);
    }

    public void dispose(){
        restart.dispose();
    }
}
