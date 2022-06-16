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

/**
 * Provides aliases for {@link Vector2f}'s and {@link Vector2i}'s constructors as well as arithmetic operations with both types.
 */
public final class Vec2 {

    private Vec2() {
    }

    /**
     * Constructs a new {@link Vector2f} with given values.
     *
     * @param x the X component of this vector
     * @param y the Y component of this vector
     * @return a new {@link Vector2f}
     */
    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f f(@NotNull Number x, @NotNull Number y) {
        return new Vector2f(x.floatValue(), y.floatValue());
    }

    /**
     * Constructs a new {@link Vector2f} by converting a {@link Vector2i}.
     *
     * @param vector the vector to copy the values from
     * @return a new {@link Vector2f}
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vector2f f(@NotNull Vector2i vector) {
        return new Vector2f(vector.x, vector.y);
    }

    /**
     * Constructs a new {@link Vector2i} with given values.
     *
     * @param x the X component of this vector
     * @param y the Y component of this vector
     * @return a new {@link Vector2i}
     */
    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i i(@NotNull Number x, @NotNull Number y) {
        return new Vector2i(x.intValue(), y.intValue());
    }

    /**
     * Constructs a new {@link Vector2i} by converting a {@link Vector2f}.
     *
     * @param vector the vector to copy the values from
     * @return a new {@link Vector2i}
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vector2i i(@NotNull Vector2f vector) {
        return new Vector2i((int) vector.x, (int) vector.y);
    }

    /**
     * Adds two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the sum of the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f add(@NotNull Vector2f vector1, @NotNull Vector2f vector2) {
        return new Vector2f(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    /**
     * Adds two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the sum of the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f add(@NotNull Vector2i vector1, @NotNull Vector2f vector2) {
        return new Vector2f(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    /**
     * Adds two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the sum of the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f add(@NotNull Vector2f vector1, @NotNull Vector2i vector2) {
        return new Vector2f(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    /**
     * Adds two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the sum of the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i add(@NotNull Vector2i vector1, @NotNull Vector2i vector2) {
        return new Vector2i(vector1.x + vector2.x, vector1.y + vector2.y);
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the difference between the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f subtract(@NotNull Vector2f vector1, @NotNull Vector2f vector2) {
        return new Vector2f(vector1.x - vector2.x, vector1.y - vector2.y);
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the difference between the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f subtract(@NotNull Vector2i vector1, @NotNull Vector2f vector2) {
        return new Vector2f(vector1.x - vector2.x, vector1.y - vector2.y);
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the difference between the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f subtract(@NotNull Vector2f vector1, @NotNull Vector2i vector2) {
        return new Vector2f(vector1.x - vector2.x, vector1.y - vector2.y);
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the difference between the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i subtract(@NotNull Vector2i vector1, @NotNull Vector2i vector2) {
        return new Vector2i(vector1.x - vector2.x, vector1.y - vector2.y);
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to multiply by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f multiply(@NotNull Vector2f vector, @NotNull Number scalar) {
        final float factor = scalar.floatValue();
        return new Vector2f(vector.x * factor, vector.y * factor);
    }

    /**
     * Multiplies a scalar by a vector.
     *
     * @param scalar the scalar to multiply by
     * @param vector the vector
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f multiply(@NotNull Number scalar, @NotNull Vector2f vector) {
        final float factor = scalar.floatValue();
        return new Vector2f(vector.x * factor, vector.y * factor);
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to multiply by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i multiply(@NotNull Vector2i vector, @NotNull Number scalar) {
        final float factor = scalar.floatValue();
        return new Vector2i((int) (vector.x * factor), (int) (vector.y * factor));
    }

    /**
     * Multiplies a scalar by a vector.
     *
     * @param scalar the scalar to multiply by
     * @param vector the vector
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i multiply(@NotNull Number scalar, @NotNull Vector2i vector) {
        final float factor = scalar.floatValue();
        return new Vector2i((int) (vector.x * factor), (int) (vector.y * factor));
    }

    /**
     * Computes the Hadamard (element-wise) product of the specified vectors, which is the result of the following expression:
     * <pre>[x1 * x2, y1 * y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) product of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f multiply(@NotNull Vector2f vector1, @NotNull Vector2f vector2) {
        return new Vector2f(vector1.x * vector2.x, vector1.y * vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) product of the specified vectors, which is the result of the following expression:
     * <pre>[x1 * x2, y1 * y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) product of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f multiply(@NotNull Vector2f vector1, @NotNull Vector2i vector2) {
        return new Vector2f(vector1.x * vector2.x, vector1.y * vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) product of the specified vectors, which is the result of the following expression:
     * <pre>[x1 * x2, y1 * y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) product of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f multiply(@NotNull Vector2i vector1, @NotNull Vector2f vector2) {
        return new Vector2f(vector1.x * vector2.x, vector1.y * vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) product of the specified vectors, which is the result of the following expression:
     * <pre>[x1 * x2, y1 * y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) product of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i multiply(@NotNull Vector2i vector1, @NotNull Vector2i vector2) {
        return new Vector2i(vector1.x * vector2.x, vector1.y * vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) quotient of the specified vectors, which is the result of the following expression:
     * <pre>[x1 / x2, y1 / y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) quotient of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f divide(@NotNull Vector2f vector1, @NotNull Vector2f vector2) {
        return new Vector2f(vector1.x / vector2.x, vector1.y / vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) quotient of the specified vectors, which is the result of the following expression:
     * <pre>[x1 / x2, y1 / y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) quotient of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f divide(@NotNull Vector2f vector1, @NotNull Vector2i vector2) {
        return new Vector2f(vector1.x / vector2.x, vector1.y / vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) quotient of the specified vectors, which is the result of the following expression:
     * <pre>[x1 / x2, y1 / y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) quotient of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f divide(@NotNull Vector2i vector1, @NotNull Vector2f vector2) {
        return new Vector2f(vector1.x / vector2.x, vector1.y / vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) quotient of the specified vectors, which is the result of the following expression:
     * <pre>[x1 / x2, y1 / y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     * Note that integer division is used. For floating-point division use {@link Vec2#divideFloat(Vector2i, Vector2i)}.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) quotient of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i divide(@NotNull Vector2i vector1, @NotNull Vector2i vector2) {
        return new Vector2i(vector1.x / vector2.x, vector1.y / vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) quotient of the specified vectors, which is the result of the following expression:
     * <pre>[x1 / x2, y1 / y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     * Note that floating-point division is used. For integer division use {@link Vec2#divide(Vector2i, Vector2i)}.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) quotient of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i divideFloat(@NotNull Vector2i vector1, @NotNull Vector2i vector2) {
        return new Vector2i((int) ((float) vector1.x / vector2.x), (int) ((float) vector1.y / vector2.y));
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f divide(@NotNull Vector2f vector, @NotNull Number scalar) {
        final float factor = scalar.floatValue();
        return new Vector2f(vector.x / factor, vector.y / factor);
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     * Note that integer division is used. For floating-point division use {@link Vec2#divideFloat(Vector2i, Number)}.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i divide(@NotNull Vector2i vector, @NotNull Number scalar) {
        final int factor = scalar.intValue();
        return new Vector2i(vector.x / factor, vector.y / factor);
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     * Note that floating-point division is used. For integer division use {@link Vec2#divide(Vector2i, Number)}.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i divideFloat(@NotNull Vector2i vector, @NotNull Number scalar) {
        final float factor = scalar.floatValue();
        return new Vector2i((int) (vector.x / factor), (int) (vector.y / factor));
    }
}
