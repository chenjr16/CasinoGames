package com.casino.games;

public class Playable {
    private final String name;
    private final String message;
    private final boolean playableResult;
    private final CasinoGames instance;

    public Playable(String name, String message, boolean result, CasinoGames instance) {
        this.name = name;
        this.message = message;
        this.playableResult = result;
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public boolean playableResult() {
        return playableResult;
    }

    public CasinoGames getInstance() {
        return instance;
    }
}