package com.casino.games.board.roulette;

import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.Before;
import org.junit.Test;

public class RouletteTest {
    //INSTANCE VARIABLES
    Player player;
    Dealer dealer;
    Roulette roulette;


    @Before
    public void setUp(){
        player= new Player("Marco", 50_000);
        dealer = new Dealer("Mr. Dealer", 50_000);
        roulette = new Roulette();
        roulette.play(player,50000,dealer);
    }

    @Test
    public void straight_ShouldReturnTrue_WhenWinningNumberEqualUseSelection() {

    }

    @Test
    public void testOddsOrEvens() {
    }

    @Test
    public void testRedOrBlack() {
    }

    @Test
    public void testLowOrHigh() {
    }

    @Test
    public void testDozen() {
    }

    @Test
    public void testColumns() {
    }

    @Test
    public void testStreet() {
    }
}