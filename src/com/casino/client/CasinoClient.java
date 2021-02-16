package com.casino.client;

import com.casino.games.Casino;
import com.casino.games.CasinoGames;
//import com.casino.games.board.roulette.Roulette;
import com.casino.player.Player;

class CasinoClient {
    public static void main(String[] args) {

        //Test code for slotMachine
//        Player player1 = new Player("Junru", 5000.0);
//        Dealer dealer1 = new Dealer("dealer", 100000.0);
//        CasinoGames game1 = new SlotMachine();
//        game1.isPlayable(player1, 50.0);
//        game1.play(player1, dealer1, 50.0);
//        game1.distributeMoney();


        //Test code for Roulette
//        CasinoGames r1 = new Roulette();
//        Player playerR = new Player("Marco", 10_000);
//        Dealer dealerR = new Dealer("Mr. Dealer", 100_000);
//        r1.play(playerR, dealerR, 50);

        // Test code for Casino

        Casino casino = new Casino();
        casino.start();
    }
}