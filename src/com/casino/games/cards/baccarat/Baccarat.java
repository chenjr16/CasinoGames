package com.casino.games.cards.baccarat;

import com.casino.games.CasinoGames;
import static com.casino.games.cards.baccarat.utils.Pipe.apply;
import com.casino.player.Dealer;
import com.casino.player.Player;

class Baccarat extends CasinoGames {
    Player player;
    Dealer dealer;
    double bet;

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