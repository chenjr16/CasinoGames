package com.casino.games;

public abstract class CasinoGames implements GameInterface {

    //General Methods
    public void playAgain(){

    }

    public void switchGame(){
        endGame();
    }

    public void exitCasino(){
        endGame();
    }


}