package com.casino.games.machines.slot;

import com.casino.games.GameInterface;
import com.casino.player.Dealer;
import com.casino.player.Player;

import java.lang.reflect.Array;
import java.util.Arrays;

//author Junru Chen

class SlotMachine implements GameInterface {
    static double SLOT_MINIMUM = 5.0;
    Player player;
    Dealer dealer;
    double bet;
    double gameResult;


    @Override
    public boolean isPlayable(Player player, double bet) {
        boolean result = true;
        if (player.getBalance() < bet || bet < SLOT_MINIMUM){
            result = false;
        }
        //Taking out the bet
        player.setBalance(player.getBalance() - bet);
        return result;
    }

    @Override
    public void play(Player player, Dealer dealer, double bet) {
        this.player = player;
        this.dealer = dealer;
        this.bet = bet;

        String[] result = new String[] {reel1[random1], reel2[random2], reel3[random3]};
        System.out.println("Your spin result is: " + Arrays.toString(result));

        if (result[0].equals(result[1]) && result[1].equals(result[2]) && result[0].equals("BAR") ){
            gameResult = bet * 60;
        }
        else if(result[0].equals(result[1]) && result[1].equals(result[2]) && result[0].equals("SEVEN") ){
            gameResult = bet * 40;
        }
        else if(result[0].equals(result[1]) && result[1].equals(result[2]) && result[0].equals("Cherry") ){
            gameResult = bet * 20;
        }
        else if(result[0].equals(result[1]) && result[1].equals(result[2]) ){
            gameResult = bet * 10;
        }
        else if((result[0].equals(result[1]) || result[1].equals(result[2]) || result[0].equals(result[2])) &&
                (result[0].equals("Cherry") || result[1].equals("Cherry") || result[0].equals("Cherry"))){
            gameResult = bet * 3;
        }
        else if(result[0].equals("Cherry") || result[1].equals("Cherry") || result[2].equals("Cherry")){
            gameResult = bet * 1;
        }

        gameResult = -bet;

        if(gameResult > 0) {
            System.out.println("Congratulations, you won:  " + gameResult + " dollars");
        }else{
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


    String[] reel1 = {"BAR", "SEVEN", "SEVEN", "SEVEN", "Cherry", "Cherry", "Cherry", "Cherry",
            "Orange", "Orange", "Orange", "Orange", "Orange",
            "Banana", "Banana","Banana","Banana","Banana",
            "Lemon", "Lemon", "Lemon", "Lemon", "Lemon"};
    String[] reel2 = {"BAR", "SEVEN", "Cherry", "Cherry", "Cherry",
            "Orange", "Orange", "Orange", "Orange", "Orange", "Orange",
            "Banana", "Banana","Banana","Banana","Banana", "Banana",
            "Lemon", "Lemon", "Lemon", "Lemon", "Lemon",  "Lemon"};
    String[] reel3 = {"BAR", "SEVEN", "Cherry", "Cherry", "Cherry",
            "Orange", "Orange", "Orange", "Orange", "Orange", "Orange",
            "Banana", "Banana","Banana","Banana","Banana", "Banana",
            "Lemon", "Lemon", "Lemon", "Lemon", "Lemon",  "Lemon"};

    int random1 = (int)Math.random()*23;
    int random2 = (int)Math.random()*23;
    int random3 = (int)Math.random()*23;



}