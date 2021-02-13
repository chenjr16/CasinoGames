package com.casino.games.cards.baccarat;

import com.casino.games.CasinoGames;

import com.casino.games.cards.baccarat.deck.Card;
import com.casino.games.cards.baccarat.deck.Cards;
import static com.casino.games.cards.baccarat.utils.Pipe.apply;
import com.casino.player.Dealer;
import com.casino.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Baccarat extends CasinoGames {
    private Player player;
    private Dealer dealer;
    private double bet;
    private final List<Card> deckOfCards = Cards.getDeck();
    private final Map<String, Response<?>> responseMap = new HashMap<>();
    private final Map<String, Integer> resultMap = new HashMap<>();
    private boolean isPlayerFrozen;
    private boolean isBankerFrozen;
    private Play winner;
    private Scanner scanner;

    // Business methods

    public void playBaccarat() {

        // Get player bets
        apply(responseMap)
                .pipe(this::getPlay)
                .pipe(this::getPlayBet)
                .pipe(this::getSidePlay)
                .pipe(this::getSidePlayBet);

        // play a round
        /*
         * 1. Give two cards to the PLAYER(not the console user).
         * If the total is 8 or 9, change state to frozen.
         * If the total is 8 or 9, calculate the total. Any total over 9 is
         * calculated as total = total % 9 - 1.
         * Do the same for the DEALER(not the dealer user).
         * This is round 1. If the player total is <= 5, then the player gets
         * another card, else they become frozen. If player was NOT dealt a third card
         * then apply the same rules to the banker. Game ends.
         * If the player WAS dealt a third card, then
         */

//        apply(resultMap)
//                .pipe(this::startBaccarat)
//                .pipe(this::drawTwoFor, Play.PLAYER)
//                .pipe(this::drawTwoFor, Play.BANKER)
//                .pipe(this::playerDrawIfEligible)
//                .pipe(this::bankerDrawIfEligible)
//                .pipe(this::determineWinner)
//                .pipe(this::dishOutWinnings)
    }


    private Map<String, Integer> startBaccarat(Map<String, Integer> resultMap) {
        System.out.println("Game is starting. No more bets please.");
        return resultMap;
    }


    Map<String, Integer> drawTwoFor(Map<String, Integer> resultMap, Play play) {
        Card card1 = getDeckOfCards().remove(0);
        Card card2 = getDeckOfCards().remove(0);
        int total = card1.getRankValue() + card2.getRankValue();
        if(total > 9) {
            total %= 10;
        }
        setFrozen(play, total);
        insertTotals(resultMap, play, total);
        return resultMap;
    }

    private void insertTotals(Map<String, Integer> resultMap, Play play, int total) {
        switch (play) {
            case PLAYER:
                resultMap.put("playerRound1", total);
                resultMap.put("playerTotal", total);
                break;
            case BANKER:
                resultMap.put("bankerRound1", total);
                resultMap.put("bankerTotal", total);
        }
    }

    void setFrozen(Play play, int total) {
        if(total == 8 || total == 9) {
            switch(play) {
                case PLAYER:
                    isPlayerFrozen = true;
                    break;
                case BANKER:
                    isBankerFrozen = true;
                    break;
            }
        }
    }

    Map<String, Response<?>> getPlay(Map<String, Response<?>> map) {
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Play play = Play.valueOf(input);
        map.put("play", new Response<Play>(play));
        return map;
    }

    Map<String, Response<?>> getPlayBet(Map<String, Response<?>> map) {
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        double bet = Double.parseDouble(input);
        map.put("bet", new Response<Double>(bet));
        return map;
    }

    Map<String, Response<?>> getSidePlay(Map<String, Response<?>> map) {
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        SidePlays sidePlay = SidePlays.valueOf(input);

        map.put("sidePlay", new Response<SidePlays>(sidePlay));
        return map;
    }

    Map<String, Response<?>> getSidePlayBet(Map<String, Response<?>> map) {
        scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        double sideBet = Double.parseDouble(input);

        map.put("sideBet", new Response<Double>(sideBet));
        return map;
    }

    // Getters and Setters
    public Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    public Dealer getDealer() {
        return dealer;
    }

    private void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public double getBet() {
        return bet;
    }

    private void setBet(double bet) {
        this.bet = bet;
    }

    public List<Card> getDeckOfCards() {
        return deckOfCards;
    }

    boolean isPlayerFrozen() {
        return isPlayerFrozen;
    }

    boolean isBankerFrozen() {
        return isBankerFrozen;
    }

    // GameInterface overrides

    @Override
    public boolean isPlayable(Player player, double bet) {
        return player.getBalance() >= 10.0;
    }

    @Override
    public void play(Player player, Dealer dealer, double bet) {
        setPlayer(player);
        setDealer(dealer);
        setBet(bet);
    }

    @Override
    public void distributeMoney() {

    }

    @Override
    public void endGame() {

    }

    enum Play {PLAYER, BANKER, TIE}
    enum SidePlays {PAIR, THREE;}
}