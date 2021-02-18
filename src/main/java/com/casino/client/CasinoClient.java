package com.casino.client;
import com.casino.games.Casino;

class CasinoClient {
    public static void main(String[] args) {
        Casino casino = new Casino();
        casino.start(casino);
    }
}