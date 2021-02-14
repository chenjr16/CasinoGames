package com.casino.client;

import com.casino.games.CasinoGames;
import com.casino.player.Dealer;
import com.casino.player.Player;
import com.casino.games.machines.slot.*;

import java.util.Scanner;

class CasinoClient {
    public static void main(String[] args) {

        //Test code for slotMachine
        Player player1 = new Player("Junru", 5000.0);
        Dealer dealer1 = new Dealer("dealer", 100000.0);
        CasinoGames game1 = new SlotMachine();
        game1.isPlayable(player1, 50.0);
        game1.play(player1, dealer1, 50.0);
        game1.distributeMoney();
    }
}