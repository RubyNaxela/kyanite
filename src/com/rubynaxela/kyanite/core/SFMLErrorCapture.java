package com.rubynaxela.kyanite.core;

import java.util.concurrent.Semaphore;

/**
 * Provides methods to capture output to {@code sf::err()}.
 */
@SuppressWarnings("deprecation")
public final class SFMLErrorCapture extends org.jsfml.internal.SFMLErrorCapture {

    private final static Semaphore semaphore = new Semaphore(1);
    private static boolean capturing = false;

    private SFMLErrorCapture() {
    }

    /**
     * Starts capturing all output to {@code sf::err()}.
     * <p/>
     * This method acquires a semaphore and will block if a capture is currently going,
     * therefore the capturing is thread-safe.
     * <p/>
     * A capture must be concluded by calling the {@link #finish()} method.
     */
    public static void start() {
        try {
            semaphore.acquire();
            capturing = true;
            nativeStart();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Finishes capturing all output to {@code sf::err()} and returns what was captured.
     *
     * @return the output that was captured, or {@code null} if capturing was never started using
     * {@link #start()}.
     */
    public static String finish() {
        final String str;
        if (capturing) {
            str = nativeFinish().trim();
            capturing = false;
            semaphore.release();
        } else {
            str = null;
        }

        return str;
    }
}
