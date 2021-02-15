package com.casino.games.board.roulette;

import java.util.Scanner;

import static com.casino.games.board.roulette.Table.roulette;

class Bets {
    Scanner scan = new Scanner(System.in);
    public void displayBets(){
        Roulette roulette = new Roulette();
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
        System.out.println("8)\t\t6 Numbers\t\t5:1");
        System.out.println("9)\t\tSplit\t\t\t17:1");
        System.out.print("10)\t\tCorner\t\t\t8:1");
        System.out.println("\n");
        System.out.println("Please select a type of bet you would like to place [1 - 10]");
    }

    public int selectBetType(){
        int betSelected;
        do
        {
            System.out.printf("\nWhat kind of bet would you like to make [1-10]: ");

            try
            {
                betSelected = Integer.valueOf(scan.nextLine());
            }
            catch (Exception e)
            {
                betSelected = 0;
            }

            if (betSelected > 0 && betSelected < 11)
            {
                return betSelected;
            }
            else
            {
                roulette.invalid();
                System.out.print("Please select from the list: [1 - 10]\n");
                betSelected = 0;
            }
        } while (betSelected == 0);
        return betSelected;
    }

    public void playBetType(int betType) {
        switch (betType) {
            case 2:
                roulette.oddsOrEvens();

                break;
            case 3:
                roulette.redOrBlack();
                break;
            case 4:
                roulette.lowOrHigh();
                break;
            case 5:
                roulette.dozens();
                break;
            case 6:
                roulette.columns();
                break;
            case 7:
                roulette.street();
                break;
            case 8:
                roulette.sixNumbers();
                break;
            case 9:
                roulette.split();
                break;
            case 10:
                roulette.corner();
                break;
        }
    }
}
