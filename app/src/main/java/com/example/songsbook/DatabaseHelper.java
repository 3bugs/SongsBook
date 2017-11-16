package com.example.songsbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Promlert on 2017-11-11.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "songsbook.db";
    private static final int DATABSE_VERSION = 1;

    public static final String TABLE_NAME_SONG = "songs";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_ARTIST = "artist";
    public static final String COL_LYRIC = "lyric";
    public static final String COL_IS_FAVORITE = "is_favorite";

    public static final String TABLE_NAME_CHORD = "chords";
    public static final String COL_NAME = "name";
    public static final String COL_PICTURE = "picture";

    private static final String SQL_CREATE_TABLE_SONGS = "CREATE TABLE " + TABLE_NAME_SONG + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TITLE + " TEXT,"
            + COL_ARTIST + " TEXT,"
            + COL_LYRIC + " TEXT,"
            + COL_IS_FAVORITE + " INTEGER)";

    private static final String SQL_CREATE_TABLE_CHORDS = "CREATE TABLE " + TABLE_NAME_CHORD + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_NAME + " TEXT,"
            + COL_PICTURE + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_SONGS);
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_CHORDS);
        insertInitialData(sqLiteDatabase);
    }

    private void insertInitialData(SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, "ทำได้เพียง");
        cv.put(COL_ARTIST, "25Hours");
        cv.put(COL_LYRIC, "song0001.png");
        cv.put(COL_IS_FAVORITE, 0);
        db.insert(TABLE_NAME_SONG, null, cv);

        cv = new ContentValues();
        cv.put(COL_TITLE, "อย่าบอก");
        cv.put(COL_ARTIST, "Atom ชนกันต์");
        cv.put(COL_LYRIC, "song0002.png");
        cv.put(COL_IS_FAVORITE, 1);
        db.insert(TABLE_NAME_SONG, null, cv);

        cv = new ContentValues();
        cv.put(COL_TITLE, "เฉยเมย");
        cv.put(COL_ARTIST, "YOUNGOHM");
        cv.put(COL_LYRIC, "song0003.png");
        cv.put(COL_IS_FAVORITE, 0);
        db.insert(TABLE_NAME_SONG, null, cv);

        cv = new ContentValues();
        cv.put(COL_NAME, "C");
        cv.put(COL_PICTURE, "chord_c.png");
        db.insert(TABLE_NAME_CHORD, null, cv);

        cv = new ContentValues();
        cv.put(COL_NAME, "D");
        cv.put(COL_PICTURE, "chord_d.png");
        db.insert(TABLE_NAME_CHORD, null, cv);

        cv = new ContentValues();
        cv.put(COL_NAME, "Am");
        cv.put(COL_PICTURE, "chord_am.png");
        db.insert(TABLE_NAME_CHORD, null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
