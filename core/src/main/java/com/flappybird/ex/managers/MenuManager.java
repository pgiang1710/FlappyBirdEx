package com.flappybird.ex.managers;

import static com.flappybird.ex.managers.TextureManager.easy;
import static com.flappybird.ex.managers.TextureManager.exit;
import static com.flappybird.ex.managers.TextureManager.medium;
import static com.flappybird.ex.managers.TextureManager.restart;
import static com.flappybird.ex.managers.TextureManager.leaderBoard;
import static com.flappybird.ex.managers.TextureManager.share;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.flappybird.ex.FlappyBirdEx;
import com.flappybird.ex.entities.Bird;

public class MenuManager implements Disposable {
    private BirdManager birdManager;
    private Camera camera;
    private Rectangle easyRec;
    private Rectangle mediumRec;
    private Rectangle exitRec;
    private final float baseX = FlappyBirdEx.WORLD_WIDTH / 2f;
    private final float baseY = FlappyBirdEx.WORLD_HEIGHT / 2f;

    public MenuManager(BirdManager birdManager, Camera camera){
        this.birdManager = birdManager;
        this.camera = camera;

        exitRec = new Rectangle(
            baseX - leaderBoard.getWidth()/ 2f,
            baseY - 50,
            leaderBoard.getWidth(),
            leaderBoard.getHeight());
        mediumRec = new Rectangle(
            baseX + leaderBoard.getWidth()/ 2f - medium.getWidth(),
            baseY - medium.getHeight() / 2f + 100,
            medium.getWidth(),
            medium.getHeight());
        easyRec = new Rectangle(
            exitRec.x,
            baseY - easy.getHeight() / 2f + 100,
            easy.getWidth(),
            easy.getHeight());


    }
    public void renderButton(SpriteBatch batch, int scroolSpeed){

    }
    public void renderRestartBtn(SpriteBatch batch){
        if(birdManager.getState() == Bird.State.DEAD){
            batch.draw(
                easy,
                easyRec.x, easyRec.y,
                easyRec.width, easyRec.height);
        }
    }
    public void renderLeaderboardBtn(SpriteBatch batch){
        if(birdManager.getState() == Bird.State.DEAD){
            batch.draw(
                exit,
                exitRec.x /2f, exitRec.y,
                exitRec.width, exitRec.height);
        }
    }
    public void rendershareBtn(SpriteBatch batch){
        if(birdManager.getState() == Bird.State.DEAD){
            batch.draw(
                medium,
                mediumRec.x, mediumRec.y,
                mediumRec.width, mediumRec.height);
        }
    }

    public boolean isRestartClicked(float screenX, float screenY) {
        if (birdManager.getState() != Bird.State.DEAD) {
            return false;
        }
        Vector3 worldTouch = camera.unproject(new Vector3(screenX, screenY, 0));
//        System.out.println("worldTouchX: " + worldTouch.x + " worldTouchY: " + worldTouch.y
//            + "\nrestartX: " + restartX + " restartY: " + restartY);
        return easyRec.contains(worldTouch.x, worldTouch.y);
    }

    public void dispose() {
        restart.dispose();
    }
}
