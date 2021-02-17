package com.casino.games.board.roulette;

import java.util.Scanner;

import static com.casino.games.board.roulette.Table.roulette;

class Bets {
    Scanner scan = new Scanner(System.in);

    public void displayBets() {
        System.out.println("\nHere are the Bets you can make and their payouts:");
        System.out.println(" ");
        System.out.println("\n#\t\tBET TYPE\t\tPAYOUT");
        System.out.println("----------------------------");
        System.out.println("1)\t\tStraight\t\t35:1");
        System.out.println("2)\t\tOdds/Evens\t\t1:1");
        System.out.println("3)\t\tRed/Blacks\t\t1:1");
        System.out.println("4)\t\tLows/Highs\t\t1:1");
        System.out.println("5)\t\tDozens\t\t\t2:1");
        System.out.println("6)\t\tColumns\t\t\t2:1");
        System.out.println("7)\t\tStreet\t\t\t11:1");
        System.out.println("\n");
        System.out.println("Please select a type of bet you would like to place [1 - 7]\n");
    }

    public int selectBetType() {
        int betSelected;
        do {
            System.out.print("\nWhat kind of bet would you like to make [1-7]: ");
            try {
                betSelected = Integer.parseInt(scan.nextLine());
            } catch (Exception e) {
                betSelected = 0;
            }
            if (betSelected > 0 && betSelected < 11) {
                return betSelected;
            } else {
                roulette.invalid();
                System.out.print("Please select from the list: [1 - 7]\n");
                betSelected = 0;
            }
        } while (betSelected == 0);
        return betSelected;
    }

    public boolean playBetType(int betType) {
        boolean result = false;
        switch (betType) {
            case 1:
                result = roulette.straight();
                break;
            case 2:
                result = roulette.oddsOrEvens();
                break;
            case 3:
                result = roulette.redOrBlack();
                break;
            case 4:
                result = roulette.lowOrHigh();
                break;
            case 5:
                result = roulette.dozen();
                break;
            case 6:
                result = roulette.columns();
                break;
            case 7:
                result = roulette.street();
                break;
        }
        return result;
    }
}