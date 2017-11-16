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
public class ChordFragment extends Fragment {

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private ArrayList<Chord> mChordList = new ArrayList<>();
    private ListView mChordsListView;

    public ChordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chord, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDataFromDb();
        bindDataToListView();
    }

    private void bindDataToListView() {
        mChordsListView = (ListView) getView().findViewById(R.id.chords_list_view);

        ArrayAdapter adapter = new ArrayAdapter<Chord>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                mChordList
        );
        mChordsListView.setAdapter(adapter);

        mChordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Chord chord = mChordList.get(i);
                String name = chord.name;
                String picture = chord.picture;

                Intent intent = new Intent(getActivity(), SongDetailsActivity.class);
                intent.putExtra("title", "คอร์ด " + name);
                intent.putExtra("artist", "");
                intent.putExtra("lyric", picture);
                startActivity(intent);
            }
        });
    }

    private void loadDataFromDb() {
        mHelper = new DatabaseHelper(getActivity());
        mDb = mHelper.getWritableDatabase();

        Cursor cursor = mDb.query(DatabaseHelper.TABLE_NAME_CHORD, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_NAME));
            String picture = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COL_PICTURE));

            String msg = String.format(
                    Locale.getDefault(),
                    "id: %d, name: %s, picture: %s",
                    id, name, picture
            );

            Log.v("MainActivity", msg);

            Chord chord = new Chord(id, name, picture);
            mChordList.add(chord);
        }
    }
}
