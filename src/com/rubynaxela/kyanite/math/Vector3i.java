package com.rubynaxela.kyanite.math;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents three-dimensional vectors using integer coordinates and provides
 * arithmetic operations on integral 3D vectors.
 */
public final class Vector3i implements Serializable {

    /**
     * The zero vector.
     */
    public static final Vector3i ZERO = new Vector3i(0, 0, 0);
    @Serial
    private static final long serialVersionUID = -2260992069088589367L;

    /**
     * The vector's X coordinate.
     */
    public int x;

    /**
     * The vector's Y coordinate.
     */
    public int y;

    /**
     * The vector's Z coordinate.
     */
    public int z;

    /**
     * Constructs a new vector with the given coordinates.
     *
     * @param x the X coordinate.
     * @param y the Y coordinate.
     * @param z the Z coordinate.
     */
    public Vector3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof final Vector3f v && v.x == x && v.y == y && v.z == z);
    }

    @Override
    public String toString() {
        return "Vector3f{x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
