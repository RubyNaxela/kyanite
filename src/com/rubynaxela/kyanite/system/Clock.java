/*
 * Copyright (c) 2021-2022 Alex Pawelski
 *
 * Licensed under the Silicon License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://rubynaxela.github.io/Silicon-License/plain_text.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

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
