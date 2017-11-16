package com.example.songsbook;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Promlert on 2017-11-16.
 */

public class SongListAdapter extends ArrayAdapter {

    private Context mContext;
    private int mLayoutResId;
    private ArrayList<Song> mSongList;

    public SongListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Song> objects) {
        super(context, resource, objects);

        this.mContext = context;
        this.mLayoutResId = resource;
        this.mSongList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View v = convertView;
        if (v == null) {
            v = inflater.inflate(mLayoutResId, null);
        }

        TextView songTitleTextView = v.findViewById(R.id.song_title_text_view);
        ImageView starImageView = v.findViewById(R.id.star_image_view);

        final Song song = mSongList.get(position);
        songTitleTextView.setText(song.toString());

        if (song.isFavorite == 0) {
            starImageView.setImageResource(android.R.drawable.btn_star_big_off);
        } else if (song.isFavorite == 1) {
            starImageView.setImageResource(android.R.drawable.btn_star_big_on);
        }

        starImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (song.updateFavorite(mContext, 1 - song.isFavorite)) {
                    notifyDataSetChanged();

                    String msg;
                    if (song.isFavorite == 1) {
                        msg = String.format(
                                Locale.getDefault(),
                                "เพิ่มเพลง '%s' ในรายการเพลงโปรดแล้ว",
                                song.title
                        );
                    } else {
                        msg = String.format(
                                Locale.getDefault(),
                                "ลบเพลง '%s' ออกจากรายการเพลงโปรดแล้ว",
                                song.title
                        );
                    }
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}
