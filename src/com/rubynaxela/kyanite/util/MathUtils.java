package com.rubynaxela.kyanite.util;

import com.rubynaxela.kyanite.game.entities.GlobalRect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Vector2f;

public final class MathUtils {

    private MathUtils() {
    }

    /**
     * Returns the smaller of two numeric values. If the arguments have the same value, the result is that same value.
     * If either value is NaN, then the result is also NaN. If exactly one of the arguments is {@code null}, the result
     * is the other argument, whereas if both values are {@code null}, then the result is also {@code null}.
     *
     * @param a first operand
     * @param b second operand
     * @return the smaller of {@code a} and {@code b}
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static <T extends Number> T min(T a, T b) {
        final Pair<Boolean, T> checkNullAndNaNResult = checkNullAndNaN(a, b);
        if (checkNullAndNaNResult.value1()) return checkNullAndNaNResult.value2();
        else return a.doubleValue() <= b.doubleValue() ? a : b;
    }

    /**
     * Returns the bigger of two numeric values. If the arguments have the same value, the result is that same value.
     * If either value is NaN, then the result is also NaN. If exactly one of the arguments is {@code null}, the result
     * is the other argument, whereas if both values are {@code null}, then the result is also {@code null}.
     *
     * @param a first operand
     * @param b second operand
     * @return the bigger of {@code a} and {@code b}
     */
    @Contract(pure = true, value = "_, _ -> new")
    public static <T extends Number> T max(T a, T b) {
        final Pair<Boolean, T> checkNullAndNaNResult = checkNullAndNaN(a, b);
        if (checkNullAndNaNResult.value1()) return checkNullAndNaNResult.value2();
        else return a.doubleValue() >= b.doubleValue() ? a : b;
    }

    /**
     * Returns {@code true} with given probability using the {@link java.util.Random} pseudorandom-number generator.
     *
     * @param probability the probability that returned value is {@code true}
     * @return {@code true} with the probability of the parameter value
     */
    @Contract(pure = true)
    public static boolean probability(float probability) {
        return Math.random() <= probability;
    }

    /**
     * @param origin the origin point
     * @param target the target point
     * @return the direction unit vector (normalzed) from the origin to the target
     * point, or {@link Vector2f#ZERO} if the specified points have the same coordinates
     */
    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f direction(@NotNull Vector2f origin, @NotNull Vector2f target) {
        if (origin.equals(target)) return Vector2f.ZERO;
        final float delta_x = origin.x - target.x, delta_y = origin.y - target.y,
                distance = (float) Math.sqrt(delta_x * delta_x + delta_y * delta_y);
        return Vec2.f(delta_x / distance, delta_y / distance);
    }

    /**
     * @param circle          a circle shape
     * @param circleDirection the circle shape's moving direction
     * @param rectangle       a rectangle shape
     * @return the new {@code circle}'s direction vector moving in the direction of {@code circleDirection}
     * if it collides with {@code rectangle}, or the current {@code circle}'s direction vector otherwise
     */
    @NotNull
    @Contract(pure = true, value = "_, _, _ -> new")
    public static Vector2f collisionDirection(@NotNull CircleShape circle, @NotNull Vector2f circleDirection,
                                              @NotNull RectangleShape rectangle) {

        final Vector2f circlePosition = circle.getPosition();
        final GlobalRect rectangleBounds = GlobalRect.from(rectangle.getGlobalBounds());

        final boolean horizontalCollision = rectangleBounds.isOnTheLeft(circlePosition) ||
                                            rectangleBounds.isOnTheRight(circlePosition);
        final boolean verticalCollision = rectangleBounds.isAbove(circlePosition) ||
                                          rectangleBounds.isBelow(circlePosition);
        final boolean cornerCollision = horizontalCollision && verticalCollision;

        if (cornerCollision) {
            final Vector2f brickNearestCorner = rectangleBounds.getNearestCorner(circlePosition);
            final float angle = (float) (2 * Math.atan2(brickNearestCorner.y - circlePosition.y,
                                                        circlePosition.x - brickNearestCorner.x)
                                         - Math.atan2(circleDirection.y, -circleDirection.x));
            return Vec2.f(Math.cos(angle), -Math.sin(angle));
        } else if (horizontalCollision)
            return Vec2.f((rectangleBounds.isOnTheRight(circlePosition) ? 1.0f : -1.0f) * Math.abs(circleDirection.x),
                          circleDirection.y);
        else if (verticalCollision)
            return Vec2.f(circleDirection.x,
                          (rectangleBounds.isBelow(circlePosition) ? 1.0f : -1.0f) * Math.abs(circleDirection.y));
        else return circleDirection;
    }

    private static <T extends Number> Pair<Boolean, T> checkNullAndNaN(T a, T b) {
        if (a != null && b == null) return new Pair<>(true, a);
        else if (a == null) return new Pair<>(true, b);
        else {
            final double aDouble = a.doubleValue(), bDouble = b.doubleValue();
            if (aDouble != aDouble) return new Pair<>(true, a);
            else if (bDouble != bDouble) return new Pair<>(true, b);
            else return new Pair<>(false, null);
        }
    }
}
