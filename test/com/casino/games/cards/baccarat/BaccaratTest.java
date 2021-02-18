
package com.casino.games.cards.baccarat;

import com.casino.games.Casino;
import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

import java.util.Map;
import com.casino.games.cards.baccarat.ResponsePipeline.Response;
import com.casino.games.cards.baccarat.BaccaratDealer.Result;

import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.*;

import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.*;

import static org.junit.Assert.*;

public class BaccaratTest {
    Player player;
    Dealer dealer;
    Baccarat baccarat;
    Map<Baccarat.ResponseKeys, Response<?>> responseMap;
    Map<Baccarat.ResultKeys, BaccaratDealer.Result<?>> resultMap;
    double bet = 1000;
    Baccarat mockBaccarat;
    MockedStatic<Casino> mockStaticCasino;

    @Before
    public void setUp() {
        mockBaccarat = spy(Baccarat.class);
        doNothing().when(mockBaccarat).playBaccarat();
        mockStaticCasino = mockStatic(Casino.class);
        resultMap = mockBaccarat.getResultMap();
        responseMap = mockBaccarat.getResponseMap();
        player = new Player("Nick", 50_000.0);
        dealer = new Dealer("Ron", 50_000_000.0);
        mockBaccarat.play(player, bet, dealer);
    }

    @After
    public void close() {
        mockStaticCasino.close();
    }


    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByTwoTimesTheBet_whenPlayIsCorrectAndIsBanker() {
        double newExpectedTotal = 50_000.0 + (bet * 2);
        responseMap.put(PLAY, new Response<>(Baccarat.Play.BANKER));
        resultMap.put(WINNER, new Result<>(Baccarat.Play.BANKER));
        mockBaccarat.dishOutWinnings(responseMap, resultMap);

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }

    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByTwoTimesTheBet_whenPlayIsCorrectAndIsPlayer() {
        double newExpectedTotal = 50_000.0 + (bet * 2);
        responseMap.put(PLAY, new Response<>(Baccarat.Play.PLAYER));
        resultMap.put(WINNER, new Result<>(Baccarat.Play.PLAYER));

        mockBaccarat.dishOutWinnings(responseMap, resultMap);

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }
    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByNineTimesTheBet_whenPlayIsCorrectAndIsTie() {
        double newExpectedTotal = 50_000.0 + (bet * 9);
        responseMap.put(PLAY, new Response<>(Baccarat.Play.TIE));
        resultMap.put(WINNER, new Result<>(Baccarat.Play.TIE));

        mockBaccarat.dishOutWinnings(responseMap, resultMap);

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }

    @Test
    public void dishOutWinnings_shouldNotAffectPlayerBalance_whenPlayIsWrong() {
        double sameTotal = 50_000.0;
        responseMap.put(PLAY, new Response<>(Baccarat.Play.BANKER));
        resultMap.put(WINNER, new Result<>(Baccarat.Play.PLAYER));

        mockBaccarat.dishOutWinnings(responseMap, resultMap);

        assertEquals(sameTotal, player.getBalance(), .001);
    }

    @Test
    public void testSetWinnings_shouldIncreasePlayerBalanceByMultiplierAmountTimesBetAmount() {
        double expectedTotal = 50_000.0 + 2000;
        mockBaccarat.setWinnings(2000);
        assertEquals(expectedTotal, player.getBalance(), .001);
    }
}


