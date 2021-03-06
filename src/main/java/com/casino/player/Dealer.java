package com.casino.player;

public class Dealer extends Player{

    public Dealer() {
        // non-op
    }
    public Dealer (String name){
        super(name);
    }

    public Dealer (String name, double balance){
        super(name, balance);
    }

    public void moneyTransfer(Player player, Boolean win, double money){
        if(win) {
            this.setBalance(this.getBalance() - money);
            player.setBalance(player.getBalance() + money);
        }else {
            player.setBalance(player.getBalance() - money);
            this.setBalance(this.getBalance() + money);
        }
    }
}