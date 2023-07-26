package com.example.notesrecorder2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.LinkedList;

public class ListViewAdapter extends BaseAdapter {
    Context context;
    LinkedList<RecordsListElement> list;
    LayoutInflater inflter;

    public ListViewAdapter(Context c, LinkedList<RecordsListElement> linkedList) {
        this.context = c;
        this.list = linkedList;
        inflter = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return (Object)list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewgroup) {
        view = inflter.inflate(R.layout.record_list_item, null);
        TextView id_note = (TextView) view.findViewById(R.id._id);
        TextView text_note = (TextView) view.findViewById(R.id.textnote);
        TextView audio_note = (TextView) view.findViewById(R.id.audionote);
        RecordsListElement e = list.get(i);
        id_note.setText(e._id);
        text_note.setText(e._txt);
        audio_note.setText(e._audio);

        return view;
    }
}
