package com.apps.util.client;

import com.apps.util.SplashApp;

class ApplicationClient implements SplashApp {

    @Override
    public void start() {
        System.out.println("W E L C O M E  T O  T H E  T E S T  A P P L I C A T I O N");
    }

    public static void main(String[] args) {
        ApplicationClient client = new ApplicationClient();
        client.welcome(1500, "images/hangman.jpg", "images/credits.jpg");
        client.start();
    }
}
