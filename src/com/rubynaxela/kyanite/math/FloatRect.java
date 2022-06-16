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

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an axis-aligned rectangle using floating point coordinates.
 */
public final class FloatRect implements Serializable {

    /**
     * An empty rectangle with no dimensions.
     */
    public static final FloatRect EMPTY = new FloatRect(0, 0, 0, 0);

    @Serial
    private static final long serialVersionUID = -8603980852893951558L;

    /**
     * The X coordinate of the rectangle's left edge.
     */
    public final float left;
    /**
     * The Y coordinate of the rectangle's top edge.
     */
    public final float top;
    /**
     * The width of the rectangle.
     */
    public final float width;
    /**
     * The height of the rectangle.
     */
    public final float height;

    /**
     * Constructs a new rectangle with the specified parameters.
     *
     * @param left   the X coordinate of the rectangle's left edge
     * @param top    the Y coordinate of the rectangle's top edge
     * @param width  the rectangle's width
     * @param height the rectangle's height
     */
    public FloatRect(float left, float top, float width, float height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new rectangle with the specified parameters.
     *
     * @param position the position of the rectangle's top left corner
     * @param size     the rectangle's dimension
     */
    public FloatRect(@NotNull Vector2f position, @NotNull Vector2f size) {
        this.left = position.x;
        this.top = position.y;
        this.width = size.x;
        this.height = size.y;
    }

    /**
     * Constructs a new rectangle by converting an intergral rectangle. The fractions of the components will be set to zero.
     *
     * @param rect the rectangle to convert
     */
    public FloatRect(IntRect rect) {
        this((float) rect.left, (float) rect.top, (float) rect.width, (float) rect.height);
    }

    /**
     * Tests whether a point is inside the rectangle's boundaries, including its edges.
     *
     * @param x the X coordinate of the tested point
     * @param y the Y coordinate of the tested point
     * @return {@code true} if the point lies within the rectangle's boundaries, {@code false} otherwise
     */
    public boolean contains(float x, float y) {
        final float minX = Math.min(left, left + width);
        final float maxX = Math.max(left, left + width);
        final float minY = Math.min(top, top + height);
        final float maxY = Math.max(top, top + height);
        return (x >= minX) && (x < maxX) && (y >= minY) && (y < maxY);
    }

    /**
     * Tests whether a point is inside the rectangle's boundaries, including its edges.
     *
     * @param point the point to be tested
     * @return {@code true} if the point lies within the rectangle's boundaries, {@code false} otherwise
     */
    public boolean contains(Vector2f point) {
        return contains(point.x, point.y);
    }

    /**
     * Tests whether this rectangle intersects with another rectangle and calculates the rectangle of intersection.
     *
     * @param rect the rectangle to test against
     * @return the intersection rectangle, or {@code null} if the rectangles do not intersect
     */
    public FloatRect intersection(FloatRect rect) {
        final float interLeft = Math.max(Math.min(left, left + width), Math.min(rect.left, rect.left + rect.width)),
                interTop = Math.max(Math.min(top, top + height), Math.min(rect.top, rect.top + rect.height)),
                interRight = Math.min(Math.max(left, left + width), Math.max(rect.left, rect.left + rect.width)),
                interBottom = Math.min(Math.max(top, top + height), Math.max(rect.top, rect.top + rect.height));
        if ((interLeft < interRight) && (interTop < interBottom))
            return new FloatRect(interLeft, interTop, interRight - interLeft, interBottom - interTop);
        else return null;
    }

    @Override
    public int hashCode() {
        int result = (left != +0.0f ? Float.floatToIntBits(left) : 0);
        result = 31 * result + (top != +0.0f ? Float.floatToIntBits(top) : 0);
        result = 31 * result + (width != +0.0f ? Float.floatToIntBits(width) : 0);
        result = 31 * result + (height != +0.0f ? Float.floatToIntBits(height) : 0);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof final FloatRect r)
            return (left == r.left && top == r.top && width == r.width && height == r.height);
        else return false;
    }

    @Override
    public String toString() {
        return "FloatRect{left=" + left + ", top=" + top + ", width=" + width + ", height=" + height + '}';
    }
}
