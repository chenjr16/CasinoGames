package com.casino.games.cards.baccarat;

import com.casino.games.cards.baccarat.deck.Card;
import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class BaccaratTest {
    Player player;
    Dealer dealer;
    Baccarat baccarat;
    Map<String, Response<?>> responseMap;

    @Before
    public void setUp() {
        player = new Player("Nick", 50_000.0);
        dealer = new Dealer("Ron", 100_000.0);
        baccarat = new Baccarat();
        baccarat.play(player, dealer, 1000);
        responseMap = new HashMap<>();
    }

    @Test
    public void testPlay_shouldSetupClassMembers() {
        assertSame(player, baccarat.getPlayer());
        assertSame(dealer, baccarat.getDealer());
        assertEquals(1000, baccarat.getBet(), .001);
    }

    @Test
    public void testGetPlay_shouldReturnResponseMapWithUserInput() {
        Baccarat.Play play = Baccarat.Play.PLAYER;
        String playString = "PLAYER";

        InputStream in = new ByteArrayInputStream(playString.getBytes());
        System.setIn(in);

        assertEquals(play, baccarat.getPlay(responseMap).get("play").getResponse());
    }

    @Test
    public void testGetPlayBet_shouldReturnResponseMapWithUserInput() {
        double bet = 2500.0;
        String betString = "2500.0";

        InputStream in = new ByteArrayInputStream(betString.getBytes());
        System.setIn(in);

        Double data = (Double) baccarat.getPlayBet(responseMap).get("bet").getResponse();

        assertEquals(bet, data, .001);
    }

    @Test
    public void testGetSidePlay_shouldReturnResponseMapWithUserInput() {
        Baccarat.SidePlays sidePlay = Baccarat.SidePlays.THREE;
        String sidePlayString = "THREE";

        InputStream in = new ByteArrayInputStream(sidePlayString.getBytes());
        System.setIn(in);

        assertEquals(sidePlay, baccarat.getSidePlay(responseMap).get("sidePlay").getResponse());
    }

    @Test
    public void testGetSidePlayBet_shouldReturnResponseMapWithUserInput() {
        double sideBet = 2500.0;
        String sideBetString = "2500.0";

        InputStream in = new ByteArrayInputStream(sideBetString.getBytes());
        System.setIn(in);

        Double data = (Double) baccarat.getSidePlayBet(responseMap).get("sideBet").getResponse();

        assertEquals(sideBet, data, .001);
    }

    @Test
    public void testDrawTwoFor_shouldReturnResultMapWithRoundTotals_whenPlayer() {
        Map<String, Integer> resultMap = new HashMap<>();
        assertTrue(baccarat.drawTwoFor(resultMap, Baccarat.Play.PLAYER).containsKey("playerTotal"));
        assertTrue(baccarat.drawTwoFor(resultMap, Baccarat.Play.PLAYER).containsKey("playerRound1"));
    }

    @Test
    public void testDrawTwoFor_shouldReturnResultMapWithRoundTotals_whenBanker() {
        Map<String, Integer> resultMap = new HashMap<>();
        assertTrue(baccarat.drawTwoFor(resultMap, Baccarat.Play.BANKER).containsKey("bankerTotal"));
        assertTrue(baccarat.drawTwoFor(resultMap, Baccarat.Play.BANKER).containsKey("bankerRound1"));
    }

    @Test
    public void testSetFrozen_shouldSetIsPlayerFrozenToTrue_whenTotalIs8() {
        baccarat.setFrozen(Baccarat.Play.PLAYER, 8);
        assertTrue(baccarat.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldNotSetIsPlayerFrozenToTrue_whenTotalIs7() {
        baccarat.setFrozen(Baccarat.Play.PLAYER, 7);
        assertFalse(baccarat.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsPlayerFrozenToTrue_whenTotalIs9() {
        baccarat.setFrozen(Baccarat.Play.PLAYER, 9);
        assertTrue(baccarat.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsBankerFrozenToTrue_whenTotalIs8() {
        baccarat.setFrozen(Baccarat.Play.BANKER, 8);
        assertTrue(baccarat.isBankerFrozen());
    }

    @Test
    public void testSetFrozen_shouldNotSetIsBankerFrozenToTrue_whenTotalIs7() {
        baccarat.setFrozen(Baccarat.Play.BANKER, 7);
        assertFalse(baccarat.isBankerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsBankerFrozenToTrue_whenTotalIs9() {
        baccarat.setFrozen(Baccarat.Play.BANKER, 9);
        assertTrue(baccarat.isBankerFrozen());
    }
}