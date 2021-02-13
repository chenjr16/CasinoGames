package com.casino.games.board.roulette;

import com.casino.games.GameInterface;
import com.casino.player.Dealer;
import com.casino.player.Player;

class Roulette implements GameInterface {
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
}