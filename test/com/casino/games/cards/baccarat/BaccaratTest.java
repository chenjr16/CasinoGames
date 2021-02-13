package com.casino.games.cards.baccarat;

import com.casino.games.cards.baccarat.deck.Card;
import com.casino.games.cards.baccarat.deck.Cards;
import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaccaratTest {
    Player player;
    Dealer dealer;
    Baccarat baccarat;

    @Before
    public void setUp() {
        player = new Player("Nick", 50_000.0);
        dealer = new Dealer("Ron", 100_000.0);
        baccarat = new Baccarat();
        baccarat.play(player, dealer, 1000);
    }

    @Test
    public void testPlay_shouldSetupClassMembers() {
        assertSame(player, baccarat.getPlayer());
        assertSame(dealer, baccarat.getDealer());
        assertEquals(1000, baccarat.getBet(), .001);
    }

    @Test
    public void clientTest() {
        for(Card card : baccarat.getDeckOfCards()) {
            System.out.println(card.getRankValue() + ": " + card.getSuit());
        }
    }
}