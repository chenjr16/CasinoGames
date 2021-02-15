package com.casino.games.cards.baccarat;

import com.casino.games.cards.baccarat.deck.Card;
import com.casino.games.cards.baccarat.deck.Cards;
import static com.casino.games.cards.baccarat.utils.Pipe.apply;

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
    private boolean isPlayerFrozen = false;
    private boolean isBankerFrozen = false;

    BaccaratDealer() {
        Collections.shuffle(getDeckOfCards());
    }

    void start(Map<String, Result<?>> resultMap) {
        apply(resultMap)
                .pipe(this::drawTwoFor, Baccarat.Play.PLAYER)
                .pipe(this::drawTwoFor, Baccarat.Play.BANKER)
                .pipe(this::playerDrawIfEligible)
                .pipe(this::bankerDrawIfEligible)
                .pipe(this::determineWinner)
                .result();
    }

    Map<String, Result<?>> drawTwoFor(Map<String, Result<?>> resultMap, Baccarat.Play play) {
        Card card1 = getDeckOfCards().remove(0);
        Card card2 = getDeckOfCards().remove(0);
        if(card1.equals(card2)) {
            resultMap.put("isPair", new Result<>(true));
        }
        int total = card1.getRankValue() + card2.getRankValue();
        if(total > 9) {
            total %= 10;
        }
        setFrozen(play, total);
        insertTotals(resultMap, play, total);
        return resultMap;
    }

    Map<String, Result<?>> playerDrawIfEligible(Map<String, Result<?>> resultMap) {
        if(isPlayerFrozen()) {
            return resultMap;
        }
        int playerTotal = (int) resultMap.get("playerTotal").getResult();
        if(playerTotal <= 5) {
            drawSingleCard(resultMap, Baccarat.Play.PLAYER);
        }
        return resultMap;
    }

    Map<String, Result<?>> bankerDrawIfEligible(Map<String, Result<?>> resultMap) {
        if(isBankerFrozen()) {
            return resultMap;
        }
        int bankerTotal = (int) resultMap.get("bankerTotal").getResult();

        if(resultMap.containsKey("playerThirdCard")) {
            int playerThirdCardTotal = (int) resultMap.get("playerThirdCard").getResult();
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

    Map<String, Result<?>> determineWinner(Map<String, Result<?>> resultMap) {
        int playerTotal = (int) resultMap.get("playerTotal").getResult();
        int bankerTotal = (int) resultMap.get("bankerTotal").getResult();
        if(playerTotal == bankerTotal) {
            resultMap.put("winner", new Result<>(Baccarat.Play.TIE));
        } else if(playerTotal > bankerTotal) {
            resultMap.put("winner", new Result<>(Baccarat.Play.PLAYER));
        } else {
            resultMap.put("winner", new Result<>(Baccarat.Play.BANKER));
        }
        return resultMap;
    }

    // private helper methods

    private void insertTotals(Map<String, Result<?>> resultMap, Baccarat.Play play, int total) {
        switch (play) {
            case PLAYER:
                resultMap.put("playerRound1", new Result<>(total));
                resultMap.put("playerTotal", new Result<>(total));
                break;
            case BANKER:
                resultMap.put("bankerRound1", new Result<>(total));
                resultMap.put("bankerTotal", new Result<>(total));
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

    /*
     * Takes in a resultMap and Play type(Dealer or Player). Assigns string variables based on
     * Play type. Draws a third card and places total into the resultMap. Calculates the new
     * total by adding the third card to the previous, places the result in the resultMap.
     */

    private void drawSingleCard(Map<String, Result<?>> resultMap, Baccarat.Play play) {
        String bankerOrPlayerCard = "";
        String bankerOrPlayerTotal = "";
        switch(play) {
            case PLAYER:
                bankerOrPlayerCard = "playerThirdCard";
                bankerOrPlayerTotal = "playerTotal";
                break;
            case BANKER:
                bankerOrPlayerCard = "bankerThirdCard";
                bankerOrPlayerTotal = "bankerTotal";
                break;
        }
        int prevTotal = (int) resultMap.get(bankerOrPlayerTotal).getResult();
        Card thirdCard = getDeckOfCards().remove(0);
        int thirdCardTotal = thirdCard.getRankValue();
        resultMap.put(bankerOrPlayerCard, new Result<>(thirdCardTotal));

        int newTotal = prevTotal + thirdCardTotal;
        if(newTotal > 9) {
            newTotal %= 10;
        }
        resultMap.put(bankerOrPlayerTotal, new Result<>(newTotal));
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