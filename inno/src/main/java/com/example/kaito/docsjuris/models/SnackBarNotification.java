package com.example.kaito.docsjuris.models;


public class SnackBarNotification {
    public static int COUNT;
    private String message;
    private int requestCode;

    public SnackBarNotification(String message) {
        this.message = message;
        this.requestCode = ++COUNT;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }
}
