package com.davebyrne.ironsight.activity;

/**
 * Created by Dave on 16/12/2016.
 */

public class Game {

    private String title, genre, date;

    public Game() {
    }

    public Game(String title, String genre, String date) {
        this.genre = genre;
        this.date = date;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
