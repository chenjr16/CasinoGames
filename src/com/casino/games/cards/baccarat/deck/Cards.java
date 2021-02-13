package com.casino.games.cards.baccarat.deck;

import java.util.ArrayList;
import java.util.List;

public enum Cards {;
    private static final List<Card> DECK = new ArrayList<>();

    public static List<Card> getDeck() {
        return new ArrayList<>(DECK);
    }

    static {
        for(Suit suit : Suit.values()) {
            for(Rank rank : Rank.values()) {
                DECK.add(new Card(rank, suit));
            }
        }
    }
}