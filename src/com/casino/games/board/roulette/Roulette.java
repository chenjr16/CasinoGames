package com.casino.games.board.roulette;

import com.casino.games.CasinoGames;
import com.casino.player.Dealer;
import com.casino.player.Player;

public class Roulette extends CasinoGames {
    int randomNumber = (int)(Math.random() * 36);

    @Override
    public boolean isPlayable(Player player, double bet) {
        return false;
    }

    @Override
    public void play(Player player, Dealer dealer, double bet) {

    }

    @Override
    public void distributeMoney() {

    }

    @Override
    public void endGame() {

    }

    //BUSINESS METHODS
    public void welcomeScreen() {
        System.out.println("                 \t\tMarco Bragado");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("|                                                         |");
        System.out.println("|             Welcome to Roulette table!                  |");
        System.out.println("|             You have $XYZ in starting chips.            |");
        System.out.println("|                  Good Luck and Have Fun!                |");
        System.out.println("|                                                         |");
        System.out.println("|                                                         |");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%&&&&&&&\n");
        table.displayRouletteTable();
        System.out.print(" Press ANY KEY and choose which bet you would like to place!\n");
        scan.nextLine();
        initializeGame();
    }
}