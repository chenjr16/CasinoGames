package com.casino.games.machines.slot;


import com.apps.util.Prompter;
import com.casino.employees.Dealer;
import com.casino.player.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Scanner;

import static org.junit.Assert.*;

public class SlotMachineTest {

    Player player;
    Dealer dealer;
    SlotMachine game;

    @Before
    public void setUp() {
        player = new Player("Junru", 5_000.0);
        dealer = new Dealer("James");
        game = new SlotMachine();
    }

    @Test
    public void isPlayable_shouldReturnFalse_whenPlayBalanceLessThanBet() {
        Player player1 = new Player("Jason", 5);
        Prompter prompter = new Prompter(new Scanner(System.in));
        assertFalse(game.isPlayable(player1, 50, prompter));
    }

    @Test
    public void isPlayable_shouldReturnFalse_whenPlayBalanceLessThanMinimum() {
        Prompter prompter = new Prompter(new Scanner(System.in));
        assertFalse(game.isPlayable(player, 1, prompter));
    }

    @Test
    public void getGameResult_shouldReturn60Bet_whenTripleBarResult() {
        assertEquals(60.0, game.getGameResult(1, new String[]{"BAR", "BAR", "BAR"}), 0.001);
    }

    @Test
    public void getGameResult_shouldReturn40Bet_whenTripleSevenResult() {
        assertEquals(40.0, game.getGameResult(1, new String[]{"SEVEN", "SEVEN", "SEVEN"}), 0.001);
    }

    @Test
    public void getGameResult_shouldReturn20Bet_whenTripleCherryResult() {
        assertEquals(20.0, game.getGameResult(1, new String[]{"Cherry", "Cherry", "Cherry"}), 0.001);
    }

    @Test
    public void getGameResult_shouldReturn10Bet_whenTripleBananaResult() {
        assertEquals(10.0, game.getGameResult(1, new String[]{"Banana", "Banana", "Banana"}), 0.001);
    }

    @Test
    public void getGameResult_shouldReturn10Bet_whenTripleLemonResult() {
        assertEquals(10.0, game.getGameResult(1, new String[]{"Lemon", "Lemon", "Lemon"}), 0.001);
    }

    @Test
    public void getGameResult_shouldReturn3Bet_whenDoubleCherryResult() {
        assertEquals(3.0, game.getGameResult(1, new String[]{"Cherry", "Cherry", "Lemon"}), 0.001);
    }

    @Test
    public void getGameResult_shouldReturn1Bet_whenSingleCherryResult() {
        assertEquals(1.0, game.getGameResult(1, new String[]{"BAR", "Cherry", "Lemon"}), 0.001);
    }



}