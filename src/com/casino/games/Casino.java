package com.casino.games;

import com.apps.util.Prompter;
import com.casino.employees.CasinoBoss;
import com.casino.employees.Dealer;
import com.casino.player.Player;

import java.util.*;

public class Casino {
    CasinoPrompter casinoPrompter = new CasinoPrompter();
    Player player;
    Dealer dealer;
    double bet;
    CasinoGames game;

    public Casino() {
        CasinoBoss casinoBoss = new CasinoBoss(5_000_000.0);
    }


    public void start() {
        playerCreation();
        betCreation();
        gameChoiceMenu();
    }

    private void betCreation() {
        String betInput = casinoPrompter.getPrompt("Please enter your bet: ", "\\d+", "\nThat is " +
                                                    "not a valid bet!\n");
        bet = Double.parseDouble(betInput);
    }

    private void playerCreation() {
        player = new Player();
        dealer = new Dealer("Casino Dealer");
        String name = casinoPrompter.getPrompt("Please enter your name: ", "^([ \\u00c0-\\u01ffa-zA-Z'\\-])+$",
                                                "\nThat is not a valid name!\n");
        player.setName(name);
        balanceCreation();
    }

    private void balanceCreation() {
        String balance = casinoPrompter.getPrompt("Please enter your starting balance: ",
                                            "^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$",
                                            "\nThat is not a valid balance!\n");
        double doubleBalance = Double.parseDouble(balance);
        player.setBalance(doubleBalance);
    }

    private void gameChoiceMenu() {
        List<Playable> playableGames = CasinoGames.games(player, bet, casinoPrompter);

        for(int i = 0; i < playableGames.size(); i++) {
            System.out.println(i + ": " + playableGames.get(i).getName());
        }
        int gameChoice = casinoPrompter.gameChoice("Please choose a playable game:", playableGames,
                                                        "Please enter a valid number.");
        Playable playable = playableGames.get(gameChoice);
        if(playable.isPlayable()) {
            game = playable.getInstance();
            game.play(player, bet, dealer, casinoPrompter);
        } else {
            System.out.println(playable.getMessage());
            gameChoiceMenu();
        }
    }

    public void quitGame() {

    }
    public class CasinoPrompter {
        Scanner scanner = new Scanner(System.in);
        Prompter prompter = new Prompter(scanner);

        public String getPrompt(String message, String regex, String errorMessage) {
            String input = prompter.prompt(message, regex, errorMessage);

            switch(input) {
                case "bet":
                    betCreation();
                    break;
                case "balance":
                    balanceCreation();
                    break;
                case "quit":
                    System.exit(0);
                case "setup":
                    playerCreation();
                    break;
                case "select game":
                    gameChoiceMenu();
            }
            return input;
        }

        public int gameChoice(String message, List<Playable> playableGames, String errorMessage) {
            int input = Integer.parseInt(this.getPrompt(message, "^(0|[1-9][0-9]*)$", errorMessage));
            if(input > playableGames.size() - 1 || input < 0) {
                System.out.println(errorMessage);
                this.gameChoice(message, playableGames, errorMessage);
            }
            return input;
        }
    }
}