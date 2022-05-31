package com.rubynaxela.kyanite.system;

import com.rubynaxela.kyanite.util.Time;

/**
 * Provides functionality for time measurement.
 */
public final class Clock {

    private long timeStarted;
    private boolean started;

    /**
     * Constructs a clock.
     *
     * @param started whether this clock is automatically started by the constructor
     *                (if {@code false}, requires calling {@link Clock#start} to start it)
     */
    public Clock(boolean started) {
        this.timeStarted = System.nanoTime();
        this.started = started;
    }

    /**
     * Constructs a clock and starts it.
     */
    public Clock() {
        this(true);
    }

    /**
     * Starts the clock.
     *
     * @throws IllegalStateException if the clock has been already started
     */
    public void start() {
        if (!started) {
            timeStarted = System.nanoTime();
            started = true;
        } else throw new IllegalStateException("The clock has been already started");
    }

    /**
     * Starts the clock if it hasn't been already started.
     */
    public void tryStart() {
        if (!started) {
            timeStarted = System.nanoTime();
            started = true;
        }
    }

    /**
     * Gets the elapsed time since the clock was created or last restarted.
     *
     * @return the elapsed time since the clock was created or last restarted
     */
    public Time getTime() {
        if (started) return Time.us((System.nanoTime() - timeStarted) / 1000L);
        else throw new IllegalStateException("The clock has not been started");
    }

    /**
     * Yields the elapsed time and restarts the clock.
     *
     * @return the elapsed time since the clock was created or last restarted
     */
    public Time restart() {
        if (started) {
            final Time dt = getTime();
            timeStarted = System.nanoTime();
            return dt;
        } else throw new IllegalStateException("The clock has not been started");
    }

}
