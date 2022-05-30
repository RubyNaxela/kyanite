package com.rubynaxela.kyanite.system;

import com.rubynaxela.kyanite.core.system.Time;

/**
 * Provides functionality for time measurement.
 */
public final class Clock {

    private final com.rubynaxela.kyanite.core.system.Clock clock;
    private boolean started;

    /**
     * Constructs a clock.
     *
     * @param started whether this clock is automatically started by the constructor
     *                (if {@code false}, requires calling {@link Clock#start} to start it)
     */
    public Clock(boolean started) {
        clock = new com.rubynaxela.kyanite.core.system.Clock();
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
            clock.restart();
            started = true;
        } else throw new IllegalStateException("The clock has been already started");
    }

    /**
     * Starts the clock if it hasn't been already started.
     */
    public void tryStart() {
        if (!started) {
            clock.restart();
            started = true;
        }
    }

    /**
     * Gets the elapsed time since the clock was created or last restarted.
     *
     * @return the elapsed time since the clock was created or last restarted.
     */
    public Time getTime() {
        if (started) return clock.getElapsedTime();
        else throw new IllegalStateException("The clock has not been started");
    }

    /**
     * Yields the elapsed time and restarts the clock.
     *
     * @return the elapsed time since the clock was created or last restarted.
     */
    public Time restart() {
        if (started) {
            return clock.restart();
        } else throw new IllegalStateException("The clock has not been started");
    }
}
