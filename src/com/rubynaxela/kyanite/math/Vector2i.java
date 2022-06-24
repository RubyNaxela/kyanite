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

package com.rubynaxela.kyanite.math;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents two-dimensional vectors using integer coordinates.
 */
@SuppressWarnings("ClassCanBeRecord")
public final class Vector2i implements Serializable {

    @Serial
    private static final long serialVersionUID = 4059550337913883695L;
    /**
     * The vector's X coordinate.
     */
    public final int x;
    /**
     * The vector's Y coordinate.
     */
    public final int y;

    /**
     * Constructs a new vector with the given coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a zero vector.
     *
     * @return a new {@code Vector2i} with zero coordinates
     */
    @Contract(pure = true, value = "-> new")
    public static Vector2i zero() {
        return new Vector2i(0, 0);
    }

    @Override
    public int hashCode() {
        int result = (x != 0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != 0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof final Vector2i v && v.x == x && v.y == y);
    }

    @Override
    public String toString() {
        return "Vector2i{x=" + x + ", y=" + y + '}';
    }

    /**
     * Creates a new {@link Vector2f} by converting this vector to a floating-point vector.
     *
     * @return this vector converted to {@code Vector2f}
     */
    @NotNull
    @Contract(pure = true, value = "-> new")
    public Vector2f asVector2f() {
        return Vec2.f(this);
    }
}
