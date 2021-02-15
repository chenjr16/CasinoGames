package com.casino.games.cards.baccarat;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ResponsePipelineTest {

    ResponsePipeline responsePipeline;
    Map<String, ResponsePipeline.Response<?>> responseMap;

    @Before
    public void setUp() {
        responsePipeline = new ResponsePipeline();
        responseMap = new HashMap<>();
    }

    @Test
    public void testGetPlay_shouldReturnResponseMapWithUserInput() {
        Baccarat.Play play = Baccarat.Play.PLAYER;
        String playString = "PLAYER";

        InputStream in = new ByteArrayInputStream(playString.getBytes());
        System.setIn(in);
        responsePipeline.getPlay(responseMap);

        assertEquals(play, responseMap.get("play").getResponse());
    }

    @Test
    public void testGetPlayBet_shouldReturnResponseMapWithUserInput() {
        double bet = 2500.0;
        String betString = "2500.0";
        InputStream in = new ByteArrayInputStream(betString.getBytes());
        System.setIn(in);
        responsePipeline.getPlayBet(responseMap);
        Double data = (Double) responseMap.get("bet").getResponse();

        assertEquals(bet, data, .001);
    }

    @Test
    public void testGetSidePlay_shouldReturnResponseMapWithUserInput() {
        Baccarat.SidePlay sidePlay = Baccarat.SidePlay.PAIR;
        String sidePlayString = "PAIR";

        InputStream in = new ByteArrayInputStream(sidePlayString.getBytes());
        System.setIn(in);

        responsePipeline.getSidePlay(responseMap);

        assertEquals(sidePlay, responseMap.get("sidePlay").getResponse());
    }

    @Test
    public void testGetSidePlayBet_shouldReturnResponseMapWithUserInput() {
        double sideBet = 2500.0;
        String sideBetString = "2500.0";

        InputStream in = new ByteArrayInputStream(sideBetString.getBytes());
        System.setIn(in);

        responsePipeline.getSidePlayBet(responseMap);

        Double data = (Double) responseMap.get("sideBet").getResponse();

        assertEquals(sideBet, data, .001);
    }

}