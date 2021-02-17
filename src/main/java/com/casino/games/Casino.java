package com.casino.games;


import com.casino.player.Dealer;
import com.casino.player.Player;

import java.util.List;

public class Casino {
    private static Casino me;
    private static final CasinoPrompter casinoPrompter = new CasinoPrompter();
    Player player;
    Dealer dealer;
    double bet;
    CasinoGames game;


    public Casino() {
    }

    public void start(Casino casino) {
        me = casino;
        playerCreation();
        betCreation();
        gameChoiceMenu();
    }

    private void betCreation() {
        String betInput = prompt("Please enter your bet: ", "[0-9]*\\.?[0-9]*", "\nThat is " +
                                                    "not a valid bet!\n");
        bet = Double.parseDouble(betInput);
    }

    private void playerCreation() {
        player = new Player();
        dealer = new Dealer("Casino Dealer", 50_000_000.0);
        String name = prompt("Please enter your name: ", "[a-zA-z]*",
                                                "\nThat is not a valid name!\n");
        player.setName(name);
        balanceCreation();
    }

    private void balanceCreation() {
        String balance = prompt("Please enter your starting balance: ",
                                            "^[0-9]*\\.?[0-9]*",
                                            "\nThat is not a valid balance!\n");
        double doubleBalance = Double.parseDouble(balance);
        player.setBalance(doubleBalance);
    }


    private void gameChoiceMenu() {
        List<Playable> playableGames = CasinoGames.games(player, bet);


        for(int i = 0; i < playableGames.size(); i++) {
            System.out.println(i + ": " + playableGames.get(i).getName());
        }
        int gameChoice = promptGameChoice("Please choose a playable game:", playableGames,
                                                        "Please enter a valid number.");
        Playable playable = playableGames.get(gameChoice);
        if(playable.playableResult()) {
            game = playable.getInstance();
            game.play(player, bet, dealer);
        } else {
            System.out.println(playable.getMessage());
            gameChoiceMenu();
        }
    }

    public static int promptGameChoice(String message, List<Playable> playableGames, String errorMessage) {
        int size =  playableGames.size() - 1;
        String input = prompt(message, "[0-" + size + "]", errorMessage);
        int choice = Integer.parseInt(input);
        if (choice > playableGames.size() - 1 || choice < 0) {
            System.out.println(errorMessage);
            promptGameChoice(message, playableGames, errorMessage);
        }
        return choice;
    }

    public static String prompt(String message, String regex, String errorMessage) {
        String customRegex = "bet|balance|quit|setup|select game|" + regex;
        String input = casinoPrompter.getPrompt(message, customRegex, errorMessage);
        // check for global commands
        switch(input) {
            case "bet":
                me.betCreation();
                break;
            case "balance":
                me.balanceCreation();
                break;
            case "quit":
                me.quitGame();
            case "setup":
                me.playerCreation();
                break;
            case "select game":
                me.gameChoiceMenu();
        }
        return input;
    }

    public void quitGame() {
        System.exit(0);
    }
}