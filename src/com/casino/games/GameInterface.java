package com.casino.games;

import com.casino.player.Dealer;
import com.casino.player.Player;

public interface GameInterface {

    public boolean isPlayable(Player player, double bet);

    public void play(Player player, Dealer dealer, double bet);

    public void distributeMoney();

    public void endGame();
}
