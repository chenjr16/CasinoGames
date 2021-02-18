package com.casino.games.machines.slot;

import com.casino.player.Dealer;
import com.casino.games.Casino;
import com.casino.games.CasinoGames;
import com.casino.games.Playable;
import com.casino.player.Player;

import java.util.Arrays;


public class SlotMachine extends CasinoGames {
    static double SLOT_MINIMUM = 0.25;
    private double betAgain;
    private Player player;
    private Dealer dealer;
    private double gameResult;

    @Override
    public Playable isPlayable(Player player, double bet) {
        Playable playable;
        if (player.getBalance() < bet) {
            playable = new Playable("SlotMachine", "You don't have enough to play", false, new SlotMachine());
        } else if (bet < SLOT_MINIMUM) {
            playable = new Playable("SlotMachine", "Too little money, minimal bet is: " + SLOT_MINIMUM, false, new SlotMachine());
        } else {
            playable = new Playable("SlotMachine", "Can play", true, new SlotMachine());
        }

        return playable;
    }

    @Override
    public void play(Player player, double bet, Dealer dealer) {
        this.player = player;
        this.dealer = dealer;
        int random1 = getRandom23();
        int random2 = getRandom23();
        int random3 = getRandom23();
        payoutTable();

        String input = Casino.prompt("Welcome to Slot Machine Game, Type [Yes] to start the game, or [No] to return to game menu: ", "[y|Y]es|[n|N]o", "That's not a valid response.");
        if (input.equalsIgnoreCase("yes")) {
            String[] result = new String[]{reel1[random1], reel2[random2], reel3[random3]};
            System.out.println("Started");
            animate(random1, random2, random3);
            System.out.println("Your spin result is: " + Arrays.toString(result));

            gameResult = getGameResult(bet, result);

            if (gameResult > 0) {
                System.out.println("Congratulations, you won:  " + gameResult + " dollars");
            } else {
                System.out.println("Sorry, you didn't win anything, please try again next time!");
            }
            distributeMoney();
        } else {
            Casino.prompt("Please type in [select game] to go back to game menu, or type '[quit] to leave our casino: ", " ", "Invalid input");
        }

    }

    @Override
    public void distributeMoney() {
        if (gameResult > 0) {
            dealer.moneyTransfer(player, true, gameResult);
        } else {
            dealer.moneyTransfer(player, false, -gameResult);
        }
        System.out.println("Player's new balance is: " + player.getBalance());
        System.out.println("Dealer's new balance is: " + dealer.getBalance());
        endGame();
    }

    @Override
    public void endGame() {
        String input = Casino.prompt("Do you want to play again? [Yes] or [No]: ", "[y|Y]es|[n|N]o", "That's not a valid response.");
        if (input.equalsIgnoreCase("yes")) {
            String betInput = Casino.prompt("Please enter your bet: ", "[0-9]*\\.?[0-9]*", "\nThat is " +
                    "not a valid bet!\n");
            betAgain = Double.parseDouble(betInput);
            play(player, betAgain, dealer);
        } else {
            Casino.prompt("Please type in [select game] to go back to game menu, or type '[quit] to leave our casino: ", " ", "Invalid input");
        }
    }

    public int getRandom23() {
        return (int) (Math.random() * 23);
    }

    public double getGameResult(double bet, String[] result) {
        double winningAmount;
        if (result[0].equals(result[1]) && result[1].equals(result[2]) && result[0].equals("BAR")) {
            winningAmount = bet * 60;
        } else if (result[0].equals(result[1]) && result[1].equals(result[2]) && result[0].equals("SEVEN")) {
            winningAmount = bet * 40;
        } else if (result[0].equals(result[1]) && result[1].equals(result[2]) && result[0].equals("Cherry")) {
            winningAmount = bet * 20;
        } else if (result[0].equals(result[1]) && result[1].equals(result[2])) {
            winningAmount = bet * 10;
        } else if ((result[0].equals(result[1]) || result[1].equals(result[2]) || result[0].equals(result[2])) &&
                (result[0].equals("Cherry") || result[1].equals("Cherry") || result[2].equals("Cherry"))) {
            winningAmount = bet * 3;
        } else if (result[0].equals("Cherry") || result[1].equals("Cherry") || result[2].equals("Cherry")) {
            winningAmount = bet * 1;
        } else {
            winningAmount = -bet;
        }
        return winningAmount;
    }

    private void animate(int order1, int order2, int order3) {
        for (int i = 15; i >= 0; i--) {
            if (order1 - i < 0) {
                order1 = order1 + 22;
            }
            if (order1 - i > 22) {
                order1 = order1 - 22;
            }
            System.out.print(reel1[order1 - i] + "\r");

            try {
                Thread.sleep(800 - i * 50L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        for (int i = 10; i >= 0; i--) {
            if (order2 - i < 0) {
                order2 = order2 + 22;
            }
            if (order2 - i > 22) {
                order2 = order2 - 22;
            }
            System.out.print(reel1[order1] + " " + reel2[order2 - i] + "\r");
            try {
                Thread.sleep(800 - i * 50L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        for (int i = 10; i > 0; i--) {
            if (order3 - i < 0) {
                order3 = order3 + 22;
            }
            if (order3 - i > 22) {
                order3 = order3 - 22;
            }
            System.out.print(reel1[order1] + " " + reel2[order2] + " " + reel3[order3 - i] + "\r");
            try {
                Thread.sleep(800 - i * 50L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.print(reel1[order1] + " " + reel2[order2] + " " + reel3[order3] + "\n");
        try {
            Thread.sleep(750);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }



    private void payoutTable() {
        //rule based on https://anygamble.com/guide/learn-how-to-count-probability-and-payouts-in-slots/
        System.out.println("Payout Table: \n" +
                "3 X BAR : Winning 60\n" +
                "3 X SEVEN : Winning 40\n" +
                "3 X Cherry : Winning 20\n" +
                "3 X Other Fruit : Winning 10\n" +
                "2 X Cherry : Winning 3\n" +
                "1 X Cherry : Winning 1");
    }


    String[] reel1 = {"BAR", "SEVEN", "Orange", "SEVEN", "Banana", "SEVEN", "Lemon",
            "Cherry", "Orange", "Cherry", "Banana", "Cherry", "Lemon", "Cherry",
            "Orange", "Banana", "Orange", "Lemon", "Orange", "Banana", "Lemon",
            "Banana", "Lemon"};
    String[] reel2 = {"BAR", "SEVEN", "Orange", "Lemon", "Banana", "Orange", "Lemon",
            "Banana", "Orange", "Cherry", "Banana", "Cherry", "Lemon", "Cherry",
            "Orange", "Banana", "Orange", "Lemon", "Orange", "Banana", "Lemon",
            "Banana", "Lemon"};
    String[] reel3 = {"BAR", "SEVEN", "Orange", "Lemon", "Banana", "Orange", "Lemon",
            "Banana", "Orange", "Cherry", "Banana", "Cherry", "Lemon", "Cherry",
            "Orange", "Banana", "Orange", "Lemon", "Orange", "Banana", "Lemon",
            "Banana", "Lemon"};

}