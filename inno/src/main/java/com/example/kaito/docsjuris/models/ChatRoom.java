package com.example.kaito.docsjuris.models;

import java.util.Calendar;

public class ChatRoom {
    private int id;
    private String lastSeen;
    private int totalUnseen;
    private String avatar;
    private String preview;
    private String name;

    public ChatRoom(int id, String lastSeen, int totalUnseen, String avatar, String preview, String name) {
        this.id = id;
        this.lastSeen = lastSeen;
        this.totalUnseen = totalUnseen;
        this.avatar = avatar;
        this.preview = preview;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getTotalUnseen() {
        return totalUnseen;
    }

    public void setTotalUnseen(int totalUnseen) {
        this.totalUnseen = totalUnseen;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
