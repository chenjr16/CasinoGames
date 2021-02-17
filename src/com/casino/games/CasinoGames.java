package com.casino.games;

import com.casino.games.board.roulette.Roulette;
import com.casino.player.Dealer;
import com.casino.games.cards.baccarat.Baccarat;
import com.casino.games.machines.slot.SlotMachine;
import com.casino.player.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CasinoGames implements GameInterface {
    //General Methods
    public void playAgain() {

    }

    public abstract void play(Player player, double bet, Dealer dealer);

    public static List<Playable> games(Player player, double bet) {
        List<Playable> playableGames = new ArrayList<>();
        // add each game's Playable to the list by calling their isPlayable method.
        // then return the list to the casino.

        playableGames.add(new Baccarat().isPlayable(player, bet));
        playableGames.add(new SlotMachine().isPlayable(player, bet));
        playableGames.add(new Roulette().isPlayable(player, bet));

//        Playable baccarat = new Baccarat().isPlayable(player, bet);
//        Playable slotMachine = new SlotMachine().isPlayable(player, bet);
//        Playable roulette = new Roulette().isPlayable(player, bet);
//
//        if(baccarat.playableResult()){
//            playableGames.add(baccarat);
//        }
//        if(slotMachine.playableResult()){
//            playableGames.add(slotMachine);
//        }
//        if(roulette.playableResult()){
//            playableGames.add(roulette);
//        }

        return playableGames;
    }

}