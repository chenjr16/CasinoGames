package com.casino.games;

import com.casino.player.Player;

interface GameInterface {

    public Playable isPlayable(Player player, double bet);

    public void distributeMoney();

    public void endGame();
}
