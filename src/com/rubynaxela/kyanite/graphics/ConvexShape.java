package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Specialized shape representing a convex polygon.
 */
@SuppressWarnings("deprecation")
public class ConvexShape extends org.jsfml.graphics.ConvexShape {

    /**
     * Constructs a new empty polygon.
     */
    public ConvexShape() {
    }

    /**
     * Constructs a new empty polygon and allocates a certain amount of points. This is equal to
     * calling {@link ConvexShape#setPointCount(int)} directly after construction of the polygon.
     *
     * @param points the amount of points of the polygon
     */
    public ConvexShape(int points) {
        setPointCount(points);
    }

    /**
     * Constructs a new polygon from a given set of points. This is equal to calling
     * {@link ConvexShape#setPoints} directly after construction of the polygon.
     *
     * @param points the points of the polygon
     */
    public ConvexShape(@NotNull Vector2f... points) {
        setPoints(points);
    }

    /**
     * Sets the amount of points that belong to the polygon.
     *
     * @param pointCount the amount of points of the polygon
     */
    public void setPointCount(int pointCount) {
        nativeSetPointCount(pointCount);
        points = new Vector2f[pointCount];
        for (int i = 0; i < pointCount; i++) points[i] = Vector2f.ZERO;
    }

    /**
     * Sets a point of the polygon.
     *
     * @param i the index of the point to set. Note that this index must be within the bounds of the polygon's point
     *          count, i.e. the point count needs to be set properly using {@link ConvexShape#setPointCount(int)} first
     * @param v the point to set at the given index
     */
    public void setPoint(int i, @NotNull Vector2f v) {
        if (points == null || i < 0 || i >= points.length) throw new IndexOutOfBoundsException(Integer.toString(i));
        nativeSetPoint(i, v.x, v.y);
        points[i] = v;
    }

    @Override
    public Vector2f[] getPoints() {
        pointsNeedUpdate = false;
        return super.getPoints();
    }

    /**
     * Sets the points of the polygon. The use of this method equals consecutive calls of
     * {@link ConvexShape#setPointCount} and {@link ConvexShape#setPoint} for each point in the given array.
     *
     * @param points the points of the polygon
     */
    public void setPoints(@NotNull Vector2f... points) {
        this.points = Objects.requireNonNull(points);
        nativeSetPointCount(points.length);
        for (int i = 0; i < points.length; i++) nativeSetPoint(i, points[i].x, points[i].y);
    }
}
