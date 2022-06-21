/*
 * Copyright (c) 2022 Alex Pawelski
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

import org.jetbrains.annotations.NotNull;

/**
 * Represents a line segment connecting two points using floating point coordinates.
 */
@SuppressWarnings("ClassCanBeRecord")
public final class FloatLine {

    /**
     * The first point of this line.
     */
    public final Vector2f point1;
    /**
     * The second point of this line.
     */
    public final Vector2f point2;

    /**
     * Constructs a line connecting the specified points
     *
     * @param point1 the first point of this line
     * @param point2 the second point of this line
     */
    public FloatLine(@NotNull Vector2f point1, @NotNull Vector2f point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    /**
     * Calculates the length of this line.
     *
     * @return the length of this line
     */
    public float getLength() {
        return (float) Math.hypot(point1.x - point2.x, point1.y - point2.y);
    }

    /**
     * Tests whether this line segment intersects another line segment.
     *
     * @param other a line segment
     * @return {@code true} if the line segments intersect, {@code false} otherwise
     */
    public boolean intersects(@NotNull FloatLine other) {
        final double det = MathUtils.determinant(Vec2.subtract(point2, point1), Vec2.subtract(other.point1, other.point2)),
                t = MathUtils.determinant(Vec2.subtract(other.point1, point1), Vec2.subtract(other.point1, other.point2)) / det,
                u = MathUtils.determinant(Vec2.subtract(point2, point1), Vec2.subtract(other.point1, point1)) / det;
        return (!(t < 0)) && (!(u < 0)) && (!(t > 1)) && (!(u > 1));
    }
}
