package com.casino.games.board.roulette;

import com.casino.games.Casino;
import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RouletteTest {
    //INSTANCE VARIABLES
    Player player;
    Dealer dealer;
    Roulette roulette;
    Roulette mockRoulette;
    MockedStatic<Casino> staticMocked;



    @Before
    public void setUp(){
        player= new Player("Marco", 50_000);
        dealer = new Dealer("Mr. Dealer", 50_000);
        roulette = new Roulette();
        mockRoulette = mock(Roulette.class);
        staticMocked = mockStatic(Casino.class);
        roulette.play(dealer,100,dealer);
    }

    @Test
    public void getWinningNumber() {
        when(mockRoulette.getWinningNumber()).thenReturn(0);
        assertEquals(0, mockRoulette.getWinningNumber());
    }

    @Test
    public void straight_ShouldReturnTrue_WhenWinningNumberEqualUseSelection() {


    }

    @Test
    public void testOddsOrEvens() {
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2), "Yes", String.valueOf(50));

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

    @After
    public void cleanUp(){
        staticMocked.close();
    }
}