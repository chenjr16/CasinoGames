package com.casino.games.board.roulette;

import com.casino.games.Casino;
import com.casino.games.CasinoGames;
import com.casino.games.Playable;
import com.casino.player.Dealer;
import com.casino.player.Player;

import java.util.Random;
import java.util.Scanner;

public class Roulette extends CasinoGames {
    private Player player;
    private Dealer dealer;
    private int userInput;
    private double gameBet;
    private int winningNumber;
    private boolean winResult;
    private double totalWinning;
    private int winningModifier;
    private final Bets bets = new Bets();
    private final Table table = new Table();
    private final Random randomNumber = new Random();
    private final Scanner scan = new Scanner(System.in);
    static double ROULETTE_MINIMUM = 100.0;

    //METHODS
    @Override
    public void play(Player player, double bet, Dealer dealer) {
        this.player = player;
        this.dealer = dealer;
        this.gameBet = bet;
        welcomeScreen();
        initializeGame();
    }

    @Override
    public Playable isPlayable(Player player, double bet) {
        Playable playable;
        if (player.getBalance() < bet) {
            playable = new Playable("Roulette", "You don't have enough to play", false, new Roulette());
        } else if (bet < ROULETTE_MINIMUM) {
            playable = new Playable("Roulette", "Too little money, minimal bet is: " + ROULETTE_MINIMUM, false, new Roulette());
        } else {
            playable = new Playable("Roulette", "Can play", true, new Roulette());
        }

        return playable;
    }

    @Override
    public void distributeMoney() {
        if (winResult) {
            dealer.moneyTransfer(player, true, totalWinning);
        } else {
            dealer.moneyTransfer(player, false, gameBet);
        }
        System.out.println("\t\tYour current balance is: " + player.getBalance());
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
        System.out.println("|                                                          |");
        System.out.println("|                   Good Luck and Have Fun!                |");
        System.out.println("|                                                          |");
        System.out.println("|                                                          |");
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%&&&&&&&%\n");
        System.out.print(" Press ENTER and choose which bet you would like to place!\n");
        scan.nextLine();
    }

    public void initializeGame() {
        clearConsole();
        bets.displayBets();                                         // Display Bets
        getWinningNumber();
        winResult = playBetType(bets.selectBetType());             // User Selects Bets
        table.spinWheel();                                        // Ball Animation and Winning Number Reveal
        displayWinningNumber();
        didPlayerWin(winResult);                                 // If true, player won else lost
        distributeMoney();
        playAgain();
    }

    public boolean playBetType(int betType) {
        boolean winResult = false;
        switch (betType) {
            case 1:
                winResult = straight();
                break;
            case 2:
                winResult = oddsOrEvens();
                break;
            case 3:
                winResult = redOrBlack();
                break;
            case 4:
                winResult = lowOrHigh();
                break;
            case 5:
                winResult = dozen();
                break;
            case 6:
                winResult = columns();
                break;
            case 7:
                winResult = street();
                break;
        }
        return winResult;
    }

    public void didPlayerWin(boolean win) {
        if (win) {
            System.out.println("\n\t\t\t   Congrats, you won!");
            System.out.println("\t\t  You just won " + totalWinning + " dollars\t\t\t");
        } else {
            System.out.println("\n\t\t\t   I'm sorry, you lost!");
            System.out.println("\t\t  You just lost " + gameBet + " dollars");
        }
    }

    //ROULETTE METHODS

    public boolean straight() {
        clearConsole();
        winningModifier = 35;
        System.out.println("\n\t\tStraight [1 Number]");
        table.displayRouletteTable();
        System.out.println("\n1)\t[0, 00]");
        System.out.println("2)\t[1 - 36]\n");

        // 1st Screen Selection
        String newInput = Casino.prompt("\n Please select the number you would like to bet on: 1) => [0, 00]   OR   2) => [1 - 36] : ", "[1-2]", "That's not a valid input.");
        userInput = Integer.parseInt(newInput);

        //2nd Screen Selection
        if (userInput == 1) {
            int optionSelection;
            System.out.println("\n1)\t[0]");
            System.out.println("2)\t[00]\n");
            String newOptionSelection = Casino.prompt("\n Please select from the following: 1) => [0]  OR  2) => [00] : ", "[1-2]", "[INVALID INPUT] Please select from the following options.");
            optionSelection = Integer.parseInt(newOptionSelection);
            totalWinning = winningModifier * gameBet;
            if (optionSelection == 1 && winningNumber == 0) {
                return true;
            } else {
                totalWinning = winningModifier * gameBet;
                return optionSelection == 2 && winningNumber == 37;
            }
        }
        //3rd Screen Selection
        if (userInput == 2) {
            int userGuess;
            String newOptionSelection = Casino.prompt("\n Please select from the following [1 - 36]: ", "(3[0-6]|[12][0-9]|[1-9])", "[INVALID INPUT] Please select from the following options.");
            userGuess = Integer.parseInt(newOptionSelection);
            if (userGuess == winningNumber) {
                totalWinning = winningModifier * gameBet;
                return true;
            }
        }
        return false;
    }

    public boolean oddsOrEvens() {
        clearConsole();
        winningModifier = 1;
        System.out.println("\n\t\tODDS/EVENS");
        table.displayRouletteTable();
        System.out.println("\n1)\tOdds");
        System.out.println("2)\tEvens\n");

        String newOptionSelection = Casino.prompt("\n Please select from the following: 1) Odds  OR  2) Evens : ", "[1-2]", "[INVALID INPUT] Please select from the following options.");
        userInput = Integer.parseInt(newOptionSelection);
        if (winningNumber == 0 || winningNumber == 37) {
            System.out.println("WINNING: " + winningNumber);
            return false;
        }
        if ((winningNumber % 2 == 0 && userInput == 2) || (winningNumber % 2 == 1 && userInput == 1)) {
            System.out.println("WINNING: " + winningNumber);
            totalWinning = winningModifier * gameBet;
            return true;
        } else {
            System.out.println("WINNING: " + winningNumber);
            return false;
        }
    }

    public boolean redOrBlack() {
        boolean isRed = false;
        clearConsole();
        System.out.println("\t\tRED/BLACK");
        table.displayRouletteTable();
        System.out.println("\n1)\tRed");
        System.out.println("2)\tBlack\n");

        String newOptionSelection = Casino.prompt("\n Please select from the following: 1) Red  OR  2) Black : ", "[1-2]", "[INVALID INPUT] Please select from the following options.");
        userInput = Integer.parseInt(newOptionSelection);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        for (int i = 0; i < table.redNumbers.length; i++) {
            isRed = winningNumber == table.redNumbers[i];
        }
        totalWinning = winningModifier * gameBet;
        return isRed && userInput == 1;
    }

    public boolean lowOrHigh() {
        clearConsole();
        System.out.println("\n\t\tLows or Highs");
        table.displayRouletteTable();
        System.out.println("\n1)\tLow [1 - 18]");
        System.out.println("2)\tHigh [19 - 36]\n");
        String newOptionSelection = Casino.prompt("\n Please select from the following: 1) Lows  OR  2) Highs : ", "[1-2]", "[INVALID INPUT] Please select from the following options.");
        userInput = Integer.parseInt(newOptionSelection);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        return winningNumber < 19 && userInput == 1 || winningNumber > 18 && userInput == 2;
    }

    public boolean dozen() {
        clearConsole();
        winningModifier = 2;
        System.out.println("\n\t\tDozens");
        table.displayRouletteTable();
        System.out.println("\n1)\t[1 - 12]");
        System.out.println("2)\t[13 - 24]");
        System.out.println("3)\t[25 - 36]\n");

        String newOptionSelection = Casino.prompt("\n Please select from the following: [1] 1-12 , [2] 13-24 , [3] 25-36 : ", "[1-3]", "[INVALID INPUT] Please select from the following options.");
        userInput = Integer.parseInt(newOptionSelection);
        if (winningNumber == 0 || winningNumber == 37) {
            System.out.println("lost");
            return false;
        }
        if ((userInput == 1 && winningNumber < 13) || (userInput == 2 && winningNumber > 12 && winningNumber < 25) || (userInput == 3 && winningNumber > 24)) {
            totalWinning = winningModifier * gameBet;
            return true;
        } else {
            System.out.println("lost");
            return false;
        }
    }

    public boolean columns() {
        clearConsole();
        winningModifier = 2;
        System.out.println("\n\t\t\t\tColumns");
        table.displayRouletteTable();
        System.out.println("\n1)\t1st Column (1,4,7,10,13,16,19,22,25,28,31,34)");
        System.out.println("2)\t2nd Column (2,5,8,11,14,17,20,23,26,29,32,35)");
        System.out.println("3)\t3rd Column (3,6,9,12,15,18,21,24,27,30,33,36)\n");

        String newOptionSelection = Casino.prompt("\n Please select from the following: [1] 1st Column , [2] 2nd Column , [3] 3rd Column : ", "[1-3]", "[INVALID INPUT] Please select from the following options.");
        userInput = Integer.parseInt(newOptionSelection);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        // will iterate at the number user selected and increments by 3
        for (int i = userInput; i <= 37; i += 3) {
            if (winningNumber == i) {
                totalWinning = winningModifier * gameBet;
                return true;
            }
        }
        return false;
    }

    public boolean street() {
        clearConsole();
        winningModifier = 11;
        System.out.println("\t STREET");
        table.displayRouletteTable();
        System.out.println("\nPlease select which row you would like to bet on!\n");
        System.out.println("Row [1] = 1/2/3");
        System.out.println("Row [2] = 4/5/6");
        System.out.println("Row [3] = 7/8/9");
        System.out.println("Row [4] = 10/11/12");
        System.out.println("Row [5] = 13/14/15");
        System.out.println("Row [6] = 16/17/18");
        System.out.println("Row [7] = 19/20/21");
        System.out.println("Row [8] = 22/23/24");
        System.out.println("Row [9] = 25/26/27");
        System.out.println("Row [10] = 28/29/30");
        System.out.println("Row [11] = 31/32/33");
        System.out.println("Row [12] = 34/35/36");

        String newOptionSelection = Casino.prompt("\n Please select from the following: [1 - 12]: ", "1[0-2]|[1-9]", "[INVALID INPUT] Please select from the following options.");
        userInput = Integer.parseInt(newOptionSelection);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        // stores the user's input starting number  Ex. Choosing option 1 starts at 1 | Choosing option 2 starts at 4, etc.
        int startingNumber = (userInput - 1) * 3 + 1;
        for (int i = startingNumber; i < startingNumber + 3; i++) {
            if (winningNumber == i) {
                totalWinning = winningModifier * gameBet;
                return true;
            }
        }
        return false;
    }

    public void displayWinningNumber() {
        String winningNumberString = String.valueOf(winningNumber);
//        System.out.println("\t\t\t\t\t\t " + winningNumberString);    <= will use for debugging when number is not displayed in result box.
        System.out.println("\t\t\t\t  **************");
        System.out.print("\t\t\t\t  the number ");
        if (winningNumber == 0 || winningNumber == 37) {
            System.out.println(Table.ANSI_GREEN_BACKGROUND + Table.ANSI_BLACK + winningNumberString + Table.ANSI_RESET);
        } else {
            for (int i = 0; i < table.redNumbers.length; i++) {
                if (Integer.parseInt(winningNumberString) == table.redNumbers[i]) {
                    System.out.println(Table.ANSI_RED_BACKGROUND + Table.ANSI_BLACK + winningNumberString + Table.ANSI_RESET);
                }
            }
            for (int i = 0; i < table.blackNumbers.length; i++) {
                if (Integer.parseInt(winningNumberString) == table.blackNumbers[i]) {
                    System.out.println(Table.ANSI_WHITE_BACKGROUND + Table.ANSI_BLACK + winningNumberString + Table.ANSI_RESET);
                }
            }
        }
        System.out.println("\t\t\t\t  **************");
    }

    public int getWinningNumber() {
        winningNumber = randomNumber.nextInt(38);
        return winningNumber;
    }

    public void playAgain() {
        System.out.println("\n\n\t\tWould you like to play again?");
        System.out.println("\n1)\tYes");
        System.out.println("2)\tNo, Go Back TO Main Casino Menu");
        System.out.println("3)\tExit Game\n");
        String newOptionSelection = Casino.prompt("\n Please select from the following: [1] Yes , [2] No, Go Back To Casino Menu , [3] Exit Game : ", "[1-3]", "[INVALID INPUT] Please select from the following options.");
        userInput = Integer.parseInt(newOptionSelection);
        switch (userInput) {
            case 1:
                String betInput = Casino.prompt("Please enter your bet: ", "[0-9]*\\.?[0-9]*", "\nThat is " +
                        "not a valid bet!\n");
                double betAgain = Double.parseDouble(betInput);
                play(player, betAgain, dealer);
                break;
            case 2:
                Casino.prompt("Please type in 'select game' to go back to game menu", " ", "Invalid input");
            case 3:
                System.out.println("\nThank you for playing!");
                endGame();
        }
    }

    public void invalid() {
        System.out.print("\n[INVALID SELECTION]  ");
    }

    public void clearConsole() {
        for (int i = 0; i < 50; ++i) System.out.println();
    }

}