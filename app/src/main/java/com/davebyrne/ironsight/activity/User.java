package com.davebyrne.ironsight.activity;

import java.util.List;

/**
 * Created by Dave on 24/04/2017.
 */

public class User {
    String email;
    String name;
    List<Game> games;

    public User() {
    }

    public User(String email, String name, List<Game> games) {
        this.email = email;
        this.games = games;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
