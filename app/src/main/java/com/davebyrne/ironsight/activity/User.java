package com.davebyrne.ironsight.activity;

/**
 * Created by Dave on 24/04/2017.
 */

public class User {
    String email;
    Game[] games;

    public User() {
    }

    public User(String email, Game[] games) {
        this.email = email;
        this.games = games;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Game[] getGames() {
        return games;
    }

    public void setGames(Game[] games) {
        this.games = games;
    }
}
