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
 * Represents an axis-aligned rectangle using integer coordinates.
 */
public final class IntRect implements Serializable {

    /**
     * An empty rectangle with no dimensions.
     */
    public static final IntRect EMPTY = new IntRect(0, 0, 0, 0);

    @Serial
    private static final long serialVersionUID = -4430448425788537785L;

    /**
     * The X coordinate of the rectangle's left edge.
     */
    public final int left;
    /**
     * The Y coordinate of the rectangle's top edge.
     */
    public final int top;
    /**
     * The X-coordinate of the right side of this rectangle.
     */
    public final int right;
    /**
     * The Y-coordinate of the top bottom of this rectangle.
     */
    public final int bottom;
    /**
     * The width of the rectangle.
     */
    public final int width;
    /**
     * The height of the rectangle.
     */
    public final int height;

    /**
     * Constructs a new rectangle with the specified parameters.
     *
     * @param left   the X coordinate of the rectangle's left edge
     * @param top    the Y coordinate of the rectangle's top edge
     * @param width  the rectangle's width
     * @param height the rectangle's height
     */
    public IntRect(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.right = left + width;
        this.bottom = top + height;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new rectangle with the specified parameters.
     *
     * @param position the position of the rectangle's top left corner
     * @param size     the rectangle's dimension
     */
    public IntRect(@NotNull Vector2i position, @NotNull Vector2i size) {
        this(position.x, position.y, size.x, size.y);
    }

    /**
     * Creates a new rectangle with the specified coordinates.
     *
     * @param left   the X-coordinate of the left side of this rectangle
     * @param top    the Y-coordinate of the top side of this rectangle
     * @param right  the X-coordinate of the right side of this rectangle
     * @param bottom the Y-coordinate of the bottom side of this rectangle
     */
    @Contract(pure = true, value = "_, _, _, _ -> new")
    public static IntRect fromCoordinates(int left, int top, int right, int bottom) {
        return new IntRect(left, top, right - left, bottom - top);
    }

    /**
     * Tests whether a point is inside the rectangle's boundaries, including its edges.
     *
     * @param x the X coordinate of the tested point.
     * @param y the Y coordinate of the tested point.
     * @return {@code true} if the point lies within the rectangle's boundaries,
     * {@code false} otherwise.
     */
    public boolean contains(int x, int y) {
        final int minX = Math.min(left, left + width),
                maxX = Math.max(left, left + width),
                minY = Math.min(top, top + height),
                maxY = Math.max(top, top + height);
        return (x >= minX) && (x < maxX) && (y >= minY) && (y < maxY);
    }

    /**
     * Tests whether a point is inside the rectangle's boundaries, including its edges.
     *
     * @param point the point to be tested
     * @return {@code true} if the point lies within the rectangle's boundaries, {@code false} otherwise
     */
    public boolean contains(@NotNull Vector2i point) {
        return contains(point.x, point.y);
    }

    /**
     * Tests whether this rectangle intersects with another rectangle and calculates the rectangle of intersection.
     *
     * @param rect the rectangle to test against
     * @return the intersection rectangle, or {@code null} if the rectangles do not intersect
     */
    @Contract(pure = true)
    public FloatRect intersection(@NotNull FloatRect rect) {
        final float interLeft = Math.max(Math.min(left, right), Math.min(rect.left, rect.right)),
                interTop = Math.max(Math.min(top, bottom), Math.min(rect.top, rect.bottom)),
                interRight = Math.min(Math.max(left, right), Math.max(rect.left, rect.right)),
                interBottom = Math.min(Math.max(top, bottom), Math.max(rect.top, rect.bottom));
        if ((interLeft < interRight) && (interTop < interBottom))
            return FloatRect.fromCoordinates(interLeft, interTop, interRight, interBottom);
        else return null;
    }

    /**
     * Tests whether this rectangle intersects with another rectangle and calculates the rectangle of intersection.
     *
     * @param rect the rectangle to test against
     * @return the intersection rectangle, or {@code null} if the rectangles do not intersect
     */
    @Contract(pure = true)
    public IntRect intersection(@NotNull IntRect rect) {
        final int interLeft = Math.max(Math.min(left, right), Math.min(rect.left, rect.right)),
                interTop = Math.max(Math.min(top, bottom), Math.min(rect.top, rect.bottom)),
                interRight = Math.min(Math.max(left, right), Math.max(rect.left, rect.right)),
                interBottom = Math.min(Math.max(top, bottom), Math.max(rect.top, rect.bottom));
        if ((interLeft < interRight) && (interTop < interBottom))
            return IntRect.fromCoordinates(interLeft, interTop, interRight, interBottom);
        else return null;
    }

    /**
     * Tests whether this rectangle intersects with another rectangle.
     *
     * @param rect the rectangle to test against
     * @return {@code true} if this rectangle intersects with {@code rect}, {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean intersects(@NotNull FloatRect rect) {
        return intersection(rect) != null;
    }

    /**
     * Tests whether this rectangle intersects with another rectangle.
     *
     * @param rect the rectangle to test against
     * @return {@code true} if this rectangle intersects with {@code rect}, {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean intersects(@NotNull IntRect rect) {
        return intersection(rect) != null;
    }

    /**
     * Tests whether the specified point is above this rectangle.
     *
     * @param point a 2D point
     * @return {@code true} if the specified point is above this rectangle, {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isAbove(@NotNull Vector2i point) {
        return point.y < top;
    }

    /**
     * Tests whether the specified point is on the right of this rectangle.
     *
     * @param point a 2D point
     * @return {@code true} if the specified point is on the right of this rectangle, {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isOnTheRight(@NotNull Vector2i point) {
        return point.x > right;
    }

    /**
     * Tests whether the specified point is below this rectangle.
     *
     * @param point a 2D point
     * @return {@code true} if the specified point is below this rectangle, {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isBelow(@NotNull Vector2i point) {
        return point.y > bottom;
    }

    /**
     * Test whether the specified point is on the left of this rectangle.
     *
     * @param point a 2D point
     * @return {@code true} if the specified point is on the left of this rectangle, {@code false} otherwise
     */
    @Contract(pure = true)
    public boolean isOnTheLeft(@NotNull Vector2i point) {
        return point.x < left;
    }

    /**
     * Calculates the middle point of this rectangle.
     *
     * @return the middle point of this rectangle
     */
    @Contract(pure = true, value = "-> new")
    public Vector2f getCenter() {
        return Vec2.f((left + width) / 2.0f, (top + height) / 2.0f);
    }

    /**
     * Gets the corner of this rectangle nearest to the specified point.
     *
     * @param point a 2D point
     * @return this rectangle's corner that is the nearest to the specified point
     */
    @Contract(pure = true, value = "_ -> new")
    public Vector2i getNearestCorner(@NotNull Vector2i point) {
        int x = point.x, y = point.y;
        return Vec2.i(x - left < right - x ? left : right, y - bottom < top - y ? top : bottom);
    }

    @Override
    public int hashCode() {
        int result = left;
        result = 31 * result + top;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof final IntRect r)
            return (left == r.left && top == r.top && width == r.width && height == r.height);
        else return false;
    }

    @Override
    public String toString() {
        return "IntRect{left=" + left + ", top=" + top + ", width=" + width + ", height=" + height + '}';
    }

    /**
     * Creates a new floating point rectangle by converting this rectangle.
     */
    @NotNull
    @Contract(pure = true, value = "-> new")
    public FloatRect asFloatRect() {
        return new FloatRect(left, top, right, bottom);
    }
}
