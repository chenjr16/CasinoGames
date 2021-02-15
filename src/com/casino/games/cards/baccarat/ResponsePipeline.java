package com.casino.games.cards.baccarat;

import java.util.Map;
import java.util.Scanner;

import static com.casino.games.cards.baccarat.utils.Pipe.apply;

final class ResponsePipeline {

    void start(Map<String, Response<?>> map) {
        apply(map)
                .pipe(this::getPlay)
                .pipe(this::getPlayBet)
                .pipe(this::getSidePlay)
                .pipe(this::getSidePlayBet)
                .result();
    }

    Map<String, Response<?>> getPlay(Map<String, Response<?>> map) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Baccarat.Play play = Baccarat.Play.valueOf(input);
        map.put("play", new Response<>(play));
        return map;
    }

    Map<String, Response<?>> getPlayBet(Map<String, Response<?>> map) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        double bet = Double.parseDouble(input);
        map.put("bet", new Response<Double>(bet));
        return map;
    }

    Map<String, Response<?>> getSidePlay(Map<String, Response<?>> map) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        Baccarat.SidePlay sidePlay = Baccarat.SidePlay.valueOf(input);

        map.put("sidePlay", new Response<>(sidePlay));
        return map;
    }

    Map<String, Response<?>> getSidePlayBet(Map<String, Response<?>> map) {
        if(map.get("sidePlay").getResponse().equals(Baccarat.SidePlay.NONE)) {
            return map;
        }
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        double sideBet = Double.parseDouble(input);

        map.put("sideBet", new Response<Double>(sideBet));
        return map;
    }

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