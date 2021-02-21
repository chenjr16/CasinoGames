package com.casino.games.cards.baccarat;

import com.casino.games.Casino;
import java.util.Map;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.SidePlay.*;
import static com.casino.games.cards.baccarat.utils.Pipe.apply;

final class ResponsePipeline {
    private Database database;

    void start(Map<ResponseKeys, Response<?>> map, Database database) {
        setDatabase(database);
        apply(map)
                .pipe(this::getPlay)
                .pipe(this::getPlayBet)
                .pipe(this::getSidePlay)
                .pipe(this::getSidePlayBet)
                .pipe(this::INSERT);
    }

    Map<ResponseKeys, Response<?>> getPlay(Map<ResponseKeys, Response<?>> map) {
        Baccarat.Play[] listOfPlays = Baccarat.Play.values();
        System.out.println("\nHere you will select a play.");
        for(int i = 0; i < listOfPlays.length; i++) {
            System.out.println(i + ": " + Baccarat.Play.values()[i]);
        }
        String input = Casino.prompt("\nPlease select a play. Enter the number. ","[0-" +
                                        listOfPlays.length + "]", "Not a correct choice.");
        int choice = Integer.parseInt(input);
        Baccarat.Play play = listOfPlays[choice];
        map.put(PLAY, new Response<>(play));
        return map;
    }

    Map<ResponseKeys, Response<?>> getPlayBet(Map<ResponseKeys, Response<?>> map) {
        double currentBet = (double) map.get(BET).getResponse();
        while (isInvalidBet(map, currentBet)) {
            printInvalidBetText(map);
            String input = Casino.prompt("\nHow much do you want to bet? ", "\\d+", "Not a valid bet.");
            currentBet = Double.parseDouble(input);
        }
        map.put(BET, new Response<>(currentBet));
        return map;
    }

    Map<ResponseKeys, Response<?>> getSidePlay(Map<ResponseKeys, Response<?>> map) {
        System.out.println("\nHere you will select a side play.");
        Baccarat.SidePlay[] listOfSidePlays = Baccarat.SidePlay.values();
        for(int i = 0; i < listOfSidePlays.length; i++) {
            System.out.println(i + ": " + listOfSidePlays[i]);
        }
        String input = Casino.prompt("\nPlease select a side play. Enter the number.",
                                        "[0-" + listOfSidePlays.length + "]", "Not a correct choice.");
        int choice = Integer.parseInt(input);
        Baccarat.SidePlay sidePlay = listOfSidePlays[choice];
        map.put(SIDE_PLAY, new Response<>(sidePlay));
        return map;
    }

    Map<ResponseKeys, Response<?>> getSidePlayBet(Map<ResponseKeys, Response<?>> map) {
        double currentSideBet = (double) map.get(SIDE_BET).getResponse();
        if(map.get(SIDE_PLAY).getResponse().equals(NONE)) {
            return map;
        }
        while(isInvalidBet(map, currentSideBet)) {
            printInvalidBetText(map);
            String input = Casino.prompt("\nHow much do you want to bet on PAIR play? ", "\\d+", "Not a valid bet.");
            currentSideBet = Double.parseDouble(input);
        }
        map.put(SIDE_BET, new Response<>(currentSideBet));
        return map;
    }

    private boolean isInvalidBet(Map<ResponseKeys, Response<?>> map, double bet) {
        return !(bet >= (double) map.get(BET_MINIMUM).getResponse()) ||
                !(bet <= (double) map.get(PLAYER_BALANCE).getResponse());
    }

    private void printInvalidBetText(Map<ResponseKeys, Response<?>> map) {
        double betMinimum = (double) map.get(BET_MINIMUM).getResponse();
        double playerBalance = (double) map.get(PLAYER_BALANCE).getResponse();
        System.out.println("\nBets must be between: " + betMinimum +
                " and " + playerBalance);
    }

    private Map<ResponseKeys, Response<?>> INSERT(Map<ResponseKeys, Response<?>> map) {
        // TODO: Run validations
        getDatabase().setPlay((Baccarat.Play) map.get(PLAY).getResponse());
        getDatabase().setBet((double) map.get(BET).getResponse());
        getDatabase().setSidePlay((Baccarat.SidePlay) map.get(SIDE_PLAY).getResponse());
        getDatabase().setSideBet((double) map.get(SIDE_BET).getResponse());
        return map;
    }

    // getters and setters

    private void setDatabase(Database database) {
        this.database = database;
    }

    private Database getDatabase() {
        return this.database;
    }

    // {String, Response<T>}

    static class Response <T> {
        private final T response;

        public Response(T response) {
            this.response = response;
        }

        public T getResponse() {
            return response;
        }

        @Override
        public String toString() {
            return response.toString();
        }
    }
}