package com.casino.games;

import com.casino.games.board.roulette.Roulette;
import com.casino.player.Dealer;
import com.casino.games.cards.baccarat.Baccarat;
import com.casino.games.machines.slot.SlotMachine;
import com.casino.player.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class CasinoGames implements GameInterface {

    public abstract void play(Player player, double bet, Dealer dealer);

    /*
     * This is how we consume each game's Playable object.
     * We call their isPlayable contract method, passing in the player and bet,
     * and get back a Playable, which is then added to the list.
     */
    static List<Playable> games(Player player, double bet) {
        List<Playable> playableGames = new ArrayList<>();
        playableGames.add(new Baccarat().isPlayable(player, bet));
        playableGames.add(new SlotMachine().isPlayable(player, bet));
        playableGames.add(new Roulette().isPlayable(player, bet));
        return playableGames;
    }
}