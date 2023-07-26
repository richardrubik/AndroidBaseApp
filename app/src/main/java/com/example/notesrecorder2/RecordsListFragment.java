package com.example.notesrecorder2;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private DatabaseManager dbManager;
    private ListView listView;
    private ListViewAdapter adapter;

    final String[] from = new String[] { DatabaseHelper._ID,
            DatabaseHelper.TEXT_NOTE, DatabaseHelper.AUDIO_NOTE };

    final int[] to = new int[] { R.id._id, R.id.textnote, R.id.audionote };

     public RecordsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordsListFragment newInstance(String param1, String param2) {
        RecordsListFragment fragment = new RecordsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_records_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dbManager = new DatabaseManager(this.getContext());
        dbManager.open();
        Cursor cursor = dbManager.fetch();

        LinkedList<RecordsListElement> linked_list = new LinkedList<RecordsListElement>();

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String text = cursor.getString(1);
                String audio = cursor.getString(2);
                Log.i("List", text + ": " + audio);

                RecordsListElement recordListItem = new RecordsListElement(id, text, audio);
                linked_list.add(recordListItem);
            } while (cursor.moveToNext());
        }

        Context c = this.getContext();

        listView = (ListView) getView().findViewById(R.id.list_view);
        //listView.setEmptyView(getView().findViewById(R.id.empty));

        adapter = new ListViewAdapter(this.getContext(), linked_list);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // One click to perhaps play audio and display full text message in a pop-up
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("OnClick", "position:" + position + " id: " + id);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            // Long click to provide more options, such as to delete note, edit note, etc.
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("LongClick", "position:" + position + " id: " + id);

                PopupMenu popMenu = new PopupMenu(c, view);
                popMenu.getMenuInflater().inflate(R.menu.popup_menu, popMenu.getMenu());

                popMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.i("Popup", "item:" + item.getTitle());

                        if (item.getTitle() == getResources().getString(R.string.delete_note)) {
                            RecordsListElement rec = linked_list.get(position);

                            // delete item in database
                            dbManager.delete(Integer.parseInt(rec._id));

                            // delete the media file too
                            if (rec._audio != null && rec._audio.isEmpty() == false) {
                                ContentResolver cr = requireContext().getContentResolver();
                                cr.delete(Uri.parse(rec._audio), null, null);
                            }

                            // delete item in linked list
                            linked_list.remove(position);

                            adapter.notifyDataSetChanged();
                        }

                        return true;
                    }
                });

                popMenu.show();

                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        dbManager.close();
    }
}