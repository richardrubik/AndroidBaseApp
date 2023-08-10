package com.example.notesrecorder2;

import android.content.Context;
import org.sqlite.database.sqlite.SQLiteDatabase;
import org.sqlite.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "NOTES";

    // Table columns
    public static final String _ID = "_id";
    public static final String TEXT_NOTE = "text_note";
    public static final String AUDIO_NOTE = "audio_note";
    public static final String DOC_ID = "doc_id";

    // Database info
    static final String DB_NAME = "notes.db";

    // Database version
    static final int DB_VERSION = 2;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + _ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TEXT_NOTE + " TEXT NOT NULL, " + AUDIO_NOTE + " TEXT, "
            + DOC_ID + " TEXT);";

    public DatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
