package com.casino.games.cards.baccarat;

import com.casino.games.CasinoGames;

import com.casino.games.cards.baccarat.deck.Card;
import com.casino.games.cards.baccarat.deck.Cards;
import com.casino.player.Dealer;
import com.casino.player.Player;

import java.util.List;

class Baccarat extends CasinoGames {
    private Player player;
    private Dealer dealer;
    private double bet;
    private final List<Card> deckOfCards = Cards.getDeck();

    // Getters and Setters
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

    public List<Card> getDeckOfCards() {
        return deckOfCards;
    }

    // GameInterface overrides

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