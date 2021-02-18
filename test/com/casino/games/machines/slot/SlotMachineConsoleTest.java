package com.casino.games.machines.slot;

import com.casino.games.Casino;
import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class SlotMachineConsoleTest {
    Casino casino;
    Player player;
    Dealer dealer;
    double bet;
    SlotMachine slotMachine = new SlotMachine();
    SlotMachine mockSlot;
    MockedStatic<Casino> casinoMock;

    @Before
    public void setUp() {
        casino = new Casino();
        player = new Player("Junru ", 10_000.0);
        dealer = new Dealer("Dealer", 50_000_000.0);
        bet = 1;
        mockSlot = Mockito.spy(SlotMachine.class);
        casinoMock = Mockito.mockStatic(Casino.class);
    }

    @After
    public void cleanUp() {
        casinoMock.close();
    }

    @Test
    public void play_ShouldChangePlayerBalance_whenCalled() {

        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "Yes", String.valueOf(50));
        slotMachine.play(player, bet, dealer);
        assertNotEquals(10_000.0, player.getBalance(), 0.001);

    }

    @Test
    public void distributeMoney_shouldWin60_whenManipulatedGetRandom0() {

        Mockito.when(mockSlot.getRandom23()).thenReturn(0);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(10_060, player.getBalance(), 0.001);

    }

    @Test
    public void distributeMoney_shouldWin40_whenManipulatedGetRandom1() {

        Mockito.when(mockSlot.getRandom23()).thenReturn(1);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(10_040, player.getBalance(), 0.001);

    }

    @Test
    public void distributeMoney_shouldWin10_whenManipulatedGetRandom2() {

        Mockito.when(mockSlot.getRandom23()).thenReturn(2);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(10_010, player.getBalance(), 0.001);

    }

    @Test
    public void distributeMoney_shouldWin10_whenManipulatedGetRandom21() {

        Mockito.when(mockSlot.getRandom23()).thenReturn(21);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(10_010, player.getBalance(), 0.001);

    }

    @Test
    public void distributeMoney_shouldWin1_whenManipulatedGetRandom7() {

        Mockito.when(mockSlot.getRandom23()).thenReturn(7);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(10_001.0, player.getBalance(), 0.001);

    }

    @Test
    public void distributeMoney_shouldLose1_whenManipulatedGetRandom4() {

        Mockito.when(mockSlot.getRandom23()).thenReturn(3);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(9_999.0, player.getBalance(), 0.001);

    }

    @Test
    public void endGame_shouldChangeBetAndWin180_whenUsingCasinoMock() {

        Mockito.when(mockSlot.getRandom23()).thenReturn(0);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "Yes", String.valueOf(2), "Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(10_180, player.getBalance(), 0.001);

    }

    @Test
    public void endGame_shouldChangeBetAndWin120_whenUsingCasinoMock() {

        Mockito.when(mockSlot.getRandom23()).thenReturn(1);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "Yes", String.valueOf(2), "Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(10_120, player.getBalance(), 0.001);

    }
}