package com.example.notesrecorder2;

public class RecordsListElement {
    private String _id;
    private String _txt;
    private String _audio;

    RecordsListElement(String id, String text, String audio) {
        this._id = id;
        this._txt = text;
        this._audio = audio;
    }

    public String get_audio() {
        return _audio;
    }

    public String get_id() {
        return _id;
    }

    public String get_txt() {
        return _txt;
    }

    public void set_audio(String _audio) {
        this._audio = _audio;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public void set_txt(String _txt) {
        this._txt = _txt;
    }
}
