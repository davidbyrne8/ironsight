package com.davebyrne.ironsight.activity;

/**
 * Created by Dave on 16/12/2016.
 */

public class Game {

    String gameId;
    String gameName;
    String gameGenre;
    String gameDate;
    String gamePlat;



    public Game(){

    }

    public Game(String gameId, String gameName, String gameGenre, String gameDate, String gamePlat) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.gameGenre = gameGenre;
        this.gameDate = gameDate;
        this.gamePlat = gamePlat;
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

    public String getGameDate() {
        return gameDate;
    }

    public String getGamePlat() {
        return gamePlat;
    }
}
