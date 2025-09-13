package com.flappybird.ex.managers;

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
    private Rectangle restartRec;
    private Rectangle shareRec;
    private Rectangle leaderBoardRec;
    private final float baseX = FlappyBirdEx.WORLD_WIDTH / 2f;
    private final float baseY = FlappyBirdEx.WORLD_HEIGHT / 2f;

    public MenuManager(BirdManager birdManager, Camera camera){
        this.birdManager = birdManager;
        this.camera = camera;

        leaderBoardRec = new Rectangle(
            baseX - leaderBoard.getWidth()/ 2f,
            baseY - 50,
            leaderBoard.getWidth(),
            leaderBoard.getHeight());
        shareRec = new Rectangle(
            baseX + leaderBoard.getWidth()/ 2f - restart.getWidth(),
            baseY - restart.getHeight() / 2f + 100,
            restart.getWidth(),
            restart.getHeight());
        restartRec = new Rectangle(
            leaderBoardRec.x,
            baseY - share.getHeight() / 2f + 100,
            share.getWidth(),
            share.getHeight());
    }

    public void renderRestartBtn(SpriteBatch batch){
        if(birdManager.getState() == Bird.State.DEAD){
            batch.draw(
                restart,
                restartRec.x, restartRec.y,
                restartRec.width, restartRec.height);
        }
    }
    public void renderLeaderboardBtn(SpriteBatch batch){
        if(birdManager.getState() == Bird.State.DEAD){
            batch.draw(
                leaderBoard,
                leaderBoardRec.x, leaderBoardRec.y,
                leaderBoardRec.width, leaderBoardRec.height);
        }
    }
    public void rendershareBtn(SpriteBatch batch){
        if(birdManager.getState() == Bird.State.DEAD){
            batch.draw(
                share,
                shareRec.x, shareRec.y,
                shareRec.width, shareRec.height);
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

    public void dispose() {
        restart.dispose();
    }
}
