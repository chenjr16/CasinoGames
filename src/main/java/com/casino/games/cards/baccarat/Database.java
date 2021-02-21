package com.casino.games.cards.baccarat;
import static com.casino.games.cards.baccarat.Baccarat.Play;
import static com.casino.games.cards.baccarat.Baccarat.SidePlay;

class Database {
    private Play play;
    private double bet;
    private SidePlay sidePlay;
    private double sideBet;
    private Play winner;
    private boolean isPair;
    private boolean playResult;
    private boolean sidePlayResult;

    Play getPlay() {
        return play;
    }

    void setPlay(Play PLAY) {
        this.play = PLAY;
    }

    double getBet() {
        return bet;
    }

    void setBet(double BET) {
        this.bet = BET;
    }

    SidePlay getSidePlay() {
        return sidePlay;
    }

    void setSidePlay(SidePlay sidePlay) {
        this.sidePlay = sidePlay;
    }

    double getSideBet() {
        return sideBet;
    }

    void setSideBet(double sideBet) {
        this.sideBet = sideBet;
    }


    Play getWinner() {
        return winner;
    }

    void setWinner(Play winner) {
        this.winner = winner;
    }

    boolean getPair() {
        return isPair;
    }

    void setPair(boolean pair) {
        this.isPair = pair;
    }


    boolean getPlayResult() {
        return playResult;
    }

    void setPlayResult(boolean playResult) {
        this.playResult = playResult;
    }

    boolean getSidePlayResult() {
        return sidePlayResult;
    }

    void setSidePlayResult(boolean sidePlayResult) {
        this.sidePlayResult = sidePlayResult;
    }

    @Override
    public String toString() {
        return "Database{" +
                "play=" + getPlay() +
                ", bet=" + getBet() +
                ", sidePlay=" + getSidePlay() +
                ", sideBet=" + getSideBet() +
                ", WINNER=" + getWinner() +
                ", IS_PAIR=" + getPair() +
                ", PLAY_RESULT=" + getPlayResult() +
                ", SIDE_PLAY_RESULT=" + getSidePlayResult() +
                '}';
    }
}