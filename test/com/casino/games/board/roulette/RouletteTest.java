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
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class RouletteTest {
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

//    @Test
//    public void
    @Test
    public void getWinningNumber() {
        when(mockRoulette.getWinningNumber()).thenReturn(2);
        assertEquals(2, mockRoulette.getWinningNumber());
    }

    @Test
    public void straight_ShouldReturnTrue_WhenWinningNumberEqualUseSelection() {
        when(mockRoulette.straight()).thenReturn(true);
        assertEquals(true,mockRoulette.straight());

    }

    @Test
    public void oddsOrEvens_ShouldReturnTrue_WhenUserSelectionAndWinningNumberEqual() {
        Roulette mockRoulette = Mockito.spy(Roulette.class);
        Mockito.when(mockRoulette.getWinningNumber()).thenReturn(2);
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2), String.valueOf(2));
        assertTrue(mockRoulette.playBetType(2));
    }

    @Test
    public void oddsOrEvens_ShouldReturnFalse_WhenUserSelectionAndWinningNumberNotEqual() {
        Roulette mockRoulette = Mockito.spy(Roulette.class);
        Mockito.when(mockRoulette.getWinningNumber()).thenReturn(2);
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2), String.valueOf(2));
        assertTrue(mockRoulette.playBetType(2));
    }


    @Test
    public void redOrBlack_ShouldReturnTrue_WhenWinningNumberEqualUseSelection() {
        Roulette mockRoulette = Mockito.spy(Roulette.class);
        Mockito.when(mockRoulette.getWinningNumber()).thenReturn(2);
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2), String.valueOf(2));
        assertTrue(mockRoulette.playBetType(3));
    }

    @Test
    public void dowOrHigh_ShouldReturnTrue_WhenWinningNumberEqualUseSelection() {
        Roulette mockRoulette = Mockito.spy(Roulette.class);
        Mockito.when(mockRoulette.getWinningNumber()).thenReturn(2);
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2), String.valueOf(2));
        assertTrue(mockRoulette.playBetType(4));
    }

    @Test
    public void dozen_ShouldReturnTrue_WhenWinningNumberEqualUseSelection() {
        Roulette mockRoulette = Mockito.spy(Roulette.class);
        Mockito.when(mockRoulette.getWinningNumber()).thenReturn(2);
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2), String.valueOf(2));
        assertTrue(mockRoulette.playBetType(5));
    }

    @Test
    public void columns_ShouldReturnTrue_WhenWinningNumberEqualUseSelection() {
        Roulette mockRoulette = Mockito.spy(Roulette.class);
        Mockito.when(mockRoulette.getWinningNumber()).thenReturn(2);
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2), String.valueOf(2));
        assertTrue(mockRoulette.playBetType(6));
    }

    @Test
    public void street_ShouldReturnTrue_WhenWinningNumberEqualUseSelection() {
        Roulette mockRoulette = Mockito.spy(Roulette.class);
        Mockito.when(mockRoulette.getWinningNumber()).thenReturn(2);
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2), String.valueOf(2));
        assertTrue(mockRoulette.playBetType(7));
    }

    @After
    public void cleanUp(){
        staticMocked.close();
    }
}