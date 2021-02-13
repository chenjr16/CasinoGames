package com.casino.games.cards.baccarat;

import com.casino.games.CasinoGames;
import static com.casino.games.cards.baccarat.utils.Pipe.apply;
import com.casino.player.Dealer;
import com.casino.player.Player;

class Baccarat extends CasinoGames {
    Player player;
    Dealer dealer;
    double bet;

    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    private void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public double getBet() {
        return bet;
    }

    private void setBet(double bet) {
        this.bet = bet;
    }

    @Override
    public boolean isPlayable(Player player, double bet) {
        return false;
    }

    @Override
    public void play(Player player, Dealer dealer, double bet) {
        setPlayer(player);
        setDealer(dealer);
        setBet(bet);
    }

    @Override
    public void distributeMoney() {

    }

    @Override
    public void endGame() {

    }
}