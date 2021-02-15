package com.casino.games;

import com.apps.util.Prompter;
import com.casino.employees.Dealer;
import com.casino.player.Player;

public class CasinoMainScreenFactory {
    static void getBackToMainScreen(Prompter prompter, Player player, Dealer dealer) {
        Casino casino = new Casino();
        casino.mainScreen(prompter, player, dealer);
    }
}