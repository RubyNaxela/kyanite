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
 * Provides aliases for {@link Vector3f}'s and {@link Vector3i}'s constructors as well as arithmetic operations with both types.
 */
public final strictfp class Vec3 {

    private Vec3() {
    }

    /**
     * Constructs a new {@link Vector3f} with given values.
     *
     * @param x the X component of this vector
     * @param y the Y component of this vector
     * @param z the Z component of this vector
     * @return a new {@link Vector3f}
     */
    @NotNull
    @Contract(pure = true, value = "_, _, _ -> new")
    public static Vector3f f(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
        return new Vector3f(x.floatValue(), y.floatValue(), z.floatValue());
    }

    /**
     * Constructs a new {@link Vector3f} by converting a {@link Vector3i}.
     *
     * @param vector the vector to copy the values from
     * @return a new {@link Vector3f}
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vector3f f(@NotNull Vector3i vector) {
        return new Vector3f(vector.x, vector.y, vector.z);
    }

    /**
     * Constructs a new {@link Vector3i} with given values.
     *
     * @param x the X component of this vector
     * @param y the Y component of this vector
     * @param z the Z component of this vector
     * @return a new {@link Vector3i}
     */
    @NotNull
    @Contract(pure = true, value = "_, _, _ -> new")
    public static Vector3i i(@NotNull Number x, @NotNull Number y, @NotNull Number z) {
        return new Vector3i(x.intValue(), y.intValue(), z.intValue());
    }

    /**
     * Constructs a new {@link Vector3i} by converting a {@link Vector3f}.
     *
     * @param vector the vector to copy the values from
     * @return a new {@link Vector3i}
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vector3i i(@NotNull Vector3f vector) {
        return new Vector3i((int) vector.x, (int) vector.y, (int) vector.z);
    }

    /**
     * Adds two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the sum of the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f add(@NotNull Vector3f vector1, @NotNull Vector3f vector2) {
        return new Vector3f(vector1.x + vector2.x, vector1.y + vector2.y, vector1.z + vector2.z);
    }

    /**
     * Adds two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the sum of the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f add(@NotNull Vector3i vector1, @NotNull Vector3f vector2) {
        return new Vector3f(vector1.x + vector2.x, vector1.y + vector2.y, vector1.z + vector2.z);
    }

    /**
     * Adds two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the sum of the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f add(@NotNull Vector3f vector1, @NotNull Vector3i vector2) {
        return new Vector3f(vector1.x + vector2.x, vector1.y + vector2.y, vector1.z + vector2.z);
    }

    /**
     * Adds two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the sum of the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i add(@NotNull Vector3i vector1, @NotNull Vector3i vector2) {
        return new Vector3i(vector1.x + vector2.x, vector1.y + vector2.y, vector1.z + vector2.z);
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the difference between the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f subtract(@NotNull Vector3f vector1, @NotNull Vector3f vector2) {
        return new Vector3f(vector1.x - vector2.x, vector1.y - vector2.y, vector1.z - vector2.z);
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the difference between the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f subtract(@NotNull Vector3i vector1, @NotNull Vector3f vector2) {
        return new Vector3f(vector1.x - vector2.x, vector1.y - vector2.y, vector1.z - vector2.z);
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the difference between the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f subtract(@NotNull Vector3f vector1, @NotNull Vector3i vector2) {
        return new Vector3f(vector1.x - vector2.x, vector1.y - vector2.y, vector1.z - vector2.z);
    }

    /**
     * Subtracts two vectors.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return a new vector, representing the difference between the two vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i subtract(@NotNull Vector3i vector1, @NotNull Vector3i vector2) {
        return new Vector3i(vector1.x - vector2.x, vector1.y - vector2.y, vector1.z - vector2.z);
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to multiply by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f multiply(@NotNull Vector3f vector, @NotNull Number scalar) {
        final float factor = scalar.floatValue();
        return new Vector3f(vector.x * factor, vector.y * factor, vector.z * factor);
    }

    /**
     * Multiplies a scalar by a vector.
     *
     * @param scalar the scalar to multiply by
     * @param vector the vector
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f multiply(@NotNull Number scalar, @NotNull Vector3f vector) {
        final float factor = scalar.floatValue();
        return new Vector3f(vector.x * factor, vector.y * factor, vector.z * factor);
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to multiply by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i multiply(@NotNull Vector3i vector, @NotNull Number scalar) {
        final float factor = scalar.floatValue();
        return new Vector3i((int) (vector.x * factor), (int) (vector.y * factor), (int) (vector.z * factor));
    }

    /**
     * Multiplies a scalar by a vector.
     *
     * @param scalar the scalar to multiply by
     * @param vector the vector
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i multiply(@NotNull Number scalar, @NotNull Vector3i vector) {
        final float factor = scalar.floatValue();
        return new Vector3i((int) (vector.x * factor), (int) (vector.y * factor), (int) (vector.z * factor));
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
    public static Vector3f multiply(@NotNull Vector3f vector1, @NotNull Vector3f vector2) {
        return new Vector3f(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z);
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
    public static Vector3f multiply(@NotNull Vector3f vector1, @NotNull Vector3i vector2) {
        return new Vector3f(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z);
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
    public static Vector3f multiply(@NotNull Vector3i vector1, @NotNull Vector3f vector2) {
        return new Vector3f(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z);
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
    public static Vector3i multiply(@NotNull Vector3i vector1, @NotNull Vector3i vector2) {
        return new Vector3i(vector1.x * vector2.x, vector1.y * vector2.y, vector1.z * vector2.z);
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
    public static Vector3f divide(@NotNull Vector3f vector1, @NotNull Vector3f vector2) {
        return new Vector3f(vector1.x / vector2.x, vector1.y / vector2.y, vector1.z / vector2.z);
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
    public static Vector3f divide(@NotNull Vector3f vector1, @NotNull Vector3i vector2) {
        return new Vector3f(vector1.x / vector2.x, vector1.y / vector2.y, vector1.z / vector2.z);
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
    public static Vector3f divide(@NotNull Vector3i vector1, @NotNull Vector3f vector2) {
        return new Vector3f(vector1.x / vector2.x, vector1.y / vector2.y, vector1.z / vector2.z);
    }

    /**
     * Computes the Hadamard (element-wise) quotient of the specified vectors, which is the result of the following expression:
     * <pre>[x1 / x2, y1 / y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     * Note that integer division is used. For floating-point division use {@link Vec3#divideFloat(Vector3i, Vector3i)}.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) quotient of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i divide(@NotNull Vector3i vector1, @NotNull Vector3i vector2) {
        return new Vector3i(vector1.x / vector2.x, vector1.y / vector2.y, vector1.z / vector2.z);
    }

    /**
     * Computes the Hadamard (element-wise) quotient of the specified vectors, which is the result of the following expression:
     * <pre>[x1 / x2, y1 / y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     * Note that floating-point division is used. For integer division use {@link Vec3#divide(Vector3i, Vector3i)}.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) quotient of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i divideFloat(@NotNull Vector3i vector1, @NotNull Vector3i vector2) {
        return new Vector3i((int) ((float) vector1.x / vector2.x), (int) ((float) vector1.y / vector2.y),
                            (int) ((float) vector1.z / vector2.z));
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3f divide(@NotNull Vector3f vector, @NotNull Number scalar) {
        final float factor = scalar.floatValue();
        return new Vector3f(vector.x / factor, vector.y / factor, vector.z / factor);
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     * Note that integer division is used. For floating-point division use {@link Vec3#divideFloat(Vector3i, Number)}.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i divide(@NotNull Vector3i vector, @NotNull Number scalar) {
        final int factor = scalar.intValue();
        return new Vector3i(vector.x / factor, vector.y / factor, vector.z / factor);
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     * Note that floating-point division is used. For integer division use {@link Vec3#divide(Vector3i, Number)}.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector3i divideFloat(@NotNull Vector3i vector, @NotNull Number scalar) {
        final float factor = scalar.floatValue();
        return new Vector3i((int) (vector.x / factor), (int) (vector.y / factor), (int) (vector.z / factor));
    }
}
