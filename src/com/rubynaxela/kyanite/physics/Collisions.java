package com.rubynaxela.kyanite.physics;

import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.util.Direction;
import com.rubynaxela.kyanite.util.Direction.Axis;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import java.util.Collections;
import java.util.List;

/**
 * Methods useful for collision detection.
 */
public class Collisions {

    /**
     * Checks if a moving axis-aligned rectangular object would collide with other axis-aligned rectangular object (which is
     * assumed to be stationary) in the next scene frame provided its velocity would not change in the current scene frame.
     *
     * @param moving     a moving object
     * @param stationary a stationary object ({@link GlobalRect}, {@link FloatRect}, {@link Shape}, or {@link Sprite})
     * @param deltaTime  the time difference between the last two scene frames
     *                   (typically from the {@link AnimatedEntity#animate} method)
     * @return axis perpendicular to the sides that would collide with each other (the direction of ground reaction force),
     * {@code null} if the objects would not collide, or {@link Axis#BOTH} if the objects are already intersecting
     */
    @Nullable
    @Contract(pure = true)
    public static Axis checkAABBCollision(@NotNull MovingEntity moving, @NotNull Object stationary, @NotNull Time deltaTime) {
        final Vector2f offset = Vec2.multiply(moving.getVelocity(), deltaTime.asSeconds());
        final boolean horizontal = collisionHorizontal(extractGlobalRect(moving), offset.x, extractGlobalRect(stationary));
        final boolean vertical = collisionVertical(extractGlobalRect(moving), offset.y, extractGlobalRect(stationary));
        if (horizontal && vertical) return Axis.BOTH;
        if (horizontal) return Axis.X;
        if (vertical) return Axis.Y;
        return null;
    }

    /**
     * If {@code moving} intersects with {@code stationary}, moves {@code moving} away to the closest side
     * of {@code stationary} so that the objects do not intersect anymore. If the objects were not
     * intersecting, moves {@code moving} by the shortest path to {@code stationary} so that they are touching.
     *
     * @param moving     a moving axis-aligned rectangular object
     * @param stationary a stationary axis-aligned rectangular object
     */
    public static void shiftToEdge(@NotNull MovingEntity moving, @NotNull Object stationary) {
        final GlobalRect movingRect = extractGlobalRect(moving), stationaryRect = extractGlobalRect(stationary);
        final Shift shiftUp = new Shift(movingRect.bottom - stationaryRect.top, Direction.NORTH);
        final Shift shiftRight = new Shift(stationaryRect.right - movingRect.left, Direction.EAST);
        final Shift shiftDown = new Shift(stationaryRect.bottom - movingRect.top, Direction.SOUTH);
        final Shift shiftLeft = new Shift(movingRect.right - stationaryRect.left, Direction.WEST);
        final Shift shift = Collections.min(List.of(shiftUp, shiftRight, shiftDown, shiftLeft));
        if (shift.direction == Direction.NORTH) moving.move(Vec2.f(0, -shift.offset));
        else if (shift.direction == Direction.EAST) moving.move(Vec2.f(shift.offset, 0));
        else if (shift.direction == Direction.SOUTH) moving.move(Vec2.f(0, shift.offset));
        else if (shift.direction == Direction.WEST) moving.move(Vec2.f(-shift.offset, 0));
    }

    @NotNull
    @Contract(pure = true)
    static GlobalRect extractGlobalRect(@NotNull Object object) {
        final GlobalRect rectangle;
        if (object instanceof final GlobalRect rectA) rectangle = rectA;
        else if (object instanceof final FloatRect rectA) rectangle = GlobalRect.from(rectA);
        else if (object instanceof final Shape shapeA) rectangle = GlobalRect.from(shapeA.getGlobalBounds());
        else if (object instanceof final Sprite spriteA) rectangle = GlobalRect.from(spriteA.getGlobalBounds());
        else
            throw new IllegalArgumentException("Collision objects must be of one of the following " +
                                               "types: FloatRect, GlobalRect, Shape, Sprite");
        return rectangle;
    }

    @Contract(pure = true)
    private static boolean collisionHorizontal(@NotNull GlobalRect a, float dx, @NotNull GlobalRect b) {
        return a.left + dx < b.right && a.right + dx > b.left && !(a.top >= b.bottom || a.bottom <= b.top);
    }

    @Contract(pure = true)
    private static boolean collisionVertical(@NotNull GlobalRect a, float dy, @NotNull GlobalRect b) {
        return a.top + dy < b.bottom && a.bottom + dy > b.top && !(a.left >= b.right || a.right <= b.left);
    }

    private record Shift(float offset, @NotNull Direction direction) implements Comparable<Shift> {

        @Override
        public int compareTo(@NotNull Shift other) {
            return Float.compare(offset, other.offset);
        }
    }
}
