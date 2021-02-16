package com.casino.games;

import com.casino.employees.Dealer;
import com.casino.games.cards.baccarat.Baccarat;
import com.casino.player.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CasinoGames implements GameInterface {
    //General Methods
    public void playAgain(){

    }

    public abstract void play(Player player, double bet, Dealer dealer, Casino.CasinoPrompter prompter);

    public static List<Playable> games(Player player, double bet, Casino.CasinoPrompter prompter) {
        List<Playable> playableGames = new ArrayList<>();
        // add each game's Playable to the list by calling their isPlayable method.
        // then return the list to the casino.
        playableGames.add(new Baccarat().isPlayable(player, bet, prompter));
        return playableGames;
    }

}