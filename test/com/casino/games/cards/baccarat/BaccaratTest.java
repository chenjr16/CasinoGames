package com.casino.games.cards.baccarat;

import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaccaratTest {

    @Test
    public void setUp() {
    }

    @Test
    public void testPlay_shouldSetupClassMembers() {
        Player player = new Player("Nick", 50_000.0);
        Dealer dealer = new Dealer("Ron", 100_000.0);
        Baccarat baccarat = new Baccarat();
        baccarat.play(player, dealer, 1000);

        assertSame(player, baccarat.getPlayer());
        assertSame(dealer, baccarat.getDealer());
        assertEquals(1000, baccarat.getBet(), .001);
    }
}