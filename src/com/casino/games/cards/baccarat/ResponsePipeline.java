package com.casino.games.cards.baccarat;

import com.casino.games.Casino;

import java.util.Map;
import java.util.Scanner;

import static com.casino.games.cards.baccarat.utils.Pipe.apply;

final class ResponsePipeline {

    Casino.CasinoPrompter prompter;

    void start(Map<String, Response<?>> map, Casino.CasinoPrompter prompter) {
        this.prompter = prompter;
        apply(map)
                .pipe(this::getPlay)
                .pipe(this::getPlayBet)
                .pipe(this::getSidePlay)
                .pipe(this::getSidePlayBet)
                .result();
    }

    Map<String, Response<?>> getPlay(Map<String, Response<?>> map) {
        // {"play" => Banker}

        String input = prompter.getPrompt("BANKER OR PLAYER? ","(BANKER|PLAYER)", "Not a correct choice.");

        Baccarat.Play play = Baccarat.Play.valueOf(input);
        map.put("play", new Response<>(play));
        return map;
    }

    Map<String, Response<?>> getPlayBet(Map<String, Response<?>> map) {
        // {"play" => Banker, "playBet" => 50.0}
        String input = prompter.getPrompt("How much do you want to bet? ", "\\d+", "Not a valid bet.");

        double bet = Double.parseDouble(input);
        map.put("bet", new Response<Double>(bet));
        return map;
    }

    Map<String, Response<?>> getSidePlay(Map<String, Response<?>> map) {
        // {"play" => Banker, "playBet" => 50.0, "sidePlay" => PAIR}
        String input = prompter.getPrompt("Bet on Pair? Type PAIR ","(PAIR)", "Not a correct choice.");

        Baccarat.SidePlay sidePlay = Baccarat.SidePlay.valueOf(input);

        map.put("sidePlay", new Response<>(sidePlay));
        return map;
    }

    Map<String, Response<?>> getSidePlayBet(Map<String, Response<?>> map) {
        // {"play" => Banker, "playBet" => 50.0, "sidePlay" => PAIR, "sidePlayBet" => 100.0}
        if(map.get("sidePlay").getResponse().equals(Baccarat.SidePlay.NONE)) {
            return map;
        }
        String input = prompter.getPrompt("How much do you want to bet on PAIR play? ", "\\d+", "Not a valid bet.");

        double sideBet = Double.parseDouble(input);

        map.put("sideBet", new Response<Double>(sideBet));
        return map;
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