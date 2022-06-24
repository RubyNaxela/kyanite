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

package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.graphics.Shape;
import com.rubynaxela.kyanite.graphics.Sprite;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.physics.Collisions;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

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
     * @param barrier   a stationary axis-aligned rectangle object ({@link FloatRect}, {@link Shape}, or {@link Sprite})
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
