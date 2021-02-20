package com.apps.util;

import java.awt.SplashScreen;
import java.net.URL;

/**
 * Interface that defines the display of one or more <i>splash screens</i> while the application launches.
 * It includes two {@code default} methods named {@code welcome()} that implement this functionality,
 * along with an abstract {@link #start()} method, which must be implemented to kick off the application.
 *
 * <p><b>Intended Use:</b></p>
 * <p>
 * Write a main-class that implements this interface, and override the {@link #start()} method
 * to kick off your application.  The {@code main()} method should do nothing more than instantiate
 * your {@code SplashApp} implementation class, optionally call {@link #welcome(String...)},
 * and then {@link #start()}.
 * </p>
 *
 * <p>
 * <b>NOTE:</b> in order to use the built-in splash mechanism of the JVM, an initial splash image
 * <u>must</u> be specified as part of the command-line launch.  See example below.
 * Subsequent images can be specified by calling one of the {@link #welcome(String...)} methods.
 * </p>
 *
 * <p>
 * Below, note the relative path to the image file, which means the splash image(s) are included
 * with the application - in this example, in an <i>images</i> directory.  Recommended approach.
 * </p>
 * <code>$ java -splash:images/java.png com.games.hangman.HangmanApp</code>
 *
 * <pre>
 * <code>
 *     package com.games.hangman;
 *
 *     import com.apps.util.SplashApp;
 *
 *     class HangmanApp <b>implements SplashApp</b> {
 *
 *         // @Override
 *         public void start() {
 *             HangmanGame game = new HangmanGame();
 *             game.initialize();
 *         }
 *
 *         public static void main(String[] args) {
 *             HangmanApp app = new HangmanApp();
 *             app.welcome("images/hangman.jpg", "images/credits.jpg");
 *             app.start();
 *         }
 *     }
 * </code>
 * </pre>
 *
 * @author Jay Rostosky
 * @version 1.0
 */
public interface SplashApp {
    public static final long DEFAULT_PAUSE = 2000;

    /**
     * Application main-class overrides this method to start the system.  See example above.
     */
    public abstract void start();

    /**
     * Iterates through {@code imageFiles} splash images, advancing every {@code DEFAULT_PAUSE} millis.
     * @param imageFiles splash images.
     * @throws IllegalArgumentException wraps any exception thrown in this method, e.g., a
     * {@code FileNotFoundException} if {@code imagesFiles} cannot be located, or if the initial
     * splash image is specified incorrectly on the command line (see {@code -splash} above).
     */
    default void welcome(String... imageFiles) throws IllegalArgumentException {
        welcome(DEFAULT_PAUSE, imageFiles);
    }

    /**
     * Iterates through {@code imageFiles} splash images, advancing every {@code pause} millis.
     * @param pause interval between splash images.
     * @param imageFiles splash images.
     * @throws IllegalArgumentException wraps any exception thrown in this method, e.g., a
     * {@code FileNotFoundException} if {@code imagesFiles} cannot be located, or if the initial
     * splash image is specified incorrectly on the command line (see {@code -splash} above).
     */
    default void welcome(long pause, String... imageFiles) throws IllegalArgumentException {
        SplashScreen splash = SplashScreen.getSplashScreen();

        try {
            Thread.sleep(2000);
            for (String imageFile : imageFiles) {
                URL imageURL = new URL("file:./" + imageFile);
                splash.setImageURL(imageURL);
                Thread.sleep(pause);
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Error initializing application",e);
        }
        splash.close();
    }
}