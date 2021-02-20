package com.apps.util;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import static org.junit.Assert.*;

public class PrompterTest {
    private Scanner scanner;
    private Prompter prompter;

    @After
    public void tearDown() {
        scanner.close();
    }

    /*
     * NOTE: Prompter (and associated Scanner) are created in each test method because
     * different response-files are used in them - response-and-retry.txt, responses.txt.
     */

    @Test
    public void prompt_shouldReturnResponse_andShowRetryText_whenValidThenInvalidData() throws IOException {
        String response = null;

        scanner = new Scanner(new File("responses/response-and-retry.txt"));
        prompter = new Prompter(scanner);

        String promptText = "Do you need help? ";
        String pattern = "y|Y|n|N";
        String retryText = "Sorry, you must enter a 'y' or 'n'";
        response = prompter.prompt(promptText, pattern, retryText);
        assertEquals("y", response);

        response = prompter.prompt(promptText, pattern, retryText);
        assertEquals("Y", response);

        response = prompter.prompt(promptText, pattern, retryText);
        assertEquals("n", response);

        response = prompter.prompt(promptText, pattern, retryText);
        assertEquals("N", response);

        // On the 5th time around, 'X' is entered, the error message is displayed,
        // the prompt is shown again, and then 'n' is entered (as verified below).

        response = prompter.prompt(promptText, pattern, retryText);
        assertEquals("n", response);
    }

    @Test(expected=NoSuchElementException.class)
    public void prompt_shouldReturnAnyResponse_whenOnlyPromptTextSupplied() throws IOException {
        String response = null;
        scanner = new Scanner(new File("responses/responses.txt"));
        prompter = new Prompter(scanner);

        response = prompter.prompt("What is your name: ");
        assertEquals("Jay Rostosky", response);

        response = prompter.prompt("What is your age: ");
        assertEquals(54, Integer.parseInt(response));

        // out of data in the file -> throws NoSuchElementException
        prompter.prompt("");
    }

    @Test
    public void info_shouldPrintAndReturn_whenMessageSupplied() throws IOException {
        // Scanner not actually used in this case, but needed to create the Prompter
        scanner = new Scanner(new File("responses/responses.txt"));
        prompter = new Prompter(scanner);

        String message = "Hi there";
        String response = prompter.info(message);
        assertEquals(message, response);
    }
}