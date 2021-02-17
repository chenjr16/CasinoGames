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

    //DONE
    public boolean dozen() {
        clearConsole();
        System.out.println("\n\t\tDozens");
        table.displayRouletteTable();
        System.out.println("\n1)\t[1 - 12]");
        System.out.println("2)\t[13 - 24]");
        System.out.println("3)\t[25 - 36]\n");
        do {
            try {
                System.out.print("Please select: [1] 1-12 , [2] 13-24 , [3] 25-36 : ");
                userInput = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                userInput = -1;
            }
            if (userInput != 1 && userInput != 2 && userInput != 3) {
                invalid();
                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
        } while (userInput == -1);
        if (winningNumber == 0 || winningNumber == 37) {
            System.out.println("lost");
            return false;
        }
        if ((userInput == 1 && winningNumber < 13) || (userInput == 2 && winningNumber > 12 && winningNumber < 25) || (userInput == 3 && winningNumber > 24)) {
            System.out.println("won");
            return true;
        } else {
            System.out.println("lost");
            return false;
        }
    }

    //DONE
    public boolean columns() {
        int userInput;
        clearConsole();
        System.out.println("\n\t\t\t\tColumns");
        table.displayRouletteTable();
        System.out.println("\n1)\t1st Column (1,4,7,10,13,16,19,22,25,28,31,34)");
        System.out.println("2)\t2nd Column (2,5,8,11,14,17,20,23,26,29,32,35)");
        System.out.println("3)\t3rd Column (3,6,9,12,15,18,21,24,27,30,33,36)\n");
        do {
            try {
                System.out.print("Please select: [1] 1st Column , [2] 2nd Column , [3] 3rd Column : ");
                userInput = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                userInput = -1;
            }
            if (userInput != 1 && userInput != 2 && userInput != 3) {
                invalid();
                System.out.println("Please pick from the list available\n");
                userInput = -1;
            }
        } while (userInput == -1);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        for (int i = userInput; i <= 36; i += 3) {
            if (winningNumber == i) {
                return true;
            } else {
                return true;
            }
        }
        return false;
    }

    ///DONE
    public boolean street() {
        clearConsole();
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
        do {
            try {
                System.out.print("\nWhich Street do you want to bet on? [1-12]: ");
                userInput = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                userInput = -1;
            }

            if (userInput < 1 || userInput > 12) {
                invalid();
                System.out.println("Please select from the list.");
                userInput = -1;
            }
        } while (userInput == -1);
        if (winningNumber == 0 || winningNumber == 37) {
            return false;
        }
        int startingNumber = (userInput - 1) * 3 + 1;
        for (int i = startingNumber; i < startingNumber + 3; i++) {
            if (winningNumber == i) {
                return true;
            }
        }
        return false;
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