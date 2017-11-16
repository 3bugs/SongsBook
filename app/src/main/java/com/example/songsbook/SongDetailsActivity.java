package com.example.songsbook;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class SongDetailsActivity extends AppCompatActivity {

    private String mTitle;
    private String mArtist;
    private String mLyric;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_details);

        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mArtist = intent.getStringExtra("artist");
        mLyric = intent.getStringExtra("lyric");

        String msg;
        if (mArtist.equals("")) {
            msg = String.format(
                    Locale.getDefault(),
                    "%s",
                    mTitle
            );
        } else {
            msg = String.format(
                    Locale.getDefault(),
                    "%s (%s)",
                    mTitle,
                    mArtist
            );
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(msg);
        }

        ImageView lyricImageView = (ImageView) findViewById(R.id.lyric_image_view);

        AssetManager am = getAssets();
        try {
            InputStream stream = am.open(mLyric);
            Drawable image = Drawable.createFromStream(stream, null);
            lyricImageView.setImageDrawable(image);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
