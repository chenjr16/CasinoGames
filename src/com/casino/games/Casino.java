package com.casino.games;

import com.apps.util.Prompter;
import com.casino.employees.CasinoBoss;
import com.casino.games.cards.baccarat.Baccarat;
import com.casino.employees.Dealer;
import com.casino.player.Player;

import java.util.Scanner;

class Casino {

    public void main(String[] args) {
        Prompter prompter = new Prompter(new Scanner(System.in));
        CasinoBoss casinoBoss = new CasinoBoss(5_000_000.0);
        Player player = new Player();
        Dealer dealer = new Dealer("Casino Dealer");
        String name = prompter.prompt("Please enter your name: ", "/^$|\\s+/", "\nThat is not a valid name!\n");
        player.setName(name);
        String balance = prompter.prompt("Please enter your starting balance: ", "^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$", "\nThat is not a valid balance!\n");
        double doubleBalance = Double.parseDouble(balance);
        player.setBalance(doubleBalance);
        mainScreen(prompter, player, dealer);
    }

    public void mainScreen(Prompter prompter, Player player, Dealer dealer) {
        String gameChoice = prompter.prompt("Please select: [1] Nick's Game , [2] Junuru's Game , [3]Marco's ");
        int gameChoiceInt = Integer.parseInt(gameChoice);
        CasinoGames game;

        switch(gameChoiceInt) {
            case 1:
                game = new Baccarat();
                break;
            case 2:
                game = new Baccarat();
                break;
            default:
                game = new Baccarat();
                break;

        }

        String bet = prompter.prompt("Please enter your bet: ", "\\d+", "\nThat is not a valid bet!\n");
        double betDouble = Double.parseDouble(bet);
        playValidate(prompter, player, game, betDouble, dealer);
    }

    private void playValidate(Prompter prompter, Player player, CasinoGames game, double betDouble, Dealer dealer) {
        if(game.isPlayable(player, betDouble, prompter)) {
            game.play(player, dealer, betDouble);
        } else {
            System.out.println("You can't play that game. You don't have enough money.");
            String choice = prompter.prompt("Would you like to [1] raise your balance or [2] change your bet [3] change the game?");
            switch(choice) {
                case "1":
                    String newBalance = prompter.prompt("How much would you like your new balance to be?");
                    double newBalanceDouble = Double.parseDouble(newBalance);
                    player.setBalance(newBalanceDouble);
                    playValidate(prompter, player, game, betDouble, dealer);
                    break;
                case "2":
                    String newBet = prompter.prompt("How much would you like your new balance to be?");
                    betDouble = Double.parseDouble(newBet);
                    playValidate(prompter, player, game, betDouble, dealer);
                    break;
                case "3":
                    mainScreen(prompter, player, dealer);
                    break;
            }
        }
    }


}