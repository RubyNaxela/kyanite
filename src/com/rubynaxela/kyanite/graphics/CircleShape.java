package com.rubynaxela.kyanite.graphics;

/**
 * A specialized shape representing a circle.
 */
@SuppressWarnings("deprecation")
public class CircleShape extends org.jsfml.graphics.CircleShape {

    private float radius = 0;

    /**
     * Constructs a new circle shape with a zero radius, approximated using 30 points.
     */
    public CircleShape() {
    }

    /**
     * Constructs a new circle shape with the specified radius, approximated using 30 points.
     *
     * @param radius the circle's radius
     */
    public CircleShape(float radius) {
        setRadius(radius);
    }

    /**
     * Constructs a new circle shape with the specified parameters.
     *
     * @param radius the circle's radius
     * @param points the amount of points to approximate the circle with
     * @see CircleShape#setPointCount(int)
     */
    public CircleShape(float radius, int points) {
        this(radius);
        setPointCount(points);
    }

    /**
     * Gets the radius of this circle.
     *
     * @return the radius of this circle shape
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the radius of this circle.
     *
     * @param radius the new radius of the circle shape
     */
    public void setRadius(float radius) {
        nativeSetRadius(radius);
        this.radius = radius;
        pointsNeedUpdate = true;
    }

    /**
     * Sets the amount of points the circle should be approximated with. A higher
     * amount of points will yield a smoother result at the cost of performance.
     *
     * @param count the amount of points used to approximate the circle
     */
    public void setPointCount(int count) {
        nativeSetPointCount(count);
        pointsNeedUpdate = true;
    }
}
