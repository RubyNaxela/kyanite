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
import org.jetbrains.annotations.NotNull;

/**
 * Provides functionality for time measurement.
 */
public final class Clock implements ConstClock {

    private long lastMeasuredTimestamp, pauseTimestamp, measuredTime = 0;
    private boolean started, paused = false;

    /**
     * Constructs a clock.
     *
     * @param started whether this clock is automatically started by the constructor
     *                (if {@code false}, requires calling {@link Clock#start} to start it)
     */
    public Clock(boolean started) {
        lastMeasuredTimestamp = started ? System.nanoTime() : -1;
        pauseTimestamp = -1;
        this.started = started;
    }

    /**
     * Constructs a clock and starts it.
     */
    public Clock() {
        this(true);
    }

    /**
     * Returns {@code true} if this clock has been started. This method returns {@code true} even if this clock is paused.
     *
     * @return {@code true} if this clock has been started, {@code false} otherwise
     * @see #isPaused
     */
    @Override
    public boolean isActive() {
        return started;
    }

    /**
     * Returns {@code true} if this clock is currently paused.
     *
     * @return {@code true} if this clock is currently paused, {@code false} otherwise
     */
    @Override
    public boolean isPaused() {
        return paused;
    }

    /**
     * Gets the total measured time (the total time the clock was active and not paused).
     *
     * @return the total measured time
     */
    @Override
    @NotNull
    public Time getTime() {
        if (started) {
            if (!paused) {
                final long now = System.nanoTime();
                measuredTime += now - lastMeasuredTimestamp;
                lastMeasuredTimestamp = now;
            }
            return Time.us(measuredTime / 1000L);
        } else throw new IllegalStateException("The clock has not been started");
    }

    /**
     * Starts the clock.
     *
     * @throws IllegalStateException if the clock has been already started
     */
    public void start() {
        if (!started) {
            lastMeasuredTimestamp = System.nanoTime();
            started = true;
        } else throw new IllegalStateException("The clock has been already started");
    }

    /**
     * Starts the clock if it hasn't been already started.
     */
    public void tryStart() {
        if (!started) {
            lastMeasuredTimestamp = System.nanoTime();
            started = true;
        }
    }

    /**
     * Pauses the clock.
     *
     * @throws IllegalStateException if the clock is already paused
     */
    public void pause() {
        if (!paused) {
            pauseTimestamp = System.nanoTime();
            measuredTime += pauseTimestamp - lastMeasuredTimestamp;
            paused = true;
        } else throw new IllegalStateException("The clock is already paused");
    }

    /**
     * Resumes the clock.
     *
     * @throws IllegalStateException if the clock is not paused
     */
    public void resume() {
        if (paused) {
            lastMeasuredTimestamp = System.nanoTime();
            paused = false;
        } else throw new IllegalStateException("The clock is not paused");
    }

    /**
     * Yields the total measured time and restarts the clock.
     *
     * @return the total measured time
     */
    public Time restart() {
        if (started) {
            final Time time = getTime();
            lastMeasuredTimestamp = System.nanoTime();
            pauseTimestamp = -1;
            measuredTime = 0;
            return time;
        } else throw new IllegalStateException("The clock has not been started");
    }
}
