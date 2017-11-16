package com.example.songsbook;

import java.util.Locale;

/**
 * Created by Promlert on 2017-11-12.
 */

public class Song {

    public final int id;
    public final String title;
    public final String artist;
    public final String lyric;

    public Song(int id, String title, String artist, String lyric) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.lyric = lyric;
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
