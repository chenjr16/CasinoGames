package com.casino.games.cards.baccarat;

import com.casino.games.CasinoGames;
import com.casino.games.Playable;
import com.casino.player.Dealer;
import com.casino.player.Player;
import com.casino.games.cards.baccarat.ResponsePipeline.Response;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.WinKeys.*;
import com.casino.games.cards.baccarat.BaccaratDealer.Result;
import java.util.HashMap;
import java.util.Map;


public final class Baccarat extends CasinoGames {
    private final static double BET_MINIMUM = 10.0;
    private Database database = new Database();
    private final Map<ResponseKeys, Response<?>> responseMap = new HashMap<>();
    private final Map<ResultKeys, Result<?>> resultMap = new HashMap<>();
    private final Map<WinKeys, Boolean> winMap = new HashMap<>();
    private final ResponsePipeline responsePipeline = new ResponsePipeline();
    private final BaccaratDealer baccaratDealer = new BaccaratDealer();
    private final WinDeterminer winDeterminer = new WinDeterminer();
    private Player player;
    private Dealer dealer;
    private double bet;
    private double totalWinnings;

    public Baccarat() {}

    // Business methods

    void playBaccarat() {
        getResponsePipeline().start(getResponseMap(), getDatabase());
        getBaccaratDealer().start(getResultMap(), getDatabase());
        getWinDeterminer().start(getWinMap(), getDatabase());
        dishOutPlayWinnings();
        dishOutSidePlayWinnings();
        roundEndingText();
        resetAndRestart();
    }

    void dishOutPlayWinnings() {
        Play play = getDatabase().getPlay();
        boolean result = getDatabase().getPlayResult();
        int multiplier = play.getMultiplier();
        double bet = getDatabase().getBet();
        double winnings = multiplier * bet;
        if(result) {
            moneyTransaction(winnings, true);
            View.playWonMessage(getPlayer().getName(), winnings, play);
        } else {
            moneyTransaction(bet, false);
            View.playLostMessage(getPlayer().getName(), bet, play);
        }
    }

    void dishOutSidePlayWinnings() {
        SidePlay sidePlay = getDatabase().getSidePlay();
        boolean result = getDatabase().getSidePlayResult();
        int multiplier = sidePlay.getMultiplier();
        double bet = getDatabase().getSideBet();
        double winnings = multiplier * bet;
        if(result) {
            moneyTransaction(winnings, true);
            View.sidePlayWonMessage(getPlayer().getName(), winnings, sidePlay);
        } else {
            moneyTransaction(bet, false);
            View.sidePlayLostMessage(getPlayer().getName(), bet, sidePlay);
        }
    }

    private void roundEndingText() {
        if(getTotalWinnings() == 0) {
            View.wonNothing(getPlayer().getName());
        } else if(getTotalWinnings() > 0) {
            View.wonSomething(getPlayer().getName(), getTotalWinnings());
        } else if (getTotalWinnings() < 0) {
            View.lostSomething(getPlayer().getName(), getTotalWinnings());
        }
    }

    void moneyTransaction(double winnings, boolean result) {
        if(result) {
            addToTotalWinnings(winnings);
        } else {
            subtractFromTotalWinnings(winnings);
        }
        getDealer().moneyTransfer(getPlayer(), result, winnings);
    }

    private void resetAndRestart() {
        resetTotalWinnings();
        setBet(0.0);
        createResultMap();
        createResponseMap();
        playBaccarat();
    }

    private void createResponseMap() {
        getResponseMap().put(PLAY, new Response<>(Play.PLAYER));
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

    private  void createWinMap() {
        getWinMap().put(PLAY_RESULT, false);
        getWinMap().put(SIDE_PLAY_RESULT, false);
    }

    // Getters and Setters
    private ResponsePipeline getResponsePipeline() {
        return this.responsePipeline;
    }

    private BaccaratDealer getBaccaratDealer() {
        return this.baccaratDealer;
    }

    private WinDeterminer getWinDeterminer() {
        return this.winDeterminer;
    }

    private void resetTotalWinnings() {
        this.totalWinnings = 0.0;
    }

    private double getTotalWinnings() {
        return totalWinnings;
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

    private void addToTotalWinnings(double amount) {
        totalWinnings += amount;
    }

    private void subtractFromTotalWinnings(double amount) {
        totalWinnings -= amount;
    }

    Map<ResponseKeys, Response<?>> getResponseMap() {
        return this.responseMap;
    }

    Map<ResultKeys, Result<?>> getResultMap() {
        return this.resultMap;
    }

    Map<WinKeys, Boolean> getWinMap() {
        return this.winMap;
    }

    private Database getDatabase() {
        return this.database;
    }
    void setDatabase(Database database) {
        this.database = database;
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
        createWinMap();
        View.welcomeScreen();
        playBaccarat();
    }

    @Override
    public void distributeMoney() {}

    @Override
    public void endGame() {
        View.endGameMessage();
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
    enum ResponseKeys {PLAY, BET, SIDE_PLAY, SIDE_BET, BET_MINIMUM, PLAYER_BALANCE}
    enum ResultKeys {WINNER, PLAYER_TOTAL, PLAYER_ROUND1, BANKER_TOTAL, BANKER_ROUND1, IS_PAIR,
                        PLAYER_THIRD_CARD, BANKER_THIRD_CARD}
    enum WinKeys {PLAY_RESULT, SIDE_PLAY_RESULT}
}