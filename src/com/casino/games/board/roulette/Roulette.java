package com.casino.games.board.roulette;

import com.casino.games.CasinoGames;
import com.casino.player.Dealer;
import com.casino.player.Player;

import java.util.Scanner;

public class Roulette extends CasinoGames {
    Scanner scan = new Scanner(System.in);
    Bets bets = new Bets();
    Table table = new Table();
    int winningNumber = getWinningNumber();

    @Override
    public boolean isPlayable(Player player, double bet) {
        return false;
    }

    @Override
    public void play(Player player, Dealer dealer, double bet) {

    }

    @Override
    public void distributeMoney() {

    }

    @Override
    public void endGame() {

    }

    //BUSINESS METHODS
    public void welcomeScreen() {
        System.out.println("                 \t\tMarco Bragado");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("|                                                         |");
        System.out.println("|                Welcome to Roulette table!               |");
        System.out.println("|             You have $XYZ in starting chips.            |");
        System.out.println("|                  Good Luck and Have Fun!                |");
        System.out.println("|                                                         |");
        System.out.println("|                                                         |");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%&&&&&&&\n");
        table.displayRouletteTable();
        System.out.print(" Press ANY KEY and choose which bet you would like to place!\n");
        scan.nextLine();
        initializeGame();
    }

    public void initializeGame() {
        //table.displayRouletteTable();
        bets.displayBets();
        bets.playBetType(bets.selectBetType());

    }


    //ROULETTE METHODS

    //TODO
    public void straight() {
        System.out.println("TODO");
        bets.playBetType(bets.selectBetType());
    }

    public void oddsOrEvens() {
        int userInput;
        boolean pickAgain;
        System.out.println("\t\tODDS/EVENS");
        System.out.println("\n1)\tOdds");
        System.out.println("2)\tEvens\n");
        do {
            try {
                System.out.printf("Please select: [1] Odds or [2] Evens  ");
                userInput = Integer.valueOf(scan.nextLine());
            } catch (Exception e) {
//                invalid();
//                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
            if (userInput != 1 && userInput != 2) {
                invalid();
                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
        } while (userInput == -1);
        if (winningNumber == 0 || winningNumber == 37) {
            System.out.println("lose");
        }
        if ((winningNumber % 2 == 0 && userInput == 2) || (winningNumber % 2 == 1 && userInput == 1)) {
            System.out.println("winning number: " + winningNumber);
            System.out.print("win");
        } else {
            System.out.println("lose");
        }
    }

    public void redOrBlack() {
        int userInput;
        boolean isRed = false;
        System.out.println("\t\tRED/BLACK");
        System.out.println("\n1)\tRed");
        System.out.println("2)\tBlack\n");
        do {
            try {
                System.out.printf("Please select: [1] Red or [2] Black  ");
                userInput = Integer.valueOf(scan.nextLine());
            } catch (Exception e) {
//                invalid();
//                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
            if (userInput != 1 && userInput != 2) {
                invalid();
                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
        } while (userInput == -1);
        if (winningNumber == 0 || winningNumber == 37) {
            System.out.println("lose");
        }
        for (int i = 0; i <= table.redNumbers.length; i++) {
            if (winningNumber == table.redNumbers[i]) {
                isRed = true;
            } else {
                isRed = false;
            }
        }
        if (isRed && userInput == 1) {
            System.out.println("won");
        }
        if (isRed == false && userInput == 2) {
            System.out.print("won");
        }
    }

    //TODO
    public void lowOrHigh() {
        System.out.println("TODO");
        bets.playBetType(bets.selectBetType());
    }

    //TODO
    public void dozens() {
        System.out.println("TODO");
        bets.playBetType(bets.selectBetType());
    }

    //TODO
    public void columns() {
        System.out.println("TODO");
        bets.playBetType(bets.selectBetType());
    }

    //TODO
    public void street() {
        System.out.println("TODO");
        bets.playBetType(bets.selectBetType());
    }

    //TODO
    public void sixNumbers() {
        System.out.println("TODO");
        bets.playBetType(bets.selectBetType());
    }

    //TODO
    public void split() {
        System.out.println("TODO");
        bets.playBetType(bets.selectBetType());
    }

    //TODO
    public void corner() {
        System.out.println("TODO");
        bets.playBetType(bets.selectBetType());
    }

    public int getWinningNumber() {
        int winningNumber = (int) (Math.random() * 36);
        return winningNumber;
    }

    public void invalid() {
        System.out.print("\n[INVALID SELECTION]  ");
    }
}