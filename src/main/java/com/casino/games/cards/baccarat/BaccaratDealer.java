package com.casino.games.cards.baccarat;

import com.casino.games.cards.baccarat.deck.Card;
import com.casino.games.cards.baccarat.deck.Cards;
import static com.casino.games.cards.baccarat.utils.Pipe.apply;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
 * The Dealer class does most of the heavy lifting game logic. It's designed in a way to accept
 * a map and then fill that map with results from the game. The methods are structured in a pipeline
 * format. Must adhere to the pipeline order.
 * drawTwoFor(PLAYER) -> drawTwoFor(BANKER) -> playerDrawIfEligible -> bankerDrawIfEligible -> determineWinner
 */
final class BaccaratDealer {
    private List<Card> deckOfCards = Cards.getDeck();
    private Database database;
    private boolean isPlayerFrozen = false;
    private boolean isBankerFrozen = false;

    BaccaratDealer() {
        Collections.shuffle(getDeckOfCards());
    }

    void start(Map<ResultKeys, Result<?>> resultMap, Database database) {
        setDatabase(database);
        System.out.println("\nStarting game. No more bets please!");
        sleepBetweenDraw(3000);
        apply(resultMap)
                .pipe(this::drawTwoFor, Baccarat.Play.PLAYER)
                .pipe(this::drawTwoFor, Baccarat.Play.BANKER)
                .pipe(this::playerDrawIfEligible)
                .pipe(this::bankerDrawIfEligible)
                .pipe(this::determineWinner)
                .pipe(this::INSERT);
    }

    Map<ResultKeys, Result<?>> drawTwoFor(Map<ResultKeys, Result<?>> resultMap, Baccarat.Play play) {
        Card card1 = getDeckOfCards().remove(0);
        Card card2 = getDeckOfCards().remove(0);
        if(card1.equals(card2)) {
            resultMap.put(IS_PAIR, new Result<>(true));
        }
        int total = card1.getRankValue() + card2.getRankValue();
        if(total > 9) {
            total %= 10;
        }

        printTotals(play, card1, card2, total);
        setFrozen(play, total);
        insertTotals(resultMap, play, total);
        sleepBetweenDraw(2000);
        return resultMap;
    }

    Map<ResultKeys, Result<?>> playerDrawIfEligible(Map<ResultKeys, Result<?>> resultMap) {
        if(isPlayerFrozen()) {
            return resultMap;
        }
        int playerTotal = (int) resultMap.get(PLAYER_TOTAL).getResult();
        if(playerTotal <= 5) {
            drawSingleCard(resultMap, Baccarat.Play.PLAYER);
        }
        return resultMap;
    }

    Map<ResultKeys, Result<?>> bankerDrawIfEligible(Map<ResultKeys, Result<?>> resultMap) {
        if(isBankerFrozen()) {
            return resultMap;
        }
        int bankerTotal = (int) resultMap.get(BANKER_TOTAL).getResult();

        if(resultMap.containsKey(PLAYER_THIRD_CARD)) {
            int playerThirdCardTotal = (int) resultMap.get(PLAYER_THIRD_CARD).getResult();
            switch(bankerTotal) {
                case 0: case 1: case 2:
                    drawSingleCard(resultMap, Baccarat.Play.BANKER);
                    break;
                case 3:
                    if(playerThirdCardTotal <= 9 && playerThirdCardTotal != 8) {
                        drawSingleCard(resultMap, Baccarat.Play.BANKER);
                    }
                    break;
                case 4:
                    if(playerThirdCardTotal >= 2 && playerThirdCardTotal <= 7) {
                        drawSingleCard(resultMap, Baccarat.Play.BANKER);
                    }
                    break;
                case 5:
                    if(playerThirdCardTotal >= 4 && playerThirdCardTotal <= 7) {
                        drawSingleCard(resultMap, Baccarat.Play.BANKER);
                    }
                    break;
                case 6:
                    if(playerThirdCardTotal == 6 || playerThirdCardTotal == 7) {
                        drawSingleCard(resultMap, Baccarat.Play.BANKER);
                    }
                    break;
                default:
                    break;
            }
        }
        return resultMap;
    }

    Map<ResultKeys, Result<?>> determineWinner(Map<ResultKeys, Result<?>> resultMap) {
        int playerTotal = (int) resultMap.get(PLAYER_TOTAL).getResult();
        int bankerTotal = (int) resultMap.get(BANKER_TOTAL).getResult();
        if(playerTotal == bankerTotal) {
            resultMap.put(WINNER, new Result<>(Baccarat.Play.TIE));
        } else if(playerTotal > bankerTotal) {
            resultMap.put(WINNER, new Result<>(Baccarat.Play.PLAYER));
        } else {
            resultMap.put(WINNER, new Result<>(Baccarat.Play.BANKER));
        }
        return resultMap;
    }

    // private helper methods

    private void insertTotals(Map<ResultKeys, Result<?>> resultMap, Baccarat.Play play, int total) {
        switch (play) {
            case PLAYER:
                resultMap.put(PLAYER_ROUND1, new Result<>(total));
                resultMap.put(PLAYER_TOTAL, new Result<>(total));
                break;
            case BANKER:
                resultMap.put(BANKER_ROUND1, new Result<>(total));
                resultMap.put(BANKER_TOTAL, new Result<>(total));
        }
    }

    void setFrozen(Baccarat.Play play, int total) {
        if(total == 8 || total == 9) {
            switch(play) {
                case PLAYER:
                    setPlayerFrozen();
                    break;
                case BANKER:
                    setBankerFrozen();
                    break;
            }
        }
    }

    private void drawSingleCard(Map<ResultKeys, Result<?>> resultMap, Baccarat.Play play) {
        ResultKeys bankerOrPlayerCard = PLAYER_THIRD_CARD;
        ResultKeys bankerOrPlayerTotal = PLAYER_TOTAL;
        if (play == Baccarat.Play.BANKER) {
            bankerOrPlayerCard = BANKER_THIRD_CARD;
            bankerOrPlayerTotal = BANKER_TOTAL;
        }
        int prevTotal = (int) resultMap.get(bankerOrPlayerTotal).getResult();
        Card thirdCard = getDeckOfCards().remove(0);
        int thirdCardTotal = thirdCard.getRankValue();
        resultMap.put(bankerOrPlayerCard, new Result<>(thirdCardTotal));
        int newTotal = prevTotal + thirdCardTotal;
        if(newTotal > 9) {
            newTotal %= 10;
        }
        printTotals(play, thirdCard, newTotal);
        resultMap.put(bankerOrPlayerTotal, new Result<>(newTotal));
        sleepBetweenDraw(2000);
    }

    private void printTotals(Baccarat.Play play, Card thirdCard, int total) {
        System.out.println("\nThe " + play + " received a " + thirdCard.getRankValue() +
                    " for a total of " + total);
    }

    private void printTotals(Baccarat.Play play, Card card1, Card card2, int total) {
        System.out.println("\nThe " + play + " received a " + card1.getRankValue() + " and a " +
                card2.getRankValue() + " for a total of " + total);
    }

    void sleepBetweenDraw(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Map<ResultKeys, Result<?>> INSERT(Map<ResultKeys, Result<?>> resultMap) {
        // TODO: run validations
        getDatabase().setWinner((Baccarat.Play) resultMap.get(WINNER).getResult());
        getDatabase().setPair((boolean) resultMap.get(IS_PAIR).getResult());
        return resultMap;
    }

    // Getters and Setters

    private List<Card> getDeckOfCards() {
        return deckOfCards;
    }

    boolean isPlayerFrozen() {
        return isPlayerFrozen;
    }

    boolean isBankerFrozen() {
        return isBankerFrozen;
    }

    void setPlayerFrozen() {
        isPlayerFrozen = true;
    }

    void setBankerFrozen() {
        isBankerFrozen = true;
    }

    void replaceDeckOfCards(List<Card> cards) {
        this.deckOfCards = cards;
    }

    private void setDatabase(Database database) {
        this.database = database;
    }
    private Database getDatabase() {
        return this.database;
    }


    static class Result<T> {
        private final T result;

        public Result(T result) {
            this.result = result;
        }

        public T getResult() {
            return result;
        }

        @Override
        public String toString() {
            return result.toString();
        }
    }
}