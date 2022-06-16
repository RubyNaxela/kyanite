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

package com.rubynaxela.kyanite.util;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents a time period and provides functionality to convert between
 * various time units, as well as arithmetic operations on time intervals.
 */
public final class Time implements Comparable<Time>, Serializable {

    /**
     * A time object that represents a zero time period, i.e. with no duration.
     */
    public static final Time ZERO = new Time(0);
    @Serial
    private static final long serialVersionUID = 7038088548302750096L;
    private final long microseconds;

    private Time(long microseconds) {
        this.microseconds = microseconds;
    }

    /**
     * Creates a new time with the specified value.
     *
     * @param seconds the time value (in seconds)
     * @return a new time with the specified value
     */
    public static Time s(float seconds) {
        return new Time((long) (seconds * 1000000.0f));
    }

    /**
     * Creates a new time with the specified value.
     *
     * @param milliseconds the time value (in milliseconds)
     * @return a new time with the specified value
     */
    public static Time ms(long milliseconds) {
        return new Time(milliseconds * 1000);
    }

    /**
     * Creates a new time with the specified value.
     *
     * @param microseconds the time value (in microseconds)
     * @return a new time with the specified value
     */
    public static Time us(long microseconds) {
        return new Time(microseconds);
    }

    /**
     * Adds two time values.
     *
     * @param t1 the first operand
     * @param t2 the second operand
     * @return a new time, representing the sum of the two times
     */
    public static Time add(@NotNull Time t1, @NotNull Time t2) {
        return new Time(t1.microseconds + t2.microseconds);
    }

    /**
     * Subtracts two time values.
     *
     * @param t1 the first operand
     * @param t2 the second operand
     * @return a new time, representing the difference between the two times
     */
    public static Time sub(@NotNull Time t1, @NotNull Time t2) {
        return new Time(t1.microseconds - t2.microseconds);
    }

    /**
     * Scales a time by multiplying its value by a scalar.
     *
     * @param t the time to scale
     * @param s the scalar
     * @return a new time, representing the given time scaled by the given factor
     */
    public static Time multiply(@NotNull Time t, @NotNull Number s) {
        return new Time((long) (s.doubleValue() * (float) t.microseconds));
    }

    /**
     * Scales a time by dividing it by a scalar.
     *
     * @param t the time to scale
     * @param s the scalar
     * @return a new time, representing the given time scaled by the given factor
     */
    public static Time divide(@NotNull Time t, @NotNull Number s) {
        return new Time((long) ((float) t.microseconds / s.doubleValue()));
    }

    /**
     * Computes a time period's ratio of another time period.
     *
     * @param t1 the first time period
     * @param t2 the second time period
     * @return the ratio of the first time period to the second time period
     */
    public static float ratio(@NotNull Time t1, @NotNull Time t2) {
        return (float) t1.microseconds / (float) t2.microseconds;
    }

    /**
     * Returns the time value in seconds.
     *
     * @return the time value in seconds
     */
    public float asSeconds() {
        return (float) microseconds / 1000000.0f;
    }

    /**
     * Returns the time value in milliseconds.
     *
     * @return the time value in milliseconds
     */
    public long asMilliseconds() {
        return microseconds / 1000;
    }

    /**
     * Returns the time value in microseconds.
     *
     * @return the time value in microseconds
     */
    public long asMicroseconds() {
        return microseconds;
    }

    @Override
    public int compareTo(@NotNull Time t) {
        return Long.compare(microseconds, t.microseconds);
    }

    @Override
    public int hashCode() {
        return (int) (microseconds ^ (microseconds >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof final Time t && t.microseconds == microseconds);
    }

    @Override
    public String toString() {
        return "Time{microseconds=" + microseconds + '}';
    }
}
