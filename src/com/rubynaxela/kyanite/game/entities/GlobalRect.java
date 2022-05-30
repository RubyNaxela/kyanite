package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.math.Vec2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector2i;

/**
 * Represents an axis-aligned rectangle using floating point coordinates. Useful when the
 * right and bottom side coordinates of a rectangle are needed instead of its width and height.
 */
public class GlobalRect {

    /**
     * The Y-coordinate of the top side of this rectangle.
     */
    public final float top;

    /**
     * The X-coordinate of the right side of this rectangle.
     */
    public final float right;

    /**
     * The Y-coordinate of the top bottom of this rectangle.
     */
    public final float bottom;

    /**
     * The X-coordinate of the top left of this rectangle.
     */
    public final float left;

    private final FloatRect floatRect;

    /**
     * Creates a new rectangle.
     *
     * @param top    the Y-coordinate of the top side of this rectangle
     * @param right  the X-coordinate of the right side of this rectangle
     * @param bottom the Y-coordinate of the bottom side of this rectangle
     * @param left   the X-coordinate of the left side of this rectangle
     */
    public GlobalRect(float top, float right, float bottom, float left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        this.floatRect = new FloatRect(left, top, right - left, bottom - top);
    }

    /**
     * Converts a {@link FloatRect} object to a {@code GlobalBounds} object with corresponding coordinates.
     *
     * @param boundingBox global bounds stored as a {@link FloatRect}
     * @return a new {@code GlobalBounds} object
     */
    @Contract(pure = true, value = "null -> null; !null -> !null")
    public static GlobalRect from(@Nullable FloatRect boundingBox) {
        if (boundingBox == null) return null;
        return new GlobalRect(boundingBox.top, boundingBox.left + boundingBox.width,
                              boundingBox.top + boundingBox.height, boundingBox.left);
    }

    /**
     * Converts a {@link FloatRect} object to a {@code GlobalBounds} object with corresponding coordinates.
     *
     * @param boundingBox global bounds stored as a {@link FloatRect}
     * @return a new {@code GlobalBounds} object
     */
    @Contract(pure = true, value = "null -> null; !null -> !null")
    public static GlobalRect from(@Nullable IntRect boundingBox) {
        if (boundingBox == null) return null;
        return new GlobalRect(boundingBox.top, boundingBox.left + boundingBox.width,
                              boundingBox.top + boundingBox.height, boundingBox.left);
    }

    /**
     * @return a {@link FloatRect} object with corresponding coordinates
     */
    @Contract(pure = true)
    public FloatRect toFloatRect() {
        return floatRect;
    }

    /**
     * Tests whether a point is inside the rectangle's boundaries, including its edges.
     *
     * @param point the point to be tested
     * @return whether the point lies within the rectangle's boundaries
     */
    @Contract(pure = true)
    public boolean contains(@NotNull Vector2f point) {
        return floatRect.contains(point);
    }

    /**
     * Tests whether a point is inside the rectangle's boundaries, including its edges.
     *
     * @param point the point to be tested
     * @return whether the point lies within the rectangle's boundaries
     */
    @Contract(pure = true)
    public boolean contains(@NotNull Vector2i point) {
        return floatRect.contains(point.x, point.y);
    }

    /**
     * Calculates the rectangle of bounds intersection.
     *
     * @param globalRect the bounds to test against
     * @return the intersection rectangle, or {@code null} if the bounds do not intersect
     */
    @Nullable
    @Contract(pure = true)
    public GlobalRect intersection(@NotNull GlobalRect globalRect) {
        return GlobalRect.from(floatRect.intersection(globalRect.floatRect));
    }

    /**
     * @param globalRect the rectangle to test against
     * @return whether these bounds intersect with another bounds
     */
    @Contract(pure = true)
    public boolean intersects(@NotNull GlobalRect globalRect) {
        return intersection(globalRect) != null;
    }

    /**
     * @param point a 2D point
     * @return whether the specified point is above this rectangle
     */
    @Contract(pure = true)
    public boolean isAbove(@NotNull Vector2f point) {
        return point.y < top;
    }

    /**
     * @param point a 2D point
     * @return whether the specified point is on the right of this rectangle
     */
    @Contract(pure = true)
    public boolean isOnTheRight(@NotNull Vector2f point) {
        return point.x > right;
    }

    /**
     * @param point a 2D point
     * @return whether the specified point is below this rectangle
     */
    @Contract(pure = true)
    public boolean isBelow(@NotNull Vector2f point) {
        return point.y > bottom;
    }

    /**
     * @param point a 2D point
     * @return whether the specified point is on the left of this rectangle
     */
    @Contract(pure = true)
    public boolean isOnTheLeft(@NotNull Vector2f point) {
        return point.x < left;
    }

    /**
     * @return the middle point of this rectangle
     */
    @Contract(pure = true, value = "-> new")
    public Vector2f getCenter() {
        return Vec2.f(left + floatRect.width / 2, top + floatRect.height / 2);
    }

    /**
     * @param point a 2D point
     * @return this rectangle's corner that is the closest to the specified point
     */
    @Contract(pure = true, value = "_ -> new")
    public Vector2f getNearestCorner(@NotNull Vector2f point) {
        return Vec2.f(point.x - left < right - point.x ? left : right, point.y - bottom < top - point.y ? top : bottom);
    }

    @Override
    public String toString() {
        return "HitBox{" + "top=" + top + ", right=" + right + ", bottom=" + bottom + ", left=" + left + '}';
    }
}
