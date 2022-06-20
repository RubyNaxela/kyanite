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
 * Represents three-dimensional vectors using integer coordinates.
 */
@SuppressWarnings("ClassCanBeRecord")
public final class Vector3i implements Serializable {

    @Serial
    private static final long serialVersionUID = -2260992069088589367L;
    /**
     * The vector's X coordinate.
     */
    public final int x;
    /**
     * The vector's Y coordinate.
     */
    public final int y;
    /**
     * The vector's Z coordinate.
     */
    public final int z;

    /**
     * Constructs a new vector with the given coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param z the Z coordinate
     */
    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a zero vector.
     */
    @Contract(pure = true, value = "-> new")
    public static Vector3i zero() {
        return new Vector3i(0, 0, 0);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
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
     * Creates a new {@link Vector3f} by converting this vector to a floating-point vector.
     *
     * @return this vector converted to {@code Vector3f}
     */
    @NotNull
    @Contract(pure = true, value = "-> new")
    public Vector3f asVector3f() {
        return Vec3.f(this);
    }
}
