package com.rubynaxela.kyanite.util;

import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.math.Vec2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Transform;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector2i;

import java.util.Random;

/**
 * A set of mathematical constants, functions and algorithms.
 */
public final class MathUtils {

    private static final Random random = new Random();
    /**
     * The value of &Sqrt;2
     */
    public static float SQRT2 = (float) Math.sqrt(2);
    /**
     * The value of &Sqrt;2/2
     */
    public static float SQRT2_2 = (float) Math.sqrt(2) / 2;
    /**
     * The value of 1/&Sqrt;2
     */
    public static float INV_SQRT2 = 1 / (float) Math.sqrt(2);
    /**
     * The value of &Sqrt;3
     */
    public static float SQRT3 = (float) Math.sqrt(3);
    /**
     * The value of &Sqrt;3/3
     */
    public static float SQRT3_3 = (float) Math.sqrt(3) / 3;
    /**
     * The value of 1/&Sqrt;3
     */
    public static float INV_SQRT3 = 1 / (float) Math.sqrt(3);

    private MathUtils() {
    }

    /**
     * Returns the smaller of two numeric values. Both operands must be of the same type. If the
     * arguments have the same value, the result is that same value. If either value is NaN, then
     * the result is also NaN. If exactly one of the arguments is {@code null}, the result is the
     * other argument, whereas if both values are {@code null}, then the result is also {@code null}.
     *
     * @param <T> the numbers type
     * @param a   first operand
     * @param b   second operand
     * @return the smaller of {@code a} and {@code b}
     */
    @Contract(pure = true, value = "!null, !null -> !null; !null, null -> param1; null, !null -> param2; null, null -> null")
    public static <T extends Number & Comparable<T>> T min(T a, T b) {
        final Pair<Boolean, T> checkNullAndNaNResult = checkNullAndNaN(a, b);
        if (checkNullAndNaNResult.value1()) return checkNullAndNaNResult.value2();
        else return a.compareTo(b) <= 0 ? a : b;
    }

    /**
     * Returns the bigger of two numeric values. Both operands must be of the same type. If the
     * arguments have the same value, the result is that same value. If either value is NaN, then
     * the result is also NaN. If exactly one of the arguments is {@code null}, the result is the
     * other argument, whereas if both values are {@code null}, then the result is also {@code null}.
     *
     * @param <T> the numbers type
     * @param a   first operand
     * @param b   second operand
     * @return the bigger of {@code a} and {@code b}
     */
    @Contract(pure = true, value = "!null, !null -> !null; !null, null -> param1; null, !null -> param2; null, null -> null")
    public static <T extends Number & Comparable<T>> T max(T a, T b) {
        final Pair<Boolean, T> checkNullAndNaNResult = checkNullAndNaN(a, b);
        if (checkNullAndNaNResult.value1()) return checkNullAndNaNResult.value2();
        else return a.compareTo(b) >= 0 ? a : b;
    }

    /**
     * Constraints the specified value to the specified bounds. If {@code value} is less than {@code lowerBound}, then
     * {@code lowerBound} is returned. If {@code value} is greater than {@code upperBound}, then {@code upperBound} is returned.
     * All arguments must be of the same type. If any argument is NaN, then the result is also NaN. If either bound is
     * {@code null}, it is ignored. If {@code value} is {@code null}, {@code lowerBound} is returned. If {@code value} and
     * {@code lowerBound} is {@code null}, {@code upperBound} is returned. If all three parameters are {@code null}, then the
     * result is also {@code null}.
     *
     * @param <T>        the numbers type
     * @param value      the value to be constrained
     * @param lowerBound the lower bound (inclusive)
     * @param upperBound the upper bound (inclusive)
     * @return the specified value, unless it is outside the specified bounds, in which case the closer bound is returned
     * @throws IllegalArgumentException when {@code lowerBound} is greater than {@code upperBound}
     */
    @Contract(pure = true, value = "!null, !null, !null -> !null; !null, !null, null -> !null; !null, null, !null -> !null; " +
                                   "!null, null, null -> param1; null, !null, !null -> !null; null, !null, null -> param2; " +
                                   "null, null, !null -> param3; null, null, null -> null;")
    public static <T extends Number & Comparable<T>> T clamp(T value, T lowerBound, T upperBound) {
        if (lowerBound != null && upperBound != null && lowerBound.compareTo(upperBound) > 0)
            throw new IllegalArgumentException("lowerBound cannot be greater than upperBound");
        return min(max(value, lowerBound), upperBound);
    }

    /**
     * Performs a power operation by iterative multiplication. This method, unlike {@link Math#pow},
     * is guaranteed to yield a mathematically correct result. However, if result is too big or too small
     * to be stored in a {@code long} variable, {@code Long.MAX_VALUE} or {@code Long.MIN_VALUE} is returned.
     *
     * @param x the base
     * @param n the exponent, must be positive or zero
     * @return {@code x^n}; the {@code n}th power of {@code x}
     */
    @Contract(pure = true)
    public static long pow(long x, int n) {
        if (n < 0) throw new ArithmeticException("The exponent in integer exponentiation cannot be negative");
        if (x > 1 && n > 62 || x < -1 && n > 64 && n % 2 == 0) return Long.MAX_VALUE;
        if (x < -1 && n > 63 && n % 2 == 1) return Long.MIN_VALUE;
        long result = 1;
        while (true) {
            if ((n & 1) == 1) result *= x;
            n >>= 1;
            if (n == 0) break;
            x *= x;
        }
        return result;
    }

    /**
     * Converts an angle value in degrees to a value in radians. Unlike
     * {@link Math#toRadians}, this method operates on {@code float} values.
     *
     * @param angle an angle value in degrees
     * @return the specified angle in radians
     */
    @Contract(pure = true)
    public static float degToRad(float angle) {
        return angle * ((float) Math.PI) / 180.0f;
    }

    /**
     * Converts an angle value in radians to a value in degrees. Unlike
     * {@link Math#toDegrees}, this method operates on {@code float} values.
     *
     * @param angle an angle value in radians
     * @return the specified angle in degrees
     */
    @Contract(pure = true)
    public static float radToDeg(float angle) {
        return angle / ((float) Math.PI) * 180.0f;
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
     * Returns a pseudorandomly chosen {@code int} from the specified range.
     *
     * @param lowerBound the range lower bound (inclusive)
     * @param upperBound the range upper bound (exclusive)
     * @return a pseudorandom integer from the specified range
     */
    @Contract(pure = true)
    public static int randomInt(int lowerBound, int upperBound) {
        return random.nextInt(upperBound - lowerBound) + lowerBound;
    }

    /**
     * Returns a pseudorandomly chosen {@code float} from the specified range.
     *
     * @param lowerBound the range lower bound (inclusive)
     * @param upperBound the range upper bound (exclusive)
     * @return a pseudorandom float from the specified range
     */
    @Contract(pure = true)
    public static float randomFloat(float lowerBound, float upperBound) {
        return random.nextFloat() * (upperBound - lowerBound) + lowerBound;
    }

    /**
     * Combines transformation matrices by multiplying them.
     *
     * @param transforms the transformation matrices
     * @return the product of the specified matrices
     */
    @Contract(pure = true, value = "_ -> new")
    public static Transform combineTransforms(@NotNull Transform... transforms) {
        Transform result = Transform.IDENTITY;
        for (final Transform transform : transforms) result = Transform.combine(result, transform);
        return result;
    }

    /**
     * Computes the distance between the specified points using Pythagorean theorem.
     *
     * @param origin the target point
     * @param target the origin point
     * @return the distance between the two points
     */
    @Contract(pure = true)
    public static float distance(@NotNull Vector2f origin, @NotNull Vector2f target) {
        final float deltaX = target.x - origin.x, deltaY = target.y - origin.y;
        return (float) Math.hypot(deltaX, deltaY);
    }

    /**
     * Returns the taxicab (Manhattan geometry) distance between two points, which is the result of the following expression:
     * <code>Math.abs(a.x - b.x) + Math.abs(a.x - b.y)</code>
     *
     * @param a first point
     * @param b second point
     * @return the taxicab distance between the points
     */
    @Contract(pure = true)
    public static int taxicabDistance(Vector2i a, Vector2i b) {
        return Math.abs(a.x - b.x) + Math.abs(a.x - b.y);
    }

    /**
     * Detects whether or not the specified point is contained within a circle of the specified middle point and radius.
     *
     * @param point          a point
     * @param circlePosition the middle point of the circle
     * @param circleRadius   the radius of the circle
     * @return whether the specified point is contained within the specified circle
     */
    public static boolean isInsideCircle(@NotNull Vector2f point, @NotNull Vector2f circlePosition, float circleRadius) {
        final float deltaX = point.x - circlePosition.x;
        final float deltaY = point.y - circlePosition.y;
        return deltaX * deltaX + deltaY * deltaY <= circleRadius * circleRadius;
    }

    /**
     * Returns the specified number formatted in "kmbtqp" notation with one decimal place,
     * rounded down (if the number is positive) or up (if the number is negative). Examples:
     * <ul>
     *     <li>{@code shortNotation(72457) // "72.4k"}</li>
     *     <li>{@code shortNotation(2489697245700) // "2.4q"}</li>
     *     <li>{@code shortNotation(198345134672) // "198.3b"}</li>
     *     <li>{@code shortNotation(129) // "129"}</li>
     *     <li>{@code shortNotation(724000619032) // "724b"}</li>
     *     <li>{@code shortNotation(82745824) // "-82.7m"}</li>
     *     <li>{@code shortNotation(1892722390) // "-1.8t"}</li>
     * </ul>
     *
     * @param number a number
     * @return the specified number formatted in "kmbtqp" notation with one decimal place
     */
    @Contract(pure = true)
    public static String shortNotation(long number) {
        if (number < 0) return "-" + shortNotation(-number);
        if (number < 1000) return "" + number;
        final String[] suffixes = {"k", "m", "b", "t", "q", "p"};
        final int powerOf1000 = (("" + number).length() - 1) / 3;
        final String shortNotation = "" + number / pow(10, powerOf1000 * 3 - 1);
        final char decimalPlace = shortNotation.charAt(shortNotation.length() - 1);
        return shortNotation.substring(0, shortNotation.length() - 1) +
               (decimalPlace != '0' ? "." + decimalPlace : "") + suffixes[powerOf1000 - 1];
    }

    /**
     * Computes the direction unit vector (normalzed) for going in the direction of the specified
     * angle (in degrees; 0 means upward and the direction of increasing the angle is clockwise).
     *
     * @param angle the angle relative to the Y axis
     * @return normalzed direction for going in the direction of the specified angle
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vector2f direction(float angle) {
        return Vec2.f((float) Math.sin(degToRad(angle)), (float) -Math.cos(degToRad(angle)));
    }

    /**
     * Computes the direction unit vector (normalzed) from the origin to the target
     * point, or {@link Vector2f#ZERO} if the specified points have the same coordinates
     *
     * @param origin the target point
     * @param target the origin point
     * @return normalzed direction vector from the origin to the target
     */
    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vector2f direction(@NotNull Vector2f origin, @NotNull Vector2f target) {
        if (target.equals(origin)) return Vector2f.ZERO;
        final float delta_x = target.x - origin.x, delta_y = target.y - origin.y,
                distance = (float) Math.sqrt(delta_x * delta_x + delta_y * delta_y);
        return Vec2.f(delta_x / distance, delta_y / distance);
    }

    /**
     * Computes the direction vector of a {@link CircleShape} moving in the specified direction after it collides with a
     * {@link RectangleShape}. If {@code circle} and {@code rectangle} are not colliding, {@code circleDirection} is returned.
     *
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

    /**
     * Elementary transformations of the identity matrix.
     */
    public static final class ElementaryTransform {

        private ElementaryTransform() {
        }

        /**
         * The rotation transformation.
         *
         * @param angle the rotation angle
         * @return the identity matrix after the rotation transformation
         */
        public static Transform rotation(float angle) {
            return Transform.rotate(Transform.IDENTITY, angle);
        }

        /**
         * The scale transformation.
         *
         * @param factor the x and y scale factor
         * @return the identity matrix after the scale transformation
         */
        public static Transform scale(float factor) {
            return Transform.scale(Transform.IDENTITY, factor, factor);
        }

        /**
         * The scale transformation.
         *
         * @param xFactor the x scale factor
         * @param yFactor the y scale factor
         * @return the identity matrix after the scale transformation
         */
        public static Transform scale(float xFactor, float yFactor) {
            return Transform.scale(Transform.IDENTITY, xFactor, yFactor);
        }

        /**
         * The scale transformation.
         *
         * @param factors the scale factors vector
         * @return the identity matrix after the scale transformation
         */
        public static Transform scale(@NotNull Vector2f factors) {
            return Transform.scale(Transform.IDENTITY, factors);
        }

        /**
         * The translation transformation.
         *
         * @param xOffset the x translation offset
         * @param yOffset the y translation offset
         * @return the identity matrix after the translation transformation
         */
        public static Transform translation(float xOffset, float yOffset) {
            return Transform.translate(Transform.IDENTITY, xOffset, yOffset);
        }

        /**
         * The translation transformation.
         *
         * @param offset the translation vector
         * @return the identity matrix after the translation transformation
         */
        public static Transform translation(@NotNull Vector2f offset) {
            return Transform.translate(Transform.IDENTITY, offset);
        }
    }
}
