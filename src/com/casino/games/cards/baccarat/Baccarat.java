package com.casino.games.cards.baccarat;

import com.casino.games.CasinoGames;
import com.casino.player.Dealer;
import com.casino.player.Player;
import com.casino.games.cards.baccarat.ResponsePipeline.Response;
import com.casino.games.cards.baccarat.BaccaratDealer.Result;
import java.util.*;

public final class Baccarat extends CasinoGames {
    private Player player;
    private Dealer dealer;
    private double bet;
    private final Map<String, Response<?>> responseMap = new HashMap<>();
    private final Map<String, Result<?>> resultMap = new HashMap<>();
    private final ResponsePipeline responsePipeline = new ResponsePipeline();
    private final BaccaratDealer baccaratDealer = new BaccaratDealer();

    public Baccarat() {
        createResultMap();
        createResponseMap();
    }

    // Business methods

    public void playBaccarat() {
        // Give an empty map to the ResponsePipeline and let it fill it up with Responses from the user.
        // Needs access to scanner.
        responsePipeline.start(getResponseMap());
        // Give a template map to the Dealer and let them fill it out with the results. Game logic here.
        baccaratDealer.start(getResultMap());
        // Dish out them winnings. Takes the responseMap and resultMap and compares results.
        dishOutWinnings(getResponseMap(), getResultMap());
    }

    void dishOutWinnings(Map<String, Response<?>> responseMap, Map<String, Result<?>> resultMap) {
        // Get all the user response data
        Play playerPlay = (Play) responseMap.get("play").getResponse();
        SidePlay sidePlay = (SidePlay) responseMap.get("sidePlay").getResponse();

        // Get the result data

        Play winner = (Play) resultMap.get("winner").getResult();
        boolean isPair = (boolean) resultMap.get("isPair").getResult();


        // Compare and see if correct. For Regular Plays.

        if(playerPlay.equals(winner)) {
            int multiplier = playerPlay.getMultiplier();
            setWinnings(multiplier);
        } else {
            double currentDealerBalance = getDealer().getBalance();
            getDealer().setBalance(currentDealerBalance + getBet());
        }

        //  Compare and see if correct. For Side Plays.

        if(sidePlay.equals(SidePlay.PAIR) && isPair) {
            int multiplier = SidePlay.PAIR.getMultiplier();
            setWinnings(multiplier);
        }
    }

    // Set the Winners helper for the dishOutWinnings method.

    void setWinnings(int multiplier) {
        double currentPlayerBalance = getPlayer().getBalance();
        double currentDealerBalance = getDealer().getBalance();
        double winnings = (getBet() * multiplier);
        getPlayer().setBalance(currentPlayerBalance + winnings);
        getDealer().setBalance(currentDealerBalance - winnings);
    }

    private void createResponseMap() {
        getResponseMap().put("play", new Response<>(0));
        getResponseMap().put("bet", new Response<>(0));
        getResponseMap().put("sidePlay", new Response<>(SidePlay.NONE));
        getResponseMap().put("sideBet", new Response<>(0));
    }

    private void createResultMap() {
        getResultMap().put("winner", new Result<>(0));
        getResultMap().put("playerTotal", new Result<>(0));
        getResultMap().put("playerRound1", new Result<>(0));
        getResultMap().put("bankerTotal", new Result<>(0));
        getResultMap().put("bankerRound1", new Result<>(0));
        getResultMap().put("isPair", new Result<>(false));
    }

    // Getters and Setters
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

    Map<String, Response<?>> getResponseMap() {
        return this.responseMap;
    }

    Map<String, Result<?>> getResultMap() {
        return this.resultMap;
    }



    // GameInterface overrides

    @Override
    public boolean isPlayable(Player player, double bet) {
        return player.getBalance() >= 10.0;
    }

    @Override
    public void play(Player player, com.casino.player.Dealer dealer, double bet) {
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

    interface BetType {}
    enum Play implements BetType {
        PLAYER(2), BANKER(2), TIE(9);
        private final int multiplier;
        Play(int multiplier) {this.multiplier = multiplier;}
        int getMultiplier(){return  multiplier;}
    }
    enum SidePlay implements BetType {
        PAIR(11), NONE(1);
        private final int multiplier;
        SidePlay(int multiplier) {this.multiplier = multiplier;}
        int getMultiplier() {return multiplier;}
    }
}