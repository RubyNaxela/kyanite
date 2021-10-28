package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Represents an axis-aligned rectangle using floating point coordinates. Useful when the
 * right and bottom side coordinates of a rectangle are needed instead of its width and height.
 */
public class GlobalBounds {

    public final float top, right, bottom, left;
    private final FloatRect floatRect;

    public GlobalBounds(float top, float right, float bottom, float left) {
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
    @Contract(pure = true, value = "_ -> new")
    public static GlobalBounds from(@NotNull FloatRect boundingBox) {
        return new GlobalBounds(boundingBox.top, boundingBox.left + boundingBox.width,
                                boundingBox.top + boundingBox.height, boundingBox.left);
    }

    /**
     * @return a {@link FloatRect} object with corresponding coordinates
     */
    @Contract(pure = true, value = "-> new")
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
     * @param globalBounds the bounds to test against
     * @return the intersection rectangle, or {@code null} if the bounds do not intersect
     */
    @Nullable
    @Contract(pure = true)
    public GlobalBounds intersection(@NotNull GlobalBounds globalBounds) {
        return GlobalBounds.from(floatRect.intersection(globalBounds.floatRect));
    }

    /**
     * @param globalBounds the rectangle to test against
     * @return whether these bounds intersect with another bounds
     */
    @Contract(pure = true)
    public boolean intersects(@NotNull GlobalBounds globalBounds) {
        return intersection(globalBounds) != null;
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
     * @param point a 2D point
     * @return this rectangle's corner that is the closest to the specified point
     */
    @Contract(pure = true)
    public Vector2f getNearestCorner(@NotNull Vector2f point) {
        return Vec2.f(point.x - left < right - point.x ? left : right, point.y - bottom < top - point.y ? top : bottom);
    }

    @Override
    public String toString() {
        return "HitBox{" + "top=" + top + ", right=" + right + ", bottom=" + bottom + ", left=" + left + '}';
    }
}
