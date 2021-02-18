package com.casino.games.machines.slot;

import com.apps.util.Prompter;
import com.casino.games.Casino;
import com.casino.games.CasinoGames;
import com.casino.player.Dealer;
import com.casino.player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.File;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;

public class SlotMachineConsoleTest {
    Casino casino;
    Player player;
    Dealer dealer;
    double bet;
    CasinoGames game;
    SlotMachine slotMachine = new SlotMachine();
//    MockedStatic<Casino> casinoMock;

    @Before
    public void setUp() {
        casino = new Casino();
        player = new Player("Junru ", 10_000.0);
        dealer = new Dealer("Dealer", 50_000_000.0);
        bet = 1;
    }

    @After
    public void cleanUp() {

    }

    @Test
    public void prompterShouldChangePlayerBalance() {

        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "Yes", String.valueOf(50));
        slotMachine.play(player, bet, dealer);

        assertNotEquals(10_000.0, player.getBalance(), 0.001);

    }

    @Test
    public void name() {
        SlotMachine mockSlot = Mockito.spy(SlotMachine.class);
        Mockito.when(mockSlot.getRandom23()).thenReturn(1);
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn("Yes", "no");
        mockSlot.play(player, bet, dealer);
        assertEquals(10_040, player.getBalance(), 0.001);

    }
}