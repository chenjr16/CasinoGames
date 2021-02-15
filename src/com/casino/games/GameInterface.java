package com.casino.games;

import com.apps.util.Prompter;
import com.casino.employees.Dealer;
import com.casino.player.Player;

public interface GameInterface {

    public boolean isPlayable(Player player, double bet, Prompter prompter);

    public void play(Player player, Dealer dealer, double bet);

    public void distributeMoney();

    public void endGame();
}
