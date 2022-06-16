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

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents two-dimensional vectors using floating point coordinates and provides
 * arithmetic operations on floating point 2D vectors.
 */
public final class Vector2f implements Serializable {

    /**
     * The zero vector.
     */
    public static final Vector2f ZERO = new Vector2f(0, 0);
    @Serial
    private static final long serialVersionUID = -2082611034304583379L;

    /**
     * The vector's X coordinate.
     */
    public float x;

    /**
     * The vector's Y coordinate.
     */
    public float y;

    /**
     * Constructs a new vector with the given coordinates.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     */
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof final Vector2f v && v.x == x && v.y == y);
    }

    @Override
    public String toString() {
        return "Vector2f{x=" + x + ", y=" + y + '}';
    }
}
