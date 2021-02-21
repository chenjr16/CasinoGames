package com.casino.games.cards.baccarat;

import com.casino.games.Casino;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import com.casino.games.cards.baccarat.Baccarat.ResponseKeys;
import static com.casino.games.cards.baccarat.Baccarat.ResponseKeys.*;
import com.casino.games.cards.baccarat.ResponsePipeline.Response;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

public class ResponsePipelineTest {

    ResponsePipeline responsePipeline;
    Map<ResponseKeys, Response<?>> responseMap;
    MockedStatic<Casino> casinoMock;

    @Before
    public void setUp() {
        casinoMock = Mockito.mockStatic(Casino.class);
        responseMap = setUpResponseMap();
        responsePipeline = new ResponsePipeline();
    }

    @After
    public void close() {
        casinoMock.close();
    }

    private Map<ResponseKeys, Response<?>> setUpResponseMap() {
        responseMap = new HashMap<>();
        responseMap.put(PLAY, new ResponsePipeline.Response<>(0));
        responseMap.put(BET, new ResponsePipeline.Response<>(0.0));
        responseMap.put(SIDE_PLAY, new ResponsePipeline.Response<>(Baccarat.SidePlay.NONE));
        responseMap.put(SIDE_BET, new ResponsePipeline.Response<>(0.0));
        responseMap.put(BET_MINIMUM, new ResponsePipeline.Response<>(10.0));
        responseMap.put(PLAYER_BALANCE, new ResponsePipeline.Response<>(50_000.0));
        return responseMap;
    }

    @Test
    public void testGetPlay_shouldReturnResponseMapWithUserInput() {
        Baccarat.Play play = Baccarat.Play.PLAYER;
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(0));

        responsePipeline.getPlay(responseMap);

        assertEquals(play, responseMap.get(PLAY).getResponse());
    }

    @Test
    public void testGetPlayBet_shouldReturnResponseMapWithUserInput() {
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2500.0));
        responsePipeline.getPlayBet(responseMap);
        Double data = (Double) responseMap.get(BET).getResponse();

        assertEquals(2500, data, .001);
    }

    @Test
    public void testGetSidePlay_shouldReturnResponseMapWithUserInput() {
        Baccarat.SidePlay sidePlay = Baccarat.SidePlay.PAIR;
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(1));
        responsePipeline.getSidePlay(responseMap);

        assertEquals(sidePlay, responseMap.get(SIDE_PLAY).getResponse());
    }

    @Test
    public void testGetSidePlayBet_shouldReturnResponseMapWithUserInput() {
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2500.0));

        responseMap.put(SIDE_PLAY, new Response<>(Baccarat.SidePlay.PAIR));
        responsePipeline.getSidePlayBet(responseMap);

        Double data = (Double) responseMap.get(SIDE_BET).getResponse();

        assertEquals(2500.0, data, .001);
    }

}