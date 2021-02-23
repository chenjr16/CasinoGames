package com.casino.games.cards.baccarat;

import com.casino.games.Casino;
import com.casino.games.cards.baccarat.deck.Card;

/*
 * Temporary view until front end developed. All Baccarat souts have been extracted to this class
 * to simplify moving to web view when the time comes.
 */
enum View {;

    // Baccarat base class
    static void welcomeScreen() {
        System.out.println("\nWelcome to Nick's Baccarat.");
    }

    static void endGameMessage() {
        System.out.println("Hey get back here!!!");
    }

    static void sidePlayWonMessage(String name, double winnings, Baccarat.SidePlay sidePlay) {
        System.out.println("\nCongrats " + name + ". You won " + winnings + " on " + sidePlay);
    }

    static void sidePlayLostMessage(String name, double sideBet, Baccarat.SidePlay sidePlay) {
        System.out.println("\nSorry " + name + ". You lost " + sideBet + " on " + sidePlay);
    }

    static void playWonMessage(String name, double winnings, Baccarat.Play play) {
        System.out.println("\nCongrats " + name + ". You won " + winnings + " on " + play);
    }

    static void playLostMessage(String name, double playBet, Baccarat.Play play) {
        System.out.println("\nSorry " + name + ". You lost " + playBet + " on " + play);
    }

    static void wonNothing(String name) {
        System.out.println("\nSorry " + name + ". You didn't win a thing. " +
                "Better luck next time.");
    }

    static void wonSomething(String name, double winnings) {
        System.out.println("\nCongrats " + name + ". You won a total of " +
                winnings + " playing Baccarat.");
    }

    static void lostSomething(String name, double winnings) {
        System.out.println("\nSorry " + name + ". You lost " +
               winnings + " playing Baccarat.");
    }

    // BaccaratDealer class

    static void printTotals(Baccarat.Play play, Card card1, Card card2, int total) {
        System.out.println("\nThe " + play + " received a " + card1.getRankValue() + " and a " +
                card2.getRankValue() + " for a total of " + total);
    }
    static void printTotals(Baccarat.Play play, Card thirdCard, int total) {
        System.out.println("\nThe " + play + " received a " + thirdCard.getRankValue() +
                " for a total of " + total);
    }

    static void gameStartMessage() {
        System.out.println("\nStarting game. No more bets please!");
    }

    // ResponsePipeline class

    static void printInvalidBet(double betMinimum, double playerBalance) {
        System.out.println("\nBets must be between: " + betMinimum +
                " and " + playerBalance);
    }

    static String askHowMuchBetOnSidePlay() {
        return Casino.prompt("\nHow much do you want to bet on PAIR play? ", "\\d+", "Not a valid bet.");
    }

    static String askIfWantToPlaySidePlay(int length) {
        return Casino.prompt("\nPlease select a side play. Enter the number.",
                "[0-" + length + "]", "Not a correct choice.");
    }

    static void sidePlayText() {
        System.out.println("\nHere you will select a side play.");
    }

    static String askHowMuchBetOnPlay() {
        return Casino.prompt("\nHow much do you want to bet? ", "\\d+", "Not a valid bet.");
    }

    static String askWhoToPlacePlayBetOn(int length) {
        return Casino.prompt("\nPlease select a play. Enter the number. ","[0-" +
                length + "]", "Not a correct choice.");
    }

    static void playText() {
        System.out.println("\nHere you will select a play.");
    }
}