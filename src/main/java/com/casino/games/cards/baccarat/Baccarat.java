package com.casino.games.cards.baccarat;

import com.casino.games.CasinoGames;

import com.casino.games.Playable;
import com.casino.player.Dealer;
import com.casino.player.Player;
import com.casino.games.cards.baccarat.ResponsePipeline.Response;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.*;
import com.casino.games.cards.baccarat.BaccaratDealer.Result;

import java.util.HashMap;
import java.util.Map;


public final class Baccarat extends CasinoGames {
    private final static double BET_MINIMUM = 10.0;
    private final Map<ResponseKeys, Response<?>> responseMap = new HashMap<>();
    private final Map<ResultKeys, Result<?>> resultMap = new HashMap<>();
    private final ResponsePipeline responsePipeline = new ResponsePipeline();
    private final BaccaratDealer baccaratDealer = new BaccaratDealer();
    private Player player;
    private Dealer dealer;
    private double bet;
    private boolean didUserWinPlay;
    private boolean didUserWinSidePlay;
    private double playWinnings;
    private double sidePlayWinnings;
    private double totalWinnings;

    public Baccarat() {}

    // Business methods

    void playBaccarat() {
        getResponsePipeline().start(getResponseMap());
        getBaccaratDealer().start(getResultMap());
        dishOutWinnings(getResponseMap(), getResultMap());
        resetAndRestart();
    }

    void dishOutWinnings(Map<ResponseKeys, Response<?>> responseMap, Map<ResultKeys, Result<?>> resultMap) {
        // Get all the user response data
        Play playerPlay = (Play) responseMap.get(PLAY).getResponse();
        SidePlay sidePlay = (SidePlay) responseMap.get(SIDE_PLAY).getResponse();

        // Get the result data
        Play winner = (Play) resultMap.get(WINNER).getResult();
        boolean isPair = (boolean) resultMap.get(IS_PAIR).getResult();

        // Main Plays.
        playBetDeterminer(playerPlay, winner);
        if(didUserWinPlay()) {
            System.out.println(getWinText(playWinnings, playerPlay));
        }

        //  Side Plays
        sidePlayBetDeterminer(sidePlay, isPair);
        if(didUserWinSidePlay()) {
            System.out.println(getWinText(sidePlayWinnings, sidePlay));
        }
        System.out.println(roundEndingText());
    }

    // Set the Winners helper for the dishOutWinnings method.

    private String roundEndingText() {
        String result = "";
        if(!didUserWinPlay() && !didUserWinSidePlay()) {
            result = "\nSorry " + getPlayer().getName() + ". You didn't win a thing. " +
                    "Better luck next time.";
        } else {
            result = "\nYou won a total of " + getTotalWinnings() + " playing Baccarat.";
        }
        return result;
    }

    void moneyTransaction(double winnings, boolean result) {
        if(result) {
            addToTotalWinnings(winnings);
        } else {
            subtractFromTotalWinnings(winnings);
        }
        getDealer().moneyTransfer(getPlayer(), result, winnings);
    }

    private void addToTotalWinnings(double amount) {
        totalWinnings += amount;
    }

    private void subtractFromTotalWinnings(double amount) {
        totalWinnings -= amount;
    }

    private String getWinText(double winnings, BetType betType) {
        return "\nCongrats " + getPlayer().getName() + ". You won " + winnings + " on " + betType;
    }

    private void resetAndRestart() {
        resetTotalWinnings();
        setPlayWinnings(0.0);
        setDidWinPlay(false);
        setDidWinSidePlay(false);
        setBet(0.0);
        createResultMap();
        createResponseMap();
        playBaccarat();
    }

    private void createResponseMap() {
        getResponseMap().put(PLAY, new Response<>(0));
        getResponseMap().put(BET, new Response<>(getBet()));
        getResponseMap().put(SIDE_PLAY, new Response<>(SidePlay.NONE));
        getResponseMap().put(SIDE_BET, new Response<>(0.0));
        getResponseMap().put(ResponseKeys.BET_MINIMUM, new Response<>(BET_MINIMUM));
        getResponseMap().put(PLAYER_BALANCE, new Response<>(player.getBalance()));
    }

    private void createResultMap() {
        getResultMap().put(WINNER, new Result<>(0));
        getResultMap().put(PLAYER_TOTAL, new Result<>(0));
        getResultMap().put(PLAYER_ROUND1, new Result<>(0));
        getResultMap().put(BANKER_TOTAL, new Result<>(0));
        getResultMap().put(BANKER_ROUND1, new Result<>(0));
        getResultMap().put(IS_PAIR, new Result<>(false));
    }

    private void playBetDeterminer(Play play, Play winner) {
        int multiplier = play.getMultiplier();
        double bet = (double) getResponseMap().get(BET).getResponse();
        double winnings = multiplier * bet;
        if(play.equals(winner)) {
            setDidWinPlay(true);
            setPlayWinnings(winnings);
            moneyTransaction(winnings, true);
        } else {
            moneyTransaction(bet, false);
        }
    }

    private void sidePlayBetDeterminer(SidePlay sidePlay, boolean isPair) {
        int multiplier = sidePlay.getMultiplier();
        double bet = (double) getResponseMap().get(SIDE_BET).getResponse();
        double winnings = (multiplier * bet);
        if(sidePlay.equals(SidePlay.PAIR) && isPair) {
            setDidWinSidePlay(true);
            setSidePlayWinnings(winnings);
            moneyTransaction(winnings, true);
        } else {
            moneyTransaction(bet, false);
        }
    }

    // Getters and Setters


    private ResponsePipeline getResponsePipeline() {
        return responsePipeline;
    }

    private BaccaratDealer getBaccaratDealer() {
        return baccaratDealer;
    }

    private boolean didUserWinPlay() {
        return didUserWinPlay;
    }

    private boolean didUserWinSidePlay() {
        return didUserWinSidePlay;
    }

    private void resetTotalWinnings() {
        this.totalWinnings = 0.0;
    }

    private double getTotalWinnings() {
        return totalWinnings;
    }

    private void setDidWinPlay(boolean b) {
        this.didUserWinPlay = true;
    }

    private void setDidWinSidePlay(boolean b) {
        this.didUserWinSidePlay = true;
    }

    private void setPlayWinnings(double playWinnings) {
        this.playWinnings = playWinnings;
    }

    private void setSidePlayWinnings(double sidePlayWinnings) {
        this.sidePlayWinnings = sidePlayWinnings;
    }

    private Player getPlayer() {
        return player;
    }

    private void setPlayer(Player player) {
        this.player = player;
    }

    private Dealer getDealer() {
        return dealer;
    }

    private void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    private double getBet() {
        return bet;
    }

    private void setBet(double bet) {
        this.bet = bet;
    }

    Map<ResponseKeys, Response<?>> getResponseMap() {
        return this.responseMap;
    }

    Map<ResultKeys, Result<?>> getResultMap() {
        return this.resultMap;
    }

    // GameInterface overrides

    @Override
    public Playable isPlayable(Player player, double bet) {
        Playable playable;
        if(player.getBalance() >= BET_MINIMUM) {
            setPlayer(player);
            playable = new Playable("Baccarat", "Can play", true, new Baccarat());
        } else {
            playable = new Playable("Baccarat", "Your balance is too low.", false, new Baccarat());
        }
        return playable;
    }

    @Override
    public void play(Player player, double bet, Dealer dealer) {
        setDealer(dealer);
        setPlayer(player);
        setBet(bet);
        createResultMap();
        createResponseMap();
        System.out.println("\nWelcome to Nick's Baccarat.");
        playBaccarat();
    }

    @Override
    public void distributeMoney() {}

    @Override
    public void endGame() {
        System.out.println("Hey get back here!!!");
    }

    interface BetType {}
    enum Play implements BetType {
        PLAYER(2), BANKER(2), TIE(9);
        private final int multiplier;
        Play(int multiplier) {this.multiplier = multiplier;}
        int getMultiplier(){return  multiplier;}
    }
    enum SidePlay implements BetType {
        NONE(1), PAIR(11);
        private final int multiplier;
        SidePlay(int multiplier) {this.multiplier = multiplier;}
        int getMultiplier() {return multiplier;}
    }
    enum ResponseKeys {PLAY, BET, SIDE_PLAY, SIDE_BET, BET_MINIMUM, PLAYER_BALANCE;}
    enum ResultKeys {WINNER, PLAYER_TOTAL, PLAYER_ROUND1, BANKER_TOTAL, BANKER_ROUND1, IS_PAIR,
                        PLAYER_THIRD_CARD, BANKER_THIRD_CARD;}
}