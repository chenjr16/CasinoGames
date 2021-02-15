package com.casino.games.machines.slot;


import com.casino.games.CasinoGames;
import com.casino.employees.Dealer;
import com.casino.player.Player;
import java.util.Arrays;

//author Junru Chen

public class SlotMachine extends CasinoGames {
    static double SLOT_MINIMUM = 5.0;
    Player player;
    Dealer dealer;
    double bet;
    double gameResult;


    @Override
    public boolean isPlayable(Player player, double bet) {
        boolean result = true;
        payoutTable();
        if (player.getBalance() < bet || bet < SLOT_MINIMUM) {
            result = false;
        }
        return result;
    }

    @Override
    public void play(Player player, Dealer dealer, double bet) {
        this.player = player;
        this.dealer = dealer;
        this.bet = bet;
        int random1 = (int) (Math.random() * 23);
        int random2 = (int) (Math.random() * 23);
        int random3 = (int) (Math.random() * 23);

        String[] result = new String[]{reel1[random1], reel2[random2], reel3[random3]};
        animate(random1, random2, random3);
        System.out.println("Your spin result is: " + Arrays.toString(result));

        gameResult = getGameResult(bet, result);

        if (gameResult > 0) {
            System.out.println("Congratulations, you won:  " + gameResult + " dollars");
        } else {
            System.out.println("Sorry, you didn't win anything, please try again next time!");
        }

    }

    @Override
    public void distributeMoney() {
        player.setBalance(player.getBalance() + gameResult);
        System.out.println("Player's new balance is: " + player.getBalance());
        dealer.setBalance(dealer.getBalance() - gameResult);
        System.out.println("Dealer's new balance is: " + dealer.getBalance());

    }

    @Override
    public void endGame() {

    }

    public double getGameResult(double bet, String[] result) {
        double winningAmount = 0;
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
                Thread.sleep(800-i* 50L);
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
                Thread.sleep(800-i* 50L);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        for (int i = 15; i >= 0; i--) {
            if (i >= 1){
                if (order3 - i < 0) {
                    order3 = order3 + 22;
                }
                if (order3 - i > 22) {
                    order3 = order3 - 22;
                }
                System.out.print(reel1[order1] + " " + reel2[order2] + " " + reel3[order3 - i] + "\r");
                try {
                    Thread.sleep(800-i* 50L);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
            else{
                System.out.print(reel1[order1] + " " + reel2[order2] + " " + reel3[order3 - i] + "\n");
                try {
                    Thread.sleep(750);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
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