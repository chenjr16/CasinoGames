package com.casino.player;

public class Dealer extends Player{

    public Dealer() {
        // non-op
    }
    public Dealer (String name){
        super();
    }

    public Dealer (String name, double balance){
        super();
    }

    public void moneyTransfer(Player player, Boolean win, double money){
        if(win) {
            this.setBalance(this.getBalance() - money);
            player.setBalance(player.getBalance() + money);
        }
    }
}