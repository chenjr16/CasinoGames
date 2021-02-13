package com.casino.games;

abstract class CasinoGames implements GameInterface {

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