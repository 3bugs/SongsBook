package com.example.songsbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.Locale;

/**
 * Created by Promlert on 2017-11-12.
 */

public class Song {

    public final int id;
    public final String title;
    public final String artist;
    public final String lyric;
    public int isFavorite;

    public Song(int id, String title, String artist, String lyric, int isFavorite) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.lyric = lyric;
        this.isFavorite = isFavorite;
    }

    public boolean updateFavorite(Context context, int isFavorite) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COL_IS_FAVORITE, isFavorite);

        int numRowAffected = db.update(
                DatabaseHelper.TABLE_NAME_SONG,
                cv,
                DatabaseHelper.COL_ID + "=?",
                new String[]{String.valueOf(this.id)}
        );

        if (numRowAffected == 1) {
            this.isFavorite = isFavorite;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String msg = String.format(
                Locale.getDefault(),
                "%s (%s)",
                title,
                artist
        );
        return msg;
    }
}
