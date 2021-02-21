
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
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.WinKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.Play.*;
import static com.casino.games.cards.baccarat.Baccarat.SidePlay.*;
import static org.junit.Assert.*;

public class BaccaratTest {
    Map<Baccarat.ResponseKeys, Response<?>> responseMap;
    Map<Baccarat.ResultKeys, BaccaratDealer.Result<?>> resultMap;
    Map<Baccarat.WinKeys, Boolean> winMap;
    MockedStatic<Casino> mockStaticCasino;
    Baccarat mockBaccarat;
    Player player;
    Dealer dealer;
    Database database;

    @Before
    public void setUp() {
        mockBaccarat = spy(Baccarat.class);
        doNothing().when(mockBaccarat).playBaccarat();
        mockStaticCasino = mockStatic(Casino.class);
        resultMap = mockBaccarat.getResultMap();
        responseMap = mockBaccarat.getResponseMap();
        winMap = mockBaccarat.getWinMap();
        player = new Player("Nick", 50_000.0);
        dealer = new Dealer("Ron", 50_000_000.0);
        mockBaccarat.play(player, 1000, dealer);
        database = new Database();
        mockBaccarat.setDatabase(database);
    }

    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByTwoTimesTheBet_whenPlayIsCorrectAndIsBanker() {
        double bet = 1000;
        double newExpectedTotal = 50_000.0 + (bet * 2);
        database.setPlay(BANKER);
        database.setBet(bet);
        database.setPlayResult(true);
        mockBaccarat.dishOutPlayWinnings();

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }

    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByTwoTimesTheBet_whenPlayIsCorrectAndIsPlayer() {
        double bet = 1000;
        double newExpectedTotal = 50_000.0 + (bet * 2);
        database.setBet(bet);
        database.setPlay(PLAYER);
        database.setPlayResult(true);
        mockBaccarat.dishOutPlayWinnings();

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }
    @Test
    public void dishOutWinnings_shouldIncreasePlayerBalanceByNineTimesTheBet_whenPlayIsCorrectAndIsTie() {
        double bet = 1000;
        double newExpectedTotal = 50_000.0 + (bet * 9);
        database.setBet(bet);
        database.setPlay(TIE);
        database.setPlayResult(true);
        mockBaccarat.dishOutPlayWinnings();

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }

    @Test
    public void dishOutWinnings_shouldTakeAwayBetFromPlayerBalance_whenPlayIsWrong() {
        double bet = 2000.0;
        double originalBalance = 50_000.0;
        database.setBet(bet);
        database.setPlay(PLAYER);
        database.setPlayResult(false);
        mockBaccarat.dishOutPlayWinnings();

        assertEquals(originalBalance - bet, player.getBalance(), .001);
    }



    @Test
    public void testSetWinnings_shouldIncreasePlayerBalanceByMultiplierAmountTimesBetAmount() {
        double expectedTotal = 50_000.0 + 2000;
        mockBaccarat.moneyTransaction(2000, true);

        assertEquals(expectedTotal, player.getBalance(), .001);
    }



    @Test
    public void dishOutSidePlayWinnings_shouldIncreasePlayerBalanceByElevenTimesTheBet_whenSidePlayIsCorrectAndIsPair() {
        double bet = 1000;
        double newExpectedTotal = 50_000.0 + (bet * 11);
        database.setSidePlay(PAIR);
        database.setSideBet(bet);
        database.setSidePlayResult(true);

        mockBaccarat.dishOutSidePlayWinnings();

        assertEquals(newExpectedTotal, player.getBalance(), .001);
    }


    @Test
    public void dishOutSidePlayWinnings_shouldTakeAwayBetFromPlayerBalance_whenSidePlayIsWrong() {
        double bet = 2000.0;
        double originalBalance = 50_000.0;
        database.setSidePlay(PAIR);
        database.setSideBet(bet);
        database.setSidePlayResult(false);
        mockBaccarat.dishOutSidePlayWinnings();

        assertEquals(originalBalance - bet, player.getBalance(), .001);
    }

    @After
    public void close() {
        mockStaticCasino.close();
    }
}


