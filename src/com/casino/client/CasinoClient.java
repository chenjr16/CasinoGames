package com.casino.client;


import com.casino.games.Casino;
//import com.casino.games.board.roulette.Roulette;


class CasinoClient {
    public static void main(String[] args) {

        //Test code for slotMachine
        //Test code for Roulette

//        CasinoGames r1 = new Roulette();
//        Player playerR = new Player("Marco", 10_000);
//        Dealer dealerR = new Dealer("Mr. Dealer", 100_000);
//        r1.play(playerR, dealerR, 50);

        // Test code for Casino

        Casino casino = new Casino();
        casino.start();

        //CasinoGames r1 = new Roulette();
        //Player playerR = new Player("Marco", 10_000);

        //Dealer dealer1 = new Dealer("D1");
        //r1.play(playerR,dealer1,10);
        //Dealer dealerR = new Dealer("Mr. Dealer");
        //r1.play(playerR, dealerR, 50);
    }
}