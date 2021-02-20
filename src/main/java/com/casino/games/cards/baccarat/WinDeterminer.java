package com.casino.games.cards.baccarat;
import java.util.AbstractMap;
import java.util.Map;

import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.PLAY;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.SIDE_PLAY;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.IS_PAIR;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.WINNER;
import static com.casino.games.cards.baccarat.Baccarat.WinKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.WinKeys;
import static com.casino.games.cards.baccarat.Baccarat.SidePlay.*;
import com.casino.games.cards.baccarat.ResponsePipeline.Response;
import static com.casino.games.cards.baccarat.BaccaratDealer.Result;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys;
import static com.casino.games.cards.baccarat.utils.Pipe.apply;

final class WinDeterminer {
    private Map<ResultKeys, Result<?>> resultMap;
    private Map<ResponseKeys, Response<?>> responseMap;

    void start(Map<WinKeys, Boolean> winMap, Map<ResponseKeys, Response<?>> responseMap,
                                             Map<ResultKeys, Result<?>> resultMap) {
        setResultMap(resultMap);
        setResponseMap(responseMap);

        apply(winMap)
                .pipe(this::mapPutAll, playDeterminer(), sidePlayDeterminer());
    }

    @SafeVarargs
    final Map<WinKeys, Boolean> mapPutAll(Map<WinKeys, Boolean> winMap, Map.Entry<WinKeys, Boolean>... entries) {
        for (Map.Entry<WinKeys, Boolean> entry : entries) {
            WinKeys key = entry.getKey();
            Boolean result = entry.getValue();
            winMap.put(key, result);
        }
        return winMap;
    }

    Map.Entry<WinKeys, Boolean> playDeterminer() {
        Map.Entry<WinKeys, Boolean> winEntry = new AbstractMap.SimpleEntry<>(PLAY_RESULT, false);
        Baccarat.Play playerPlay = (Baccarat.Play) getResponseMap().get(PLAY).getResponse();
        Baccarat.Play winner = (Baccarat.Play) getResultMap().get(WINNER).getResult();
        if(playerPlay.equals(winner)) {
            winEntry.setValue(true);
        }
        return winEntry;
    }

    Map.Entry<WinKeys, Boolean> sidePlayDeterminer() {
        Map.Entry<WinKeys, Boolean> winEntry = new AbstractMap.SimpleEntry<>(SIDE_PLAY_RESULT, false);
        Baccarat.SidePlay sidePlay = (Baccarat.SidePlay) responseMap.get(SIDE_PLAY).getResponse();
        boolean isPair = (boolean) resultMap.get(IS_PAIR).getResult();
        if(sidePlay.equals(PAIR) && isPair) {
            winEntry.setValue(true);
        }
        return winEntry;
    }

    // Getters and setters

    Map<ResultKeys, Result<?>> getResultMap() {
        return resultMap;
    }

    void setResultMap(Map<ResultKeys, Result<?>> resultMap) {
        this.resultMap = resultMap;
    }

    Map<ResponseKeys, Response<?>> getResponseMap() {
        return responseMap;
    }

    void setResponseMap(Map<ResponseKeys, Response<?>> responseMap) {
        this.responseMap = responseMap;
    }
}