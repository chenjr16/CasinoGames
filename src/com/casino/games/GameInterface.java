package com.casino.games;

import com.casino.player.Player;

public interface GameInterface {

    public Playable isPlayable(Player player, double bet);

    public void distributeMoney();

    public void endGame();
}
