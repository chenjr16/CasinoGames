package com.casino.player;

public class Player {
    //Attributes
    private String name;
    private double balance;

    //Constructors

    public Player() {
        // non-op
    }

    public Player(String name){
        setName(name);
    }


    public Player(String name, double balance){
        this(name);
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