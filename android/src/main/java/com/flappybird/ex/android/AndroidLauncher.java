package com.flappybird.ex.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.flappybird.ex.FlappyBirdEx;

/** Launches the Android application. */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
        configuration.useImmersiveMode = true; // Recommended, but not required.
        initialize(new FlappyBirdEx(), configuration);

        AndroidDatabase db = new AndroidDatabase(this); // SQLiteOpenHelper implement ScoreDatabase
        initialize(new FlappyBirdEx(db), new AndroidApplicationConfiguration());
    }
}
