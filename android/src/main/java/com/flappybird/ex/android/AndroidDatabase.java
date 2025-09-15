package com.flappybird.ex.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.flappybird.ex.ScoreDatabase;

public class AndroidDatabase extends SQLiteOpenHelper implements ScoreDatabase {
    private static final String DATABASE_NAME = "flappybird.db";
    private static final int DATABASE_VERSION = 1;

    // Table
    private static final String TABLE_NAME = "SCOREBOARD";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_POINTS = "points";
    private static final String COLUMN_TIME = "time";
    public AndroidDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_POINTS + " INTEGER"
            + COLUMN_NAME + "TEXT"
            + COLUMN_TIME + "TEXT )";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public void insertScore(int points) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("points", points);
        db.insert("SCOREBOARD", null, values);
        db.close();
    }

    @Override
    public int getHighScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(points) FROM SCOREBOARD", null);
        int highScore = cursor.moveToFirst() ? cursor.getInt(0) : 0;
        cursor.close();
        db.close();
        return highScore;
    }
}
