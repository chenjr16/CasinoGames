package com.casino.games.cards.baccarat.deck;

public class Card {
    private final Rank rank;
    private final Suit suit;

    Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public int getRankValue() {
        return rank.getValue();
    }


    public Suit getSuit() {
        return suit;
    }

}