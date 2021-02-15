package com.casino.games;

import com.apps.util.Prompter;
import com.casino.employees.Dealer;
import com.casino.player.Player;

public abstract class CasinoGames implements GameInterface {

    //General Methods
    public void playAgain(){

    }

    public void switchGame(Prompter prompter, Player player, Dealer dealer){
        endGame();
        CasinoMainScreenFactory.getBackToMainScreen(prompter, player, dealer);
    }

    public void exitCasino(){
        endGame();
    }
}