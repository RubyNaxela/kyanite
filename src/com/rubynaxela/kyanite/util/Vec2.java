package com.rubynaxela.kyanite.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.core.system.Vector2f;
import com.rubynaxela.kyanite.core.system.Vector2i;

/**
 * Provides aliases for {@link Vector2f}'s and {@link Vector2i}'s constructors.
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
        return new Vector2f(vector);
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
        return new Vector2i(vector);
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
        return Vector2f.add(vector1, vector2);
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
        return Vector2f.add(Vec2.f(vector1), vector2);
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
        return Vector2f.add(vector1, Vec2.f(vector2));
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
        return Vector2i.add(vector1, vector2);
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
        return Vector2f.sub(vector1, vector2);
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
        return Vector2f.sub(Vec2.f(vector1), vector2);
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
        return Vector2f.sub(vector1, Vec2.f(vector2));
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
        return Vector2i.sub(vector1, vector2);
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
        return Vector2f.mul(vector, scalar.floatValue());
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
        return Vector2f.mul(vector, scalar.floatValue());
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
        return Vector2i.mul(vector, scalar.intValue());
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
        return Vector2i.mul(vector, scalar.intValue());
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
        return Vec2.f(vector1.x * vector2.x, vector1.y * vector2.y);
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
        return Vec2.f(vector1.x * vector2.x, vector1.y * vector2.y);
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
        return Vec2.f(vector1.x * vector2.x, vector1.y * vector2.y);
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
        return Vec2.i(vector1.x * vector2.x, vector1.y * vector2.y);
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
        return Vec2.f(vector1.x / vector2.x, vector1.y / vector2.y);
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
        return Vec2.f(vector1.x / vector2.x, vector1.y / vector2.y);
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
        return Vec2.f(vector1.x / vector2.x, vector1.y / vector2.y);
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
    public static Vector2i divide(@NotNull Vector2i vector1, @NotNull Vector2i vector2) {
        return Vec2.i(vector1.x / vector2.x, vector1.y / vector2.y);
    }

    /**
     * Computes the Hadamard (element-wise) quotient of the specified vectors, which is the result of the following expression:
     * <pre>[x1 / x2, y1 / y2]</pre> for <pre>vector1 = [x1, y1], vector2 = [x2, y2]</pre>
     * Despite accepting two {@link Vector2i} arguments, performs mathematically correct
     * division with the {@code float} precision and returns a {@link Vector2f}.
     *
     * @param vector1 the first vector
     * @param vector2 the second vector
     * @return the Hadamard (element-wise) quotient of the specified vectors
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f divideFloat(@NotNull Vector2i vector1, @NotNull Vector2i vector2) {
        return Vec2.f(1.0f * vector1.x / vector2.x, 1.0f * vector1.y / vector2.y);
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
        return Vector2f.div(vector, scalar.floatValue());
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2i divide(@NotNull Vector2i vector, @NotNull Number scalar) {
        return Vector2i.div(vector, scalar.intValue());
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f divideFloat(@NotNull Vector2f vector, @NotNull Number scalar) {
        return Vector2f.div(vector, scalar.floatValue());
    }

    /**
     * Multiplies a vector by the inverse of a scalar.
     *
     * @param vector the vector
     * @param scalar the scalar to divide by
     * @return a new vector, representing the scaled vector
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f divideFloat(@NotNull Vector2i vector, @NotNull Number scalar) {
        return Vector2f.div(Vec2.f(vector), scalar.floatValue());
    }
}
