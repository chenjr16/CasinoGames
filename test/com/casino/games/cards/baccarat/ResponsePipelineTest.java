package com.casino.games.cards.baccarat;

import com.apps.util.Prompter;
import com.casino.games.Casino;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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

    @Before
    public void setUp() {
        responseMap = setUpResponseMap();
        responsePipeline = new ResponsePipeline();
    }

    private Map<ResponseKeys, Response<?>> setUpResponseMap() {
        responseMap = new HashMap<>();
        responseMap.put(PLAY, new ResponsePipeline.Response<>(0));
        responseMap.put(BET, new ResponsePipeline.Response<>(4000.0));
        responseMap.put(SIDE_PLAY, new ResponsePipeline.Response<>(Baccarat.SidePlay.NONE));
        responseMap.put(SIDE_BET, new ResponsePipeline.Response<>(0.0));
        responseMap.put(BET_MINIMUM, new ResponsePipeline.Response<>(10.0));
        responseMap.put(PLAYER_BALANCE, new ResponsePipeline.Response<>(50_000.0));
        return responseMap;
    }

    @Test
    public void testGetPlay_shouldReturnResponseMapWithUserInput() throws Exception {
        Baccarat.Play play = Baccarat.Play.PLAYER;
        MockedStatic<Casino> casinoMock = Mockito.mockStatic(Casino.class);
        casinoMock.when(() -> Casino.prompt(anyString(), anyString(), anyString())).thenReturn(String.valueOf(2));

        responsePipeline.getPlay(responseMap);

        assertEquals(play, responseMap.get(PLAY).getResponse());
    }

    @Test
    public void testGetPlayBet_shouldReturnResponseMapWithUserInput() {
        double bet = 2500.0;
        String betString = "2500.0";
        InputStream in = new ByteArrayInputStream(betString.getBytes());
        System.setIn(in);
        responsePipeline.getPlayBet(responseMap);
        Double data = (Double) responseMap.get(BET).getResponse();

        assertEquals(bet, data, .001);
    }

    @Test
    public void testGetSidePlay_shouldReturnResponseMapWithUserInput() {
        Baccarat.SidePlay sidePlay = Baccarat.SidePlay.PAIR;
        String sidePlayString = "PAIR";

        InputStream in = new ByteArrayInputStream(sidePlayString.getBytes());
        System.setIn(in);

        responsePipeline.getSidePlay(responseMap);

        assertEquals(sidePlay, responseMap.get(SIDE_PLAY).getResponse());
    }

    @Test
    public void testGetSidePlayBet_shouldReturnResponseMapWithUserInput() {
        responseMap.put(SIDE_PLAY, new ResponsePipeline.Response<>(Baccarat.SidePlay.PAIR));
        double sideBet = 2500.0;
        String sideBetString = "2500.0";

        InputStream in = new ByteArrayInputStream(sideBetString.getBytes());
        System.setIn(in);


        responsePipeline.getSidePlayBet(responseMap);

        Double data = (Double) responseMap.get(SIDE_BET).getResponse();

        assertEquals(sideBet, data, .001);
    }

}