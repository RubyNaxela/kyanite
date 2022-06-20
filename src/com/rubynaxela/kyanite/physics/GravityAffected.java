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

package com.rubynaxela.kyanite.physics;

import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.graphics.Shape;
import com.rubynaxela.kyanite.graphics.Sprite;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

/**
 * An extension of the {@link MovingEntity} interface, providing additional functionality related to gravity.
 */
public interface GravityAffected extends MovingEntity {

    /**
     * Gets the gravity acceleration of this object. This will be used to change the velocity vector every frame.
     *
     * @return the gravity acceleration of this object
     */
    float getGravity();

    /**
     * Detects collision of this object with another axis-aligned rectangle object
     * and blocks the movement of this object so that it stops at the specified barrier.
     *
     * @param barrier   a stationary axis-aligned rectangle object
     *                  ({@link FloatRect}, {@link Shape}, or {@link Sprite})
     * @param deltaTime the time difference between the last two scene frames
     *                  (typically from the {@link AnimatedEntity#animate} method)
     */
    @Override
    default void stopAtBarrier(@NotNull Object barrier, @NotNull Time deltaTime) {
        final Direction.Axis collisionAxis = Collisions.checkAABBCollision(this, barrier, deltaTime);
        if (collisionAxis == Direction.Axis.X) setVelocity(Vec2.f(0, getVelocity().y));
        else if (collisionAxis == Direction.Axis.Y) setVelocity(Vec2.f(getVelocity().x, 0));
        if (collisionAxis == Direction.Axis.Y || collisionAxis == Direction.Axis.BOTH) Collisions.shiftToEdge(this, barrier);
    }

    /**
     * Checks whether this object is touching the specified stationary axis-aligned rectangle object while being on top of it.
     *
     * @param ground a stationary axis-aligned rectangle object
     *               ({@link FloatRect}, {@link Shape}, or {@link Sprite})
     * @return whether this object is touching {@code ground} while being on top of it
     */
    default boolean isOnGround(@NotNull Object ground) {
        final Vector2f velocity = getVelocity();
        setVelocity(Vec2.f(velocity.x, 1f));
        final Direction.Axis collisionAxis = Collisions.checkAABBCollision(this, ground, Time.ms(1));
        setVelocity(velocity);
        return collisionAxis == Direction.Axis.Y &&
               Collisions.extractBounds(this).bottom <= Collisions.extractBounds(ground).top;
    }
}
