package com.casino.games.cards.baccarat;

import com.casino.games.cards.baccarat.deck.Rank;
import com.casino.games.cards.baccarat.deck.Suit;
import org.junit.Before;
import com.casino.games.cards.baccarat.deck.Card;
import com.casino.games.cards.baccarat.deck.Cards;
import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.*;
import com.casino.games.cards.baccarat.Baccarat.ResultKeys;

import static org.junit.Assert.*;

public class BaccaratDealerTest {

    Map<ResultKeys, BaccaratDealer.Result<?>> resultMap;
    List<Card> deckOfCards;
    BaccaratDealer mockBaccaratDealer;
    Rank rankDeuce;
    Suit suitDiamonds;
    Rank rankThree;

    @Before
    public void setUp() {
        resultMap = createResultMap();
        deckOfCards = new ArrayList<>();
        mockBaccaratDealer = spy(BaccaratDealer.class);
        rankDeuce = Rank.DEUCE;
        rankThree = Rank.THREE;
        suitDiamonds = Suit.DIAMONDS;
        doNothing().when(mockBaccaratDealer).sleepBetweenDraw(anyInt());
    }
    private Map<ResultKeys, BaccaratDealer.Result<?>> createResultMap() {
        resultMap = new HashMap<>();
        resultMap.put(WINNER, new BaccaratDealer.Result<>(0));
        resultMap.put(PLAYER_TOTAL, new BaccaratDealer.Result<>(0));
        resultMap.put(PLAYER_ROUND1, new BaccaratDealer.Result<>(0));
        resultMap.put(BANKER_TOTAL, new BaccaratDealer.Result<>(0));
        resultMap.put(BANKER_ROUND1, new BaccaratDealer.Result<>(0));
        resultMap.put(IS_PAIR, new BaccaratDealer.Result<>(false));
        return resultMap;
    }


    @Test
    public void testDrawTwoFor_shouldInsertCardTotalsInResultMap() {
        int total = rankDeuce.getValue() + rankThree.getValue();
        deckOfCards.add(Cards.getCard(rankDeuce, suitDiamonds));
        deckOfCards.add(Cards.getCard(rankThree, suitDiamonds));
        mockBaccaratDealer.replaceDeckOfCards(deckOfCards);
        mockBaccaratDealer.drawTwoFor(resultMap, Baccarat.Play.BANKER);

        assertEquals(total, resultMap.get(BANKER_TOTAL).getResult());
    }

    @Test
    public void testPlayerDrawIfEligible_shouldNotUpdateTotal_whenPlayerIsFrozen() {
        BaccaratDealer.Result<Integer> twoResult = new BaccaratDealer.Result<>(2);
        resultMap.put(PLAYER_TOTAL, twoResult);
        mockBaccaratDealer.setPlayerFrozen();
        mockBaccaratDealer.playerDrawIfEligible(resultMap);

        assertSame(twoResult, resultMap.get(PLAYER_TOTAL));
    }

    @Test
    public void testPlayerDrawIfEligible_shouldInsertThirdCard_whenPlayerIsEligibleWithTotalOf5() {
        Rank rankFour = Rank.FOUR;
        deckOfCards.add(Cards.getCard(rankFour, suitDiamonds));
        mockBaccaratDealer.replaceDeckOfCards(deckOfCards);
        resultMap.put(PLAYER_TOTAL, new BaccaratDealer.Result<>(5));
        mockBaccaratDealer.playerDrawIfEligible(resultMap);

        assertEquals(rankFour.getValue(), resultMap.get(PLAYER_THIRD_CARD).getResult());
    }

    @Test
    public void testPlayerDrawIfEligible_shouldNotInsertThirdCard_whenPlayerIsInEligibleWithTotalGreaterThan5() {
        resultMap.put(PLAYER_TOTAL, new BaccaratDealer.Result<>(7));
        mockBaccaratDealer.playerDrawIfEligible(resultMap);

        assertFalse(resultMap.containsKey(PLAYER_THIRD_CARD));
    }

    @Test
    public void testBankerDrawIfEligible_shouldNotInsertThirdCard_whenBankerTotalIsTooHighAt7() {
        resultMap.put(BANKER_TOTAL, new BaccaratDealer.Result<>(7));
        mockBaccaratDealer.bankerDrawIfEligible(resultMap);

        assertFalse(resultMap.containsKey(BANKER_THIRD_CARD));
    }

    @Test
    public void testBankerDrawIfEligible_shouldNotInsertThirdCard_whenBankerIsFrozen() {
        mockBaccaratDealer.setBankerFrozen();
        mockBaccaratDealer.bankerDrawIfEligible(resultMap);

        assertFalse(resultMap.containsKey(BANKER_THIRD_CARD));
    }

    @Test
    public void testBankerDrawIfEligible_shouldInsertThirdCard_whenBankerHas3AndPlayerThirdCardIs1() {
        resultMap.put(BANKER_TOTAL, new BaccaratDealer.Result<>(3));
        Rank rankOne = Rank.ACE;
        resultMap.put(PLAYER_THIRD_CARD, new BaccaratDealer.Result<>(rankOne.getValue()));

        mockBaccaratDealer.bankerDrawIfEligible(resultMap);

        assertTrue(resultMap.containsKey(BANKER_THIRD_CARD));
    }

    @Test
    public void testBankerDrawIfEligible_shouldNotUpdateTotal_whenBankerHas3AndPlayerThirdCardIs8() {
        BaccaratDealer.Result<Integer> threeResult = new BaccaratDealer.Result<>(3);
        resultMap.put(BANKER_TOTAL, threeResult);
        resultMap.put(PLAYER_THIRD_CARD, new BaccaratDealer.Result<>(8));
        mockBaccaratDealer.bankerDrawIfEligible(resultMap);

        assertSame(threeResult, resultMap.get(BANKER_TOTAL));
    }

    @Test
    public void testBankerDrawIfEligible_shouldUpdateTotal_whenBankerHas6AndPlayerThirdCardIs7() {
        BaccaratDealer.Result<Integer> sixResult = new BaccaratDealer.Result<>(6);
        resultMap.put(BANKER_TOTAL, sixResult);
        resultMap.put(PLAYER_THIRD_CARD, new BaccaratDealer.Result<>(7));
        mockBaccaratDealer.bankerDrawIfEligible(resultMap);

        assertNotSame(sixResult, resultMap.get(BANKER_TOTAL));
    }

    @Test
    public void testBankerDrawIfEligible_shouldNotUpdateTotal_whenBankerHas6AndPlayerThirdCardIs1() {
        BaccaratDealer.Result<Integer> sixResult = new BaccaratDealer.Result<>(6);
        resultMap.put(BANKER_TOTAL, sixResult);
        resultMap.put(PLAYER_THIRD_CARD, new BaccaratDealer.Result<>(1));
        mockBaccaratDealer.bankerDrawIfEligible(resultMap);

        assertSame(sixResult, resultMap.get(BANKER_TOTAL));
    }

    @Test
    public void testDetermineWinner_shouldMakeBankerTheWinner_whenBankerHasHigherTotal() {
        Baccarat.Play winner = Baccarat.Play.BANKER;
        resultMap.put(PLAYER_TOTAL, new BaccaratDealer.Result<>(7));
        resultMap.put(BANKER_TOTAL, new BaccaratDealer.Result<>(8));
        mockBaccaratDealer.determineWinner(resultMap);

        assertEquals(winner, resultMap.get(WINNER).getResult());
    }

    @Test
    public void testDetermineWinner_shouldMakeTieTheWinner_whenBankerAndPlayerHaveSameTotal() {
        Baccarat.Play winner = Baccarat.Play.TIE;
        resultMap.put(PLAYER_TOTAL, new BaccaratDealer.Result<>(8));
        resultMap.put(BANKER_TOTAL, new BaccaratDealer.Result<>(8));
        mockBaccaratDealer.determineWinner(resultMap);

        assertEquals(winner, resultMap.get(WINNER).getResult());
    }

    @Test
    public void testDetermineWinner_shouldMakePlayerTheWinner_whenPlayerHasHigherTotal() {
        Baccarat.Play winner = Baccarat.Play.PLAYER;
        resultMap.put(PLAYER_TOTAL, new BaccaratDealer.Result<>(8));
        resultMap.put(BANKER_TOTAL, new BaccaratDealer.Result<>(7));
        mockBaccaratDealer.determineWinner(resultMap);

        assertEquals(winner, resultMap.get(WINNER).getResult());
    }

    @Test
    public void testSetFrozen_shouldSetIsPlayerFrozenToTrue_whenTotalIs8() {
        mockBaccaratDealer.setFrozen(Baccarat.Play.PLAYER, 8);
        assertTrue(mockBaccaratDealer.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldNotSetIsPlayerFrozenToTrue_whenTotalIs7() {
        mockBaccaratDealer.setFrozen(Baccarat.Play.PLAYER, 7);
        assertFalse(mockBaccaratDealer.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsPlayerFrozenToTrue_whenTotalIs9() {
        mockBaccaratDealer.setFrozen(Baccarat.Play.PLAYER, 9);
        assertTrue(mockBaccaratDealer.isPlayerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsBankerFrozenToTrue_whenTotalIs8() {
        mockBaccaratDealer.setFrozen(Baccarat.Play.BANKER, 8);
        assertTrue(mockBaccaratDealer.isBankerFrozen());
    }

    @Test
    public void testSetFrozen_shouldNotSetIsBankerFrozenToTrue_whenTotalIs7() {
        mockBaccaratDealer.setFrozen(Baccarat.Play.BANKER, 7);
        assertFalse(mockBaccaratDealer.isBankerFrozen());
    }

    @Test
    public void testSetFrozen_shouldSetIsBankerFrozenToTrue_whenTotalIs9() {
        mockBaccaratDealer.setFrozen(Baccarat.Play.BANKER, 9);
        assertTrue(mockBaccaratDealer.isBankerFrozen());
    }
}