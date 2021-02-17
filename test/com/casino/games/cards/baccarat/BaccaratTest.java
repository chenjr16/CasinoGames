
//package com.casino.games.cards.baccarat;
//
//import com.casino.player.Dealer;
//import com.casino.player.Player;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.Map;
//import com.casino.games.cards.baccarat.ResponsePipeline.Response;
//import com.casino.games.cards.baccarat.BaccaratDealer.Result;
//
//import static org.junit.Assert.*;
//
//public class BaccaratTest {
//    Player player;
//    Dealer dealer;
//    Baccarat baccarat;
//    Map<String, Response<?>> responseMap;
//    Map<String, Result<?>> resultMap;
//    double bet = 1000;
//
//    @Before
//    public void setUp() {
//        baccarat = new Baccarat();
//        resultMap = baccarat.getResultMap();
//        responseMap = baccarat.getResponseMap();
//        player = new Player("Nick", 50_000.0);
//        dealer = new Dealer("Ron");
//        baccarat.play(dealer);
//    }
//
//
//    @Test
//    public void dishOutWinnings_shouldIncreasePlayerBalanceByTwoTimesTheBet_whenPlayIsCorrectAndIsBanker() {
//        double newExpectedTotal = 50_000.0 + (bet * 2);
//        responseMap.put("play", new Response<>(Baccarat.Play.BANKER));
//        resultMap.put("winner", new Result<>(Baccarat.Play.BANKER));
//
//        baccarat.dishOutWinnings(responseMap, resultMap);
//
//        assertEquals(newExpectedTotal, player.getBalance(), .001);
//    }
//
//    @Test
//    public void dishOutWinnings_shouldIncreasePlayerBalanceByTwoTimesTheBet_whenPlayIsCorrectAndIsPlayer() {
//        double newExpectedTotal = 50_000.0 + (bet * 2);
//        responseMap.put("play", new Response<>(Baccarat.Play.PLAYER));
//        resultMap.put("winner", new Result<>(Baccarat.Play.PLAYER));
//
//        baccarat.dishOutWinnings(responseMap, resultMap);
//
//        assertEquals(newExpectedTotal, player.getBalance(), .001);
//    }
//    @Test
//    public void dishOutWinnings_shouldIncreasePlayerBalanceByNineTimesTheBet_whenPlayIsCorrectAndIsTie() {
//        double newExpectedTotal = 50_000.0 + (bet * 9);
//        responseMap.put("play", new Response<>(Baccarat.Play.TIE));
//        resultMap.put("winner", new Result<>(Baccarat.Play.TIE));
//
//        baccarat.dishOutWinnings(responseMap, resultMap);
//
//        assertEquals(newExpectedTotal, player.getBalance(), .001);
//    }
//
//    @Test
//    public void dishOutWinnings_shouldNotAffectPlayerBalance_whenPlayIsWrong() {
//        double sameTotal = 50_000.0;
//        responseMap.put("play", new Response<>(Baccarat.Play.BANKER));
//        resultMap.put("winner", new Result<>(Baccarat.Play.PLAYER));
//
//        baccarat.dishOutWinnings(responseMap, resultMap);
//
//        assertEquals(sameTotal, player.getBalance(), .001);
//    }
//
//    @Test
//    public void testSetWinnings_shouldIncreasePlayerBalanceByMultiplierAmountTimesBetAmount() {
//        double expectedTotal = 50_000.0 + (1000 * 2);
//        baccarat.setWinnings(2);
//        assertEquals(expectedTotal, player.getBalance(), .001);
//    }
//}


