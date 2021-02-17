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
    private final double betMinimum = 10.0;
    private Player player;
    private Dealer dealer;
    private double bet;
    private final Map<ResponseKeys, Response<?>> responseMap = new HashMap<>();
    private final Map<ResultKeys, Result<?>> resultMap = new HashMap<>();
    private final ResponsePipeline responsePipeline = new ResponsePipeline();
    private final BaccaratDealer baccaratDealer = new BaccaratDealer();
    private boolean didWin;
    private double totalWinnings;

    public Baccarat() {
    }



    // Business methods

    public void playBaccarat() {
        // Give an empty map to the ResponsePipeline and let it fill it up with Responses from the user.
        // Needs access to scanner.

        // {"play" => Banker, "playBet" => 50.0, "sidePlay" => PAIR, "sidePlayBet" => 100.0}
        responsePipeline.start(getResponseMap());
        // Give a template map to the Dealer and let them fill it out with the results. Game logic here.

        //{"playerCard" => 5, "playerTotal" => 8, "bankerCard" => 6, "bankerTotal" => 6,
        //  "playerThirdCard" => 3, "winner" =>  PLAYER}
        baccaratDealer.start(getResultMap());
        // Dish out them winnings. Takes the responseMap and resultMap and compares results.
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


        // Compare and see if correct. For Regular Plays.

        if(playerPlay.equals(winner)) {
            didWin = true;
            int multiplier = playerPlay.getMultiplier();
            double winnings = (multiplier * (double) responseMap.get(BET).getResponse());
            setWinnings(winnings);
            System.out.println(getWinText(winnings, playerPlay));
        } else {
            double currentDealerBalance = getDealer().getBalance();
            getDealer().setBalance(currentDealerBalance + getBet());
        }

        //  Compare and see if correct. For Side Plays.

        if(sidePlay.equals(SidePlay.PAIR) && isPair) {
            didWin = true;
            int multiplier = SidePlay.PAIR.getMultiplier();
            double winnings = (multiplier * (double) responseMap.get(SIDE_BET).getResponse());
            setWinnings(winnings);
            System.out.println(getWinText(winnings, sidePlay));
        }

        if(!didWin) {
            System.out.println("\nSorry " + getPlayer().getName() + ". You didn't win a thing. " +
                                "Better luck next time.");
        } else {
            System.out.println("\nYou won a total of " + totalWinnings + " playing Baccarat.");
        }
    }

    // Set the Winners helper for the dishOutWinnings method.

    void setWinnings(double winnings) {
        totalWinnings += winnings;
        dealer.moneyTransfer(player, true, winnings);
    }

    String getWinText(double winnings, BetType betType) {
        return "\nCongrats " + getPlayer().getName() + ". You won " + winnings + " on " + betType;
    }

    public void resetAndRestart() {
        totalWinnings = 0;
        didWin = false;
        bet = 0.0;
        createResultMap();
        createResponseMap();
        playBaccarat();
    }

    private void createResponseMap() {
        getResponseMap().put(ResponseKeys.PLAY, new Response<>(0));
        getResponseMap().put(ResponseKeys.BET, new Response<>(bet));
        getResponseMap().put(ResponseKeys.SIDE_PLAY, new Response<>(SidePlay.NONE));
        getResponseMap().put(ResponseKeys.SIDE_BET, new Response<>(0.0));
        getResponseMap().put(ResponseKeys.BET_MINIMUM, new Response<>(betMinimum));
        getResponseMap().put(ResponseKeys.PLAYER_BALANCE, new Response<>(player.getBalance()));
    }

    private void createResultMap() {
        getResultMap().put(ResultKeys.WINNER, new Result<>(0));
        getResultMap().put(ResultKeys.PLAYER_TOTAL, new Result<>(0));
        getResultMap().put(ResultKeys.PLAYER_ROUND1, new Result<>(0));
        getResultMap().put(ResultKeys.BANKER_TOTAL, new Result<>(0));
        getResultMap().put(ResultKeys.BANKER_ROUND1, new Result<>(0));
        getResultMap().put(ResultKeys.IS_PAIR, new Result<>(false));
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
        if(player.getBalance() >= betMinimum) {
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
    public void distributeMoney() {

    }

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