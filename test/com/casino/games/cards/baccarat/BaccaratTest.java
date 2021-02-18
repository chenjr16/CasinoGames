
package com.casino.games.cards.baccarat;

import com.casino.games.Casino;
import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;

import java.util.Map;
import com.casino.games.cards.baccarat.ResponsePipeline.Response;
import com.casino.games.cards.baccarat.BaccaratDealer.Result;

import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.*;

import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.*;

import static org.junit.Assert.*;

public class BaccaratTest {
    Map<Baccarat.ResponseKeys, Response<?>> responseMap;
    Map<Baccarat.ResultKeys, BaccaratDealer.Result<?>> resultMap;
    MockedStatic<Casino> mockStaticCasino;
    Baccarat mockBaccarat;
    Player player;
    Dealer dealer;

    @Before
    public void setUp() {
        mockBaccarat = spy(Baccarat.class);
        doNothing().when(mockBaccarat).playBaccarat();
        mockStaticCasino = mockStatic(Casino.class);
        resultMap = mockBaccarat.getResultMap();
        responseMap = mockBaccarat.getResponseMap();
        player = new Player("Nick", 50_000.0);
        dealer = new Dealer("Ron", 50_000_000.0);
        mockBaccarat.play(player, 1000, dealer);
    }

    @After
    public void close() {
        mockStaticCasino.close();
    }


    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByTwoTimesTheBet_whenPlayIsCorrectAndIsBanker() {
        double bet = 1000;
        double newExpectedTotal = 50_000.0 + (bet * 2);
        responseMap.put(BET, new Response<>(bet));
        responseMap.put(PLAY, new Response<>(Baccarat.Play.BANKER));
        resultMap.put(WINNER, new Result<>(Baccarat.Play.BANKER));

        mockBaccarat.dishOutWinnings(responseMap, resultMap);

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }

    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByTwoTimesTheBet_whenPlayIsCorrectAndIsPlayer() {
        double bet = 1000;
        double newExpectedTotal = 50_000.0 + (bet * 2);
        responseMap.put(BET, new Response<>(bet));
        responseMap.put(PLAY, new Response<>(Baccarat.Play.PLAYER));
        resultMap.put(WINNER, new Result<>(Baccarat.Play.PLAYER));

        mockBaccarat.dishOutWinnings(responseMap, resultMap);

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }
    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByNineTimesTheBet_whenPlayIsCorrectAndIsTie() {
        double bet = 1000;
        double newExpectedTotal = 50_000.0 + (bet * 9);
        responseMap.put(BET, new Response<>(bet));
        responseMap.put(PLAY, new Response<>(Baccarat.Play.TIE));
        resultMap.put(WINNER, new Result<>(Baccarat.Play.TIE));

        mockBaccarat.dishOutWinnings(responseMap, resultMap);

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }

    @Test
    public void dishOutWinnings_shouldTakeAwayBetFromPlayerBalance_whenPlayIsWrong() {
        double bet = 2000.0;
        double originalBalance = (double) responseMap.get(PLAYER_BALANCE).getResponse();
        responseMap.put(BET, new Response<>(bet));
        responseMap.put(PLAY, new Response<>(Baccarat.Play.BANKER));
        resultMap.put(WINNER, new Result<>(Baccarat.Play.PLAYER));

        mockBaccarat.dishOutWinnings(responseMap, resultMap);

        assertEquals(originalBalance - bet, player.getBalance(), .001);
    }

    @Test
    public void testSetWinnings_shouldIncreasePlayerBalanceByMultiplierAmountTimesBetAmount() {
        double expectedTotal = 50_000.0 + 2000;

        mockBaccarat.moneyTransaction(2000, true);

        assertEquals(expectedTotal, player.getBalance(), .001);
    }
}


