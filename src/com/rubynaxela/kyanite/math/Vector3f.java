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
 * Represents three-dimensional vectors using floating point coordinates.
 */
@SuppressWarnings("ClassCanBeRecord")
public final class Vector3f implements Serializable {

    @Serial
    private static final long serialVersionUID = -2176250005619169432L;
    /**
     * The vector's X coordinate.
     */
    public final float x;
    /**
     * The vector's Y coordinate.
     */
    public final float y;
    /**
     * The vector's Z coordinate.
     */
    public final float z;

    /**
     * Constructs a new vector with the given coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a zero vector.
     *
     * @return a new {@code Vector3f} with zero coordinates
     */
    @Contract(pure = true, value = "-> new")
    public static Vector3f zero() {
        return new Vector3f(0, 0, 0);
    }

    @Override
    public int hashCode() {
        int result = (x != 0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != 0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != 0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof final Vector3f v && v.x == x && v.y == y && v.z == z);
    }

    @Override
    public String toString() {
        return "Vector3f{x=" + x + ", y=" + y + ", z=" + z + '}';
    }

    /**
     * Creates a new {@link Vector3i} by converting this vector to an integer vector. The coordinates will be rounded down.
     *
     * @return this vector converted to {@code Vector3i}
     */
    @NotNull
    @Contract(pure = true, value = "-> new")
    public Vector3i asVector3i() {
        return Vec3.i(this);
    }
}
