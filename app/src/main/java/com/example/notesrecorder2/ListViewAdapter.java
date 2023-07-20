package com.example.notesrecorder2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ListViewAdapter extends BaseAdapter {
    Context context;
    String textNotes[];
    String audioNotes[];
    LayoutInflater inflter;

    public ListViewAdapter(Context c, String[] textNotes, String[] audioNotes) {
        this.context = c;
        this.textNotes = textNotes;
        this.audioNotes = audioNotes;
        inflter = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return textNotes.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        view = inflter.inflate(R.layout.record_list_item, null);
        TextView text_note = (TextView) view.findViewById(R.id.textnote);
        TextView audio_note = (TextView) view.findViewById(R.id.audionote);
        text_note.setText(textNotes[i]);
        text_note.setText(audioNotes[i]);

        return view;
    }
}
