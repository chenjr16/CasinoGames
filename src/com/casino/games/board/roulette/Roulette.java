package com.casino.games.board.roulette;

import com.casino.games.CasinoGames;
import com.casino.games.Playable;
import com.casino.player.Dealer;
import com.casino.player.Player;

import java.util.Random;
import java.util.Scanner;

public class Roulette extends CasinoGames {
    private final Scanner scan = new Scanner(System.in);
    private final Bets bets = new Bets();
    private final Table table = new Table();
    private final Random randomNumber = new Random();
    private int winningNumber;
    private boolean winResult;
    private int userInput;

    //METHODS
    @Override
    public void play(Player player, double bet, Dealer dealer) {
        welcomeScreen();
    }

    @Override
    public Playable isPlayable(Player player, double bet) {
        return null;
    }

    @Override
    public void distributeMoney() {

    }

    @Override
    public void endGame() {
        System.exit(0);
    }



    public void welcomeScreen() {
        System.out.println("                 \t\tMarco Bragado");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        System.out.println("|                                                          |");
        System.out.println("|                Welcome to the Roulette table!            |");
        System.out.println("|               You have $XYZ in starting chips.           |");
        System.out.println("|                   Good Luck and Have Fun!                |");
        System.out.println("|                                                          |");
        System.out.println("|                                                          |");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%&&&&&&&%\n");
        System.out.print(" Press ANY KEY and choose which bet you would like to place!\n");
        scan.nextLine();
        initializeGame();
    }

    public void initializeGame() {
        clearConsole();
        winningNumber = getWinningNumber();
        bets.displayBets();                                     // Display Bets
        winResult = bets.playBetType(bets.selectBetType());     // User Selects Bets
        table.spinWheel();                                      // Ball Animation and Winning Number Reveal
        didPlayerWin(winResult);                                // If true, player won else lost
        playAgain();
    }


    //ROULETTE METHODS
    //DONE
    public boolean straight() {
        int userInput;
        clearConsole();
        System.out.println("\n\t\tStraight [1 Number]");
        table.displayRouletteTable();
        System.out.println("\n1)\t[0, 00]");
        System.out.println("2)\t[1 - 36]\n");

        // 1st Screen Selection
        do {
            try {
                System.out.print("\n Please select the number you would like to bet on: 1) => [0, 00]   OR   2) => [1 - 36] : ");
                userInput = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                userInput = -1;
            }
            if (userInput != 1 && userInput != 2) {
                invalid();
                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
        } while (userInput == -1);

        //2nd Screen Selection
        if (userInput == 1) {
            int optionSelection;
            do {
                System.out.println("\n1)\t[0]");
                System.out.println("2)\t[00]\n");
                System.out.print("\n Please select from the following: 1) => [0]  OR  2) => [00] : ");
                try {
                    optionSelection = Integer.parseInt(scan.nextLine());
                } catch (Exception e) {
                    optionSelection = -1;
                }
                if (optionSelection != 1 && optionSelection != 2) {
                    invalid();
                    System.out.println("Please pick from the list available\n");
                    optionSelection = -1;
                }
            } while (optionSelection == -1);
            if (optionSelection == 1 && winningNumber == 0) {
                return true;
            } else {
                return optionSelection == 2 && winningNumber == 37;
            }
        }
        //3rd Screen Selection
        if (userInput == 2) {
            System.out.print("Please select from the following [1 - 36]: ");
            int numberSelection = Integer.parseInt(scan.nextLine());
            return numberSelection == winningNumber;
        }
        return false;
    }

    //DONE
    public boolean oddsOrEvens() {
        int userInput;
        clearConsole();
        System.out.println("\n\t\tODDS/EVENS");
        table.displayRouletteTable();
        System.out.println("\n1)\tOdds");
        System.out.println("2)\tEvens\n");
        do {
            try {
                System.out.print("Please select: [1] Odds or [2] Evens  ");
                userInput = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                userInput = -1;
            }
            if (userInput != 1 && userInput != 2) {
                invalid();
                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
        } while (userInput == -1);
        System.out.println("Winning Number " + winningNumber);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        if ((winningNumber % 2 == 0 && userInput == 2) || (winningNumber % 2 == 1 && userInput == 1)) {
            System.out.println("WIN");
            return true;
        } else {
            System.out.println("LOSE");
            return false;
        }
    }

    //DONE
    public boolean redOrBlack() {
        int userInput;
        boolean isRed = false;
        clearConsole();
        System.out.println("\t\tRED/BLACK");
        table.displayRouletteTable();
        System.out.println("\n1)\tRed");
        System.out.println("2)\tBlack\n");
        do {
            try {
                System.out.print("Please select: [1] Red or [2] Black  ");
                userInput = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                userInput = -1;
            }
            if (userInput != 1 && userInput != 2) {
                invalid();
                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
        } while (userInput == -1);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        for (int i = 0; i < table.redNumbers.length; i++) {
            if (winningNumber == table.redNumbers[i]) {
                isRed = true;
            } else {
                isRed = false;
            }
        }
        if (isRed && userInput == 1) {
            return true;
        }else {
            return false;
        }
    }

    //DONE
    public boolean lowOrHigh() {
        clearConsole();
        int userInput;
        System.out.println("\n\t\tLows or Highs");
        table.displayRouletteTable();
        System.out.println("\n1)\tLow [1 - 18]");
        System.out.println("2)\tHigh [19 - 36]\n");
        do {
            try {
                System.out.print("Please select: [1] Lows or [2] Highs  ");
                userInput = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                userInput = -1;
            }
            if (userInput != 1 && userInput != 2) {
                invalid();
                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
        } while (userInput == -1);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        if (winningNumber < 19 && userInput == 1 || winningNumber > 18 && userInput == 2) {
            return true;
        } else {
            return false;
        }
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

    public void didPlayerWin(boolean win){
        if(win){
            System.out.println("\n\t\t   Congrats, you won!");
            System.out.print("\tYou just won ");
            //Create addition/deductions logic
        }
        else{
            System.out.println("\n\t\t   I'm sorry, you lost!");
            System.out.print("\tYou just lost ");
            //Create addition/deductions logic
        }
        System.out.println("[BET AMOUNT] chips!");
        System.out.println("\n\t\tYou now have [CURRENT AMOUNT]");
    }

    public int getWinningNumber() {
        int winningNumber = (int) (Math.random() * 36);
        return winningNumber;
    }

    public void invalid() {
        System.out.print("\n[INVALID SELECTION]  ");
    }

    public void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println("");
    }

}