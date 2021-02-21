package com.casino.games.cards.baccarat;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static com.casino.games.cards.baccarat.Baccarat.WinKeys.*;
import com.casino.games.cards.baccarat.Baccarat.WinKeys;
import static com.casino.games.cards.baccarat.Baccarat.SidePlay.*;
import static com.casino.games.cards.baccarat.Baccarat.Play.*;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class WinDeterminerTest {
    WinDeterminer mockWinDeterminer;
    Map<WinKeys, Boolean> winMap;
    Database database;

    @Before
    public void setUp() {
        mockWinDeterminer = spy(WinDeterminer.class);
        database = new Database();
        mockWinDeterminer.setDatabase(database);
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
        database.setPlay(PLAYER);
        database.setWinner(PLAYER);

        assertTrue(mockWinDeterminer.playDeterminer().getValue());
    }

    @Test
    public void testPlayDeterminer_shouldLeaveEntryAtFalse_whenPlayerPlayAndWinnerDoNotMatch() {
        database.setPlay(PLAYER);
        database.setWinner(BANKER);


        assertFalse(mockWinDeterminer.playDeterminer().getValue());
    }

    @Test
    public void testSidePlayDeterminer_shouldSetEntryToTrue_whenPlayerSidePlayIsPairAndPairResultIsTrue() {
        database.setSidePlay(PAIR);
        database.setPair(true);

        assertTrue(mockWinDeterminer.sidePlayDeterminer().getValue());
    }

    @Test
    public void testSidePlayDeterminer_shouldLeaveEntryAtFalse_whenPlayerSidePlayIsPairAndPairResultIsFalse() {
        database.setSidePlay(PAIR);
        database.setPair(false);


        assertFalse(mockWinDeterminer.sidePlayDeterminer().getValue());
    }
}