package com.flappybird.ex.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class SoundManager {
    private static final Sound flapSound = Gdx.audio.newSound(Gdx.files.internal("audio/wing.wav"));
    private static final Sound scoreSound = Gdx.audio.newSound(Gdx.files.internal("audio/point.wav"));
    private static final Sound hitSound = Gdx.audio.newSound(Gdx.files.internal("audio/hit.wav"));
    //private final Music bgMusic;

    public SoundManager() {
//        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("bgmusic.mp3"));
//        bgMusic.setLooping(true);
//        bgMusic.setVolume(0.5f);
    }

    // hiệu ứng
    public static void playFlap() {
        flapSound.play(1.0f);
    }

    public static void playScore() {
        scoreSound.play(1.0f);
    }

    public static void playHit() {
        hitSound.play(1.0f);
    }

    // nhạc nền
//    public void playBgMusic() {
//        if (!bgMusic.isPlaying()) {
//            bgMusic.play();
//        }
//    }
//
//    public void stopBgMusic() {
//        if (bgMusic.isPlaying()) {
//            bgMusic.stop();
//        }
//    }

    public static void dispose() {
        flapSound.dispose();
        scoreSound.dispose();
        hitSound.dispose();
        //bgMusic.dispose();
    }
}

