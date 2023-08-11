package com.example.notesrecorder2;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.util.LinkedList;

public class ListViewAdapter extends BaseAdapter {

    private static final String TAG = "ListViewAdapter";
    public LinkedList<RecordsListElement> notesList;
    private final LayoutInflater inflater;
    private final RecordsListFragment mListFragment;

    private static final int BUTTON_DELETE_BASE_ID = 11011000;
    private static final int BUTTON_EDIT_BASE_ID = 55055000;

    public ListViewAdapter(RecordsListFragment listFragment, Context c) {
        this.mListFragment = listFragment;
        this.inflater = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        if (this.notesList == null) {
            return 0;
        } else {
            return this.notesList.size();
        }
    }

    @Override
    public Object getItem(int i) {
        return (Object) notesList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        view = inflater.inflate(R.layout.record_list_item, null);
        TextView id_note = (TextView) view.findViewById(R.id._id);
        TextView text_note = (TextView) view.findViewById(R.id.textnote);
        TextView audio_note = (TextView) view.findViewById(R.id.audionote);

        RecordsListElement e = notesList.get(i);
        id_note.setText(e.get_id());
        text_note.setText(e.get_txt());
        if (e.get_audio().isEmpty()) {
            audio_note.setText("(no audio note)");
        } else {
            // Extract filename and display only that
            File f = new File(e.get_audio());
            if (f.exists()) {
                audio_note.setText(f.getName());
                audio_note.setClickable(true);
            }
        }

        audio_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "audio clicked " + i);
                playAudio(i);
            }
        });

        Button btnDelete = (Button) view.findViewById(R.id.buttonDelete);
        btnDelete.setId(BUTTON_DELETE_BASE_ID + i);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClick(v);
            }
        });
        Button btnEdit = (Button) view.findViewById(R.id.buttonEdit);
        btnEdit.setId(BUTTON_EDIT_BASE_ID + i);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEditClick(v);
            }
        });

        return view;
    }

    private void onDeleteClick(View v) {
        int selectedIndex = v.getId() - BUTTON_DELETE_BASE_ID;
        Log.v(TAG, "Selected Index (delete): " + selectedIndex);
        this.mListFragment.notifyDeleteNote(selectedIndex);
    }

    private void onEditClick(View v) {
        int selectedIndex = v.getId() - BUTTON_EDIT_BASE_ID;
        Log.v(TAG, "Selected Index (edit): " + selectedIndex);
        this.mListFragment.notifyEditNote(
                Long.parseLong(this.notesList.get(selectedIndex).get_id()),
                this.notesList.get(selectedIndex).get_txt(),
                this.notesList.get(selectedIndex).get_audio());
    }

    private void playAudio(int index) {
        Uri uri = Uri.fromFile(new File(this.notesList.get(index).get_audio()));
        MediaPlayer mp = MediaPlayer.create(this.mListFragment.getContext(), uri);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                mp.release();
            }
        });

        if (mp.isPlaying() == false) {
            mp.start();
        } else {
            mp.stop();
        }
    }
}
