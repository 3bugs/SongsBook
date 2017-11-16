package com.example.songsbook;

/**
 * Created by Promlert on 2017-11-16.
 */

public class Chord {

    public final int id;
    public final String name;
    public final String picture;

    public Chord(int id, String name, String picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "คอร์ด " + name;
    }
}
