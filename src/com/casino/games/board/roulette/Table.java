package com.casino.games.board.roulette;

class Table {
    static Roulette roulette = new Roulette();
    // COLORS
    // SOURCE : https://www.quora.com/Java-How-do-I-use-System-out-print-to-print-my-text-in-color-to-the-console
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // SOURCE: https://www.youtube.com/watch?v=w-9ZTeO7q_E || 2D Array with Mike Dane
    public static int[][] rouletteTableLayout = {{1,2,3}, {4,5,6}, {7,8,9}, {10,11,12}, {13,14,15}, {16,17,18}, {19,20,21}, {22,23,24}, {25,26,27}, {28,29,30}, {31,32,33}, {34,35,36} };
    public int[] redNumbers = { 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36 };
    public int[] blackNumbers = { 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35 };


    public void displayRouletteTable(){
        System.out.print("\t");
        System.out.print("0");
        System.out.print(" ");
        System.out.println("00");
        for (int i = 0; i < rouletteTableLayout.length; i++)
        {
            for (int j = 0; j < rouletteTableLayout[i].length; j++)
            {
                System.out.print("" + rouletteTableLayout[i][j] + "\t");
                System.out.print(" ");
            }
            System.out.println(" ");
        }
    }


    // Rolls the ball and will give the user the winning number
    public void spinWheel() {
        System.out.println("The wheel is spinning and the ball is rolling!! ");
        for (int i = 0; i < 5; i ++){
            try {
                Thread.sleep(1000);
                System.out.printf("o ");
                System.out.printf("O ");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println("No more bets!");
        for(int i = 0 ; i <= 2; i ++){
            try {
                Thread.sleep(500);
                System.out.println("  o");
                Thread.sleep(500);
                System.out.println("    o");
                Thread.sleep(500);
                System.out.println("  o");
                Thread.sleep(500);
                System.out.println("    o");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("It looks like the ball has landed on .... " + roulette.getWinningNumber() );
    }
}
