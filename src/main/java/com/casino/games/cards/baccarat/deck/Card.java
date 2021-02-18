package com.casino.games.cards.baccarat.deck;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return getRank().getValue() == card.getRank().getValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRank().getValue());
    }
}