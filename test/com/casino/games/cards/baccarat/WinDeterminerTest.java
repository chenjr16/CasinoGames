package com.casino.games.cards.baccarat;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static com.casino.games.cards.baccarat.Baccarat.WinKeys.*;
import com.casino.games.cards.baccarat.Baccarat.WinKeys;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.PLAY;
import com.casino.games.cards.baccarat.ResponsePipeline.Response;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.*;
import static com.casino.games.cards.baccarat.Baccarat.ResultKeys.*;
import com.casino.games.cards.baccarat.BaccaratDealer.Result;
import static com.casino.games.cards.baccarat.Baccarat.SidePlay.*;
import static com.casino.games.cards.baccarat.Baccarat.Play.*;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WinDeterminerTest {
    Map<Baccarat.ResultKeys, BaccaratDealer.Result<?>> resultMap;
    Map<Baccarat.ResponseKeys, ResponsePipeline.Response<?>> responseMap;
    WinDeterminer mockWinDeterminer;
    Map<WinKeys, Boolean> winMap;

    @Before
    public void setUp() {
        mockWinDeterminer = spy(WinDeterminer.class);
        resultMap = new HashMap<>();
        responseMap = new HashMap<>();
        mockWinDeterminer.setResultMap(resultMap);
        mockWinDeterminer.setResponseMap(responseMap);
        winMap = new HashMap<>();
    }

    @Test
    public void testMapPutAll_shouldInsertSingleWinKeyBooleanEntryIntoMap() {
        Map.Entry<WinKeys, Boolean> winEntry = new AbstractMap.SimpleEntry<>(SIDE_PLAY_RESULT, false);
        mockWinDeterminer.mapPutAll(winMap, winEntry);

        assertTrue(winMap.containsKey(SIDE_PLAY_RESULT));
    }

    @Test
    public void testPlayDeterminer_shouldSetEntryToTrue_whenPlayerPlayAndWinnerMatches() {
        responseMap.put(PLAY, new Response<>(PLAYER));
        resultMap.put(WINNER, new Result<>(PLAYER));

        assertTrue(mockWinDeterminer.playDeterminer().getValue());
    }

    @Test
    public void testPlayDeterminer_shouldLeaveEntryAtFalse_whenPlayerPlayAndWinnerDoNotMatch() {
        responseMap.put(PLAY, new Response<>(PLAYER));
        resultMap.put(WINNER, new Result<>(BANKER));

        assertFalse(mockWinDeterminer.playDeterminer().getValue());
    }

    @Test
    public void testSidePlayDeterminer_shouldSetEntryToTrue_whenPlayerSidePlayIsPairAndPairResultIsTrue() {
        responseMap.put(SIDE_PLAY, new Response<>(PAIR));
        resultMap.put(IS_PAIR, new Result<>(true));

        assertTrue(mockWinDeterminer.sidePlayDeterminer().getValue());
    }

    @Test
    public void testSidePlayDeterminer_shouldLeaveEntryAtFalse_whenPlayerSidePlayIsPairAndPairResultIsFalse() {
        responseMap.put(SIDE_PLAY, new Response<>(PAIR));
        resultMap.put(IS_PAIR, new Result<>(false));

        assertFalse(mockWinDeterminer.sidePlayDeterminer().getValue());
    }
}