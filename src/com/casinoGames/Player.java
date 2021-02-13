package com.casinoGames;

class Player {
    //Attributes
    String name;
    double balance;

    //Constructors
    public Player (String name, double balance){
        setName(name);
        setBalance(balance);
    }


    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}