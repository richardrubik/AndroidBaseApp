package com.example.notesrecorder2;

public class RecordsListElement {
    String _id;
    String _txt;
    String _audio;

    RecordsListElement(String id, String text, String audio) {
        this._id = id;
        this._txt = text;
        this._audio = audio;
    }
}
