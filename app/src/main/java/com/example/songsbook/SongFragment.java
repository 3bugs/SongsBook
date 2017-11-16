package com.example.songsbook;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment {

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private ArrayList<Song> mSongList = new ArrayList<>();
    private ListView mSongsListView;

    public SongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataFromDb();
        bindDataToListView();
    }

    private void bindDataToListView() {
        mSongsListView = (ListView) getView().findViewById(R.id.songs_list_view);

        ArrayAdapter adapter = new ArrayAdapter<Song>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mSongList
        );
        mSongsListView.setAdapter(adapter);

        mSongsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Song song = mSongList.get(i);
                String title = song.title;
                String artist = song.artist;
                String lyrics = song.lyric;

                Intent intent = new Intent(getActivity(), SongDetailsActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("artist", artist);
                intent.putExtra("lyric", lyrics);
                startActivity(intent);
            }
        });
    }

    private void loadDataFromDb() {
        mHelper = new DatabaseHelper(getActivity());
        mDb = mHelper.getWritableDatabase();

        Cursor cursor = mDb.query(DatabaseHelper.TABLE_NAME_SONG, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ARTIST));
            String lyric = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LYRIC));

            String msg = String.format(
                    Locale.getDefault(),
                    "id: %d, title: %s, artist: %s, lyric: %s",
                    id, title, artist, lyric
            );

            Log.v("MainActivity", msg);

            Song song = new Song(id, title, artist, lyric);
            mSongList.add(song);
        }
    }
}
