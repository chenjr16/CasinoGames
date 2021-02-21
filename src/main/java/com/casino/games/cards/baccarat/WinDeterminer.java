package com.casino.games.cards.baccarat;
import java.util.AbstractMap;
import java.util.Map;

import static com.casino.games.cards.baccarat.Baccarat.WinKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.WinKeys;
import static com.casino.games.cards.baccarat.Baccarat.SidePlay.*;
import static com.casino.games.cards.baccarat.utils.Pipe.apply;

final class WinDeterminer {
    private Database database;


    void start(Map<WinKeys, Boolean> winMap, Database database) {
        setDatabase(database);

        apply(winMap)
                .pipe(this::mapPutAll, playDeterminer(), sidePlayDeterminer())
                .pipe(this::INSERT);
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
        Baccarat.Play playerPlay = getDatabase().getPlay();
        Baccarat.Play winner = getDatabase().getWinner();
        if(playerPlay.equals(winner)) {
            winEntry.setValue(true);
        }
        return winEntry;
    }

    Map.Entry<WinKeys, Boolean> sidePlayDeterminer() {
        Map.Entry<WinKeys, Boolean> winEntry = new AbstractMap.SimpleEntry<>(SIDE_PLAY_RESULT, false);
        Baccarat.SidePlay sidePlay = getDatabase().getSidePlay();
        boolean isPair = getDatabase().getPair();
        if(sidePlay.equals(PAIR) && isPair) {
            winEntry.setValue(true);
        }
        return winEntry;
    }

    private Map<WinKeys, Boolean> INSERT(Map<WinKeys, Boolean> winMap) {
        // TODO: Run validations
        getDatabase().setPlayResult(winMap.get(PLAY_RESULT));
        getDatabase().setSidePlayResult(winMap.get(SIDE_PLAY_RESULT));
        return winMap;
    }

    // Getters and setters

    void setDatabase(Database database) {
        this.database = database;
    }

    Database getDatabase() {
        return this.database;
    }

}