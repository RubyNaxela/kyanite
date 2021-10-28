package com.rubynaxela.kyanite.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

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
    public static <T extends Number> Vector2f f(T x, T y) {
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
    public static Vector2f f(Vector2i vector) {
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
    public static <T extends Number> Vector2i i(T x, T y) {
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
    public static Vector2i i(Vector2f vector) {
        return new Vector2i(vector);
    }
}
