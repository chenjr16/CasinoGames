package com.casino.games;

import com.apps.util.Prompter;
import java.util.Scanner;

public class CasinoPrompter {
    Prompter prompter = new Prompter(new Scanner(System.in));

    String getPrompt(String message, String regex, String errorMessage) {
        return prompter.prompt(message, regex, errorMessage);
    }
}