package com.casino.games;

public class Playable {
    private final String name;
    private final String message;
    private final boolean isPlayable;
    private final CasinoGames instance;

    public Playable(String name, String message, boolean result, CasinoGames instance) {
        this.name = name;
        this.message = message;
        this.isPlayable = result;
        this.instance = instance;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public CasinoGames getInstance() {
        return instance;
    }

}