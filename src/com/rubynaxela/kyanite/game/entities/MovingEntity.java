package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.physics.Collisions;
import com.rubynaxela.kyanite.util.Direction;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.Sprite;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.core.system.Vector2f;

/**
 * Objects that implement {@code MovingEntity} are being moved automatically by the scene loop.
 * This interface also provides the ability to limit available movement area through barriers.
 */
public interface MovingEntity {

    /**
     * @return the velocity vector of this object
     */
    @NotNull
    Vector2f getVelocity();

    /**
     * Sets the velocity vector of this object. This object will move automatically with this velocity.
     *
     * @param velocity the new velocity vector of this object
     */
    void setVelocity(@NotNull Vector2f velocity);

    /**
     * Moves this object by the specified offset.
     *
     * @param offset the offset vector added to the current position.
     */
    void move(@NotNull Vector2f offset);

    /**
     * Detects collision of this object with another axis-aligned rectangle object
     * and blocks the movement of this object so that it stops at the specified barrier.
     *
     * @param barrier   a stationary axis-aligned rectangle object ({@link GlobalRect},
     *                  {@link FloatRect}, {@link Shape}, or {@link Sprite})
     * @param deltaTime the time difference between the last two scene frames
     *                  (typically from the {@link AnimatedEntity#animate} method)
     */
    default void stopAtBarrier(@NotNull Object barrier, @NotNull Time deltaTime) {
        final Direction.Axis collisionAxis = Collisions.checkAABBCollision(this, barrier, deltaTime);
        if (collisionAxis == Direction.Axis.X) setVelocity(Vec2.f(0, getVelocity().y));
        else if (collisionAxis == Direction.Axis.Y) setVelocity(Vec2.f(getVelocity().x, 0));
        else if (collisionAxis == Direction.Axis.BOTH) Collisions.shiftToEdge(this, barrier);
    }
}
