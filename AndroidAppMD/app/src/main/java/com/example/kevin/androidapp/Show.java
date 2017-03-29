package com.example.kevin.androidapp;

/**
 * Created by Kevin on 25-3-2017.
 */
public class Show {
    private long id;
    private String show;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getShow() {
        return show;
    }

    public void setShows(String show) {
        this.show = show;
    }


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return show;
    }
}
