package com.rubynaxela.kyanite.math;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents two-dimensional vectors using integer coordinates and provides
 * arithmetic operations on integral 2D vectors.
 */
public final class Vector2i implements Serializable {

    /**
     * The zero vector.
     */
    public static final Vector2i ZERO = new Vector2i(0, 0);
    @Serial
    private static final long serialVersionUID = 4059550337913883695L;

    /**
     * The vector's X coordinate.
     */
    public int x;

    /**
     * The vector's Y coordinate.
     */
    public int y;

    /**
     * Constructs a new vector with the given coordinates.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     */
    public Vector2i(int x, int y) {
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
        return (o instanceof final Vector2i v && v.x == x && v.y == y);
    }

    @Override
    public String toString() {
        return "Vector2i{x=" + x + ", y=" + y + '}';
    }
}
