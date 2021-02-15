package com.casino.games.cards.baccarat;

import com.casino.games.cards.baccarat.deck.Rank;
import com.casino.games.cards.baccarat.deck.Suit;
import org.junit.Before;
import com.casino.games.cards.baccarat.deck.Card;
import com.casino.games.cards.baccarat.deck.Cards;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class BaccaratDealerTest {

    Map<String, BaccaratDealer.Result<?>> resultMap;
    List<Card> deckOfCards;
    BaccaratDealer baccaratDealer;
    Rank rankDeuce;
    Suit suitDiamonds;
    Rank rankThree;

    @Before
    public void setUp() {
        resultMap = createResultMap();
        deckOfCards = new ArrayList<>();
        baccaratDealer = new BaccaratDealer();
        rankDeuce = Rank.DEUCE;
        rankThree = Rank.THREE;
        suitDiamonds = Suit.DIAMONDS;
    }
    private Map<String, BaccaratDealer.Result<?>> createResultMap() {
        resultMap = new HashMap<>();
        resultMap.put("winner", new BaccaratDealer.Result<>(0));
        resultMap.put("playerTotal", new BaccaratDealer.Result<>(0));
        resultMap.put("playerRound1", new BaccaratDealer.Result<>(0));
        resultMap.put("bankerTotal", new BaccaratDealer.Result<>(0));
        resultMap.put("bankerRound1", new BaccaratDealer.Result<>(0));
        resultMap.put("isPair", new BaccaratDealer.Result<>(false));
        return resultMap;
    }


    @Test
    public void testDrawTwoFor_shouldInsertCardTotalsInResultMap() {
        int total = rankDeuce.getValue() + rankThree.getValue();
        deckOfCards.add(Cards.getCard(rankDeuce, suitDiamonds));
        deckOfCards.add(Cards.getCard(rankThree, suitDiamonds));
        baccaratDealer.replaceDeckOfCards(deckOfCards);
        baccaratDealer.drawTwoFor(resultMap, Baccarat.Play.BANKER);

        assertEquals(total, resultMap.get("bankerTotal").getResult());
    }

    @Test
    public void testPlayerDrawIfEligible_shouldNotUpdateTotal_whenPlayerIsFrozen() {
        BaccaratDealer.Result<Integer> twoResult = new BaccaratDealer.Result<>(2);
        resultMap.put("playerTotal", twoResult);
        baccaratDealer.setPlayerFrozen();
        baccaratDealer.playerDrawIfEligible(resultMap);

        assertSame(twoResult, resultMap.get("playerTotal"));
    }

    @Test
    public void testPlayerDrawIfEligible_shouldInsertThirdCard_whenPlayerIsEligibleWithTotalOf5() {
        Rank rankFour = Rank.FOUR;
        deckOfCards.add(Cards.getCard(rankFour, suitDiamonds));
        baccaratDealer.replaceDeckOfCards(deckOfCards);
        resultMap.put("playerTotal", new BaccaratDealer.Result<>(5));
        baccaratDealer.playerDrawIfEligible(resultMap);

        assertEquals(rankFour.getValue(), resultMap.get("playerThirdCard").getResult());
    }

    @Test
    public void testPlayerDrawIfEligible_shouldNotInsertThirdCard_whenPlayerIsInEligibleWithTotalGreaterThan5() {
        resultMap.put("playerTotal", new BaccaratDealer.Result<>(7));
        baccaratDealer.playerDrawIfEligible(resultMap);

        assertFalse(resultMap.containsKey("playerThirdCard"));
    }

    @Test
    public void testBankerDrawIfEligible_shouldNotInsertThirdCard_whenBankerTotalIsTooHighAt7() {
        resultMap.put("bankerTotal", new BaccaratDealer.Result<>(7));
        baccaratDealer.bankerDrawIfEligible(resultMap);

        assertFalse(resultMap.containsKey("bankerThirdCard"));
    }

    @Test
    public void testBankerDrawIfEligible_shouldNotInsertThirdCard_whenBankerIsFrozen() {
        baccaratDealer.setBankerFrozen();
        baccaratDealer.bankerDrawIfEligible(resultMap);

        assertFalse(resultMap.containsKey("bankerThirdCard"));
    }

    @Test
    public void testBankerDrawIfEligible_shouldInsertThirdCard_whenBankerHas3AndPlayerThirdCardIs1() {
        resultMap.put("bankerTotal", new BaccaratDealer.Result<>(3));
        Rank rankOne = Rank.ACE;
        resultMap.put("playerThirdCard", new BaccaratDealer.Result<>(rankOne.getValue()));

        baccaratDealer.bankerDrawIfEligible(resultMap);

        assertTrue(resultMap.containsKey("bankerThirdCard"));
    }

    @Test
    public void testBankerDrawIfEligible_shouldNotUpdateTotal_whenBankerHas3AndPlayerThirdCardIs8() {
        BaccaratDealer.Result<Integer> threeResult = new BaccaratDealer.Result<>(3);
        resultMap.put("bankerTotal", threeResult);
        resultMap.put("playerThirdCard", new BaccaratDealer.Result<>(8));
        baccaratDealer.bankerDrawIfEligible(resultMap);

        assertSame(threeResult, resultMap.get("bankerTotal"));
    }

    @Test
    public void testBankerDrawIfEligible_shouldUpdateTotal_whenBankerHas6AndPlayerThirdCardIs7() {
        BaccaratDealer.Result<Integer> sixResult = new BaccaratDealer.Result<>(6);
        resultMap.put("bankerTotal", sixResult);
        resultMap.put("playerThirdCard", new BaccaratDealer.Result<>(7));
        baccaratDealer.bankerDrawIfEligible(resultMap);

        assertNotSame(sixResult, resultMap.get("bankerTotal"));
    }

    @Test
    public void testBankerDrawIfEligible_shouldNotUpdateTotal_whenBankerHas6AndPlayerThirdCardIs1() {
        BaccaratDealer.Result<Integer> sixResult = new BaccaratDealer.Result<>(6);
        resultMap.put("bankerTotal", sixResult);
        resultMap.put("playerThirdCard", new BaccaratDealer.Result<>(1));
        baccaratDealer.bankerDrawIfEligible(resultMap);

        assertSame(sixResult, resultMap.get("bankerTotal"));
    }

    @Test
    public void testDetermineWinner_shouldMakeBankerTheWinner_whenBankerHasHigherTotal() {
        Baccarat.Play winner = Baccarat.Play.BANKER;
        resultMap.put("playerTotal", new BaccaratDealer.Result<>(7));
        resultMap.put("bankerTotal", new BaccaratDealer.Result<>(8));
        baccaratDealer.determineWinner(resultMap);

        assertEquals(winner, resultMap.get("winner").getResult());
    }

    @Test
    public void testDetermineWinner_shouldMakeTieTheWinner_whenBankerAndPlayerHaveSameTotal() {
        Baccarat.Play winner = Baccarat.Play.TIE;
        resultMap.put("playerTotal", new BaccaratDealer.Result<>(8));
        resultMap.put("bankerTotal", new BaccaratDealer.Result<>(8));
        baccaratDealer.determineWinner(resultMap);

        assertEquals(winner, resultMap.get("winner").getResult());
    }

    @Test
    public void testDetermineWinner_shouldMakePlayerTheWinner_whenPlayerHasHigherTotal() {
        Baccarat.Play winner = Baccarat.Play.PLAYER;
        resultMap.put("playerTotal", new BaccaratDealer.Result<>(8));
        resultMap.put("bankerTotal", new BaccaratDealer.Result<>(7));
        baccaratDealer.determineWinner(resultMap);

        assertEquals(winner, resultMap.get("winner").getResult());
    }

    @Test
    public void testSetFrozen_shouldSetIsPlayerFrozenToTrue_whenTotalIs8() {
        baccaratDealer.setFrozen(Baccarat.Play.PLAYER, 8);
        assertTrue(baccaratDealer.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldNotSetIsPlayerFrozenToTrue_whenTotalIs7() {
        baccaratDealer.setFrozen(Baccarat.Play.PLAYER, 7);
        assertFalse(baccaratDealer.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsPlayerFrozenToTrue_whenTotalIs9() {
        baccaratDealer.setFrozen(Baccarat.Play.PLAYER, 9);
        assertTrue(baccaratDealer.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsBankerFrozenToTrue_whenTotalIs8() {
        baccaratDealer.setFrozen(Baccarat.Play.BANKER, 8);
        assertTrue(baccaratDealer.isBankerFrozen());
    }

    @Test
    public void testSetFrozen_shouldNotSetIsBankerFrozenToTrue_whenTotalIs7() {
        baccaratDealer.setFrozen(Baccarat.Play.BANKER, 7);
        assertFalse(baccaratDealer.isBankerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsBankerFrozenToTrue_whenTotalIs9() {
        baccaratDealer.setFrozen(Baccarat.Play.BANKER, 9);
        assertTrue(baccaratDealer.isBankerFrozen());
    }
}