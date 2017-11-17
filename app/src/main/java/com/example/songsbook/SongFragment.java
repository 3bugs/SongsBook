package com.example.songsbook;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongFragment extends Fragment implements TextWatcher {

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private ArrayList<Song> mSongList = new ArrayList<>();
    private ArrayList<Song> mOriginalSongList = new ArrayList<>();
    private ListView mSongsListView;
    private EditText mSearchEditText;
    private ImageButton mClearSearchButton;

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

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("Songs List");

        loadDataFromDb();
        bindDataToListView();

        mSearchEditText = view.findViewById(R.id.search_edit_text);
        mSearchEditText.addTextChangedListener(this);

        mClearSearchButton = view.findViewById(R.id.clear_search_button);
        mClearSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSearchEditText.setText("");
            }
        });
    }

    private void bindDataToListView() {
        mSongsListView = (ListView) getView().findViewById(R.id.songs_list_view);

/*
        ArrayAdapter adapter = new ArrayAdapter<Song>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mSongList
        );
*/

        SongListAdapter adapter = new SongListAdapter(
                getActivity(),
                R.layout.item_song,
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

        Cursor cursor = mDb.query(DatabaseHelper.TABLE_NAME_SONG, null, null,
                null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_ARTIST));
            String lyric = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_LYRIC));
            int isFavorite = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_IS_FAVORITE));

            String msg = String.format(
                    Locale.getDefault(),
                    "id: %d, title: %s, artist: %s, lyric: %s",
                    id, title, artist, lyric
            );

            Log.v("MainActivity", msg);

            Song song = new Song(id, title, artist, lyric, isFavorite);
            mSongList.add(song);
        }

        mOriginalSongList.addAll(mSongList);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence s, int i, int i1, int i2) {
        mSongList.clear();

        String searchText = s.toString().trim();
        if (searchText.equals("")) {
            mSongList.addAll(mOriginalSongList);
        } else {
            for (Song song : mOriginalSongList) {
                if (song.title.contains(searchText)) {
                    mSongList.add(song);
                }
            }
        }

        ((ArrayAdapter) mSongsListView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
