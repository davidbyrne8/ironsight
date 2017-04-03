package com.davebyrne.ironsight.activity;

/**
 * Created by Dave on 16/12/2016.
 */

public class Game {

    String gameId;
    String gameName;
    String gameGenre;

    public Game(){

    }

    public Game(String gameId, String gameName, String gameGenre) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameGenre = gameGenre;
    }

    public String getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGameGenre() {
        return gameGenre;
    }
}
