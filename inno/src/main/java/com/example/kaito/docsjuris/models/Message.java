package com.example.kaito.docsjuris.models;

import android.media.MediaPlayer;

import com.example.kaito.docsjuris.R;

public class Message {
    private int id;
    private boolean meSender;
    private String content;
    private String date;
    private int drawable;
    private int reference;
    private int sound;

    public Message(int id, boolean meSender, String content, String date, int reference) {
        this.id = id;
        this.meSender = meSender;
        this.content = content;
        this.date = date;
        this.reference = reference;
        this.sound = 0;
    }

    public Message(int id, boolean meSender, String content, String date, int reference, int drawable) {
        this(id, meSender, content, date, reference);
        this.drawable = drawable;
        this.sound = 0;
    }

    public int getSound() {
        return sound;
    }

    public void setSound(int sound) {
        this.sound = sound;
    }

    public int getReference() {
        return reference;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public float getScal() {
        return this.drawable == 0 ? 1f : .7f;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMeSender() {
        return meSender;
    }

    public void setMeSender(boolean meSender) {
        this.meSender = meSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
