package com.mobileproto.lab2;

/**
 * Created by chris on 9/17/13.
 */
public class Note {
    private long id;
    private String title;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String titletoString() {
        return this.title;
    }
    public String texttoString(){
        return this.text;
    }
}
