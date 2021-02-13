package com.casino.games.cards.baccarat;

class Response <T> {
    private T response;

    public Response(T response) {
        this.response = response;
    }

    public T getResponse() {
        return response;
    }

    @Override
    public String toString() {
        return response.toString();
    }
}