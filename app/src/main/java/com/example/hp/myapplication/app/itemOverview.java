package com.example.hp.myapplication.app;

import android.util.Log;

public class itemOverview {

    private String text;
    private String head;

    public itemOverview(String text, String head) {
        Log.d("IN CONSTRUCTOR ", text);
        this.text = text;
        this.head = head;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
