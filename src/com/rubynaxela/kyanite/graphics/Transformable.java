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

package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

/**
 * Interface for transformable objects that can be positioned in the scene and rotated and scaled around
 * an origin. An implementation of this interface is provided by the {@link BasicTransformable} class.
 */
public interface Transformable {

    /**
     * Gets the position of this object.
     *
     * @return the current position
     */
    Vector2f getPosition();

    /**
     * Sets the position of this object in the scene so that its origin will be exactly on it.
     *
     * @param position the new position
     */
    void setPosition(@NotNull Vector2f position);

    /**
     * Sets the position of this object in the scene so that its origin will be exactly on it.
     *
     * @param x the new X coordinate
     * @param y the new Y coordinate
     */
    void setPosition(float x, float y);

    /**
     * Gets the rotation angle of this object.
     *
     * @return the current rotation angle in degrees
     */
    float getRotation();

    /**
     * Sets the rotation of this object around its origin.
     *
     * @param angle the new rotation angle in degrees
     */
    void setRotation(float angle);

    /**
     * Gets the scaling of this object.
     *
     * @return the current scaling factors
     */
    Vector2f getScale();

    /**
     * Sets the scaling of this object, using its origin as the scaling center.
     *
     * @param scale the new scaling factors
     */
    void setScale(@NotNull Vector2f scale);

    /**
     * Sets the scaling of this object, using its origin as the scaling center.
     *
     * @param factor the new scaling factor
     */
    void setScale(float factor);

    /**
     * Sets the scaling of this object, using its origin as the scaling center.
     *
     * @param x the new X scaling factor
     * @param y the new Y scaling factor
     */
    void setScale(float x, float y);

    /**
     * Gets the origin of this object.
     *
     * @return the current origin
     */
    Vector2f getOrigin();

    /**
     * Sets the rotation, scaling and drawing origin of this object.
     *
     * @param origin the new origin
     */
    void setOrigin(@NotNull Vector2f origin);

    /**
     * Sets the rotation, scaling and drawing origin of this object.
     *
     * @param x the new X coordinate of the origin
     * @param y the new Y coordinate of the origin
     */
    void setOrigin(float x, float y);

    /**
     * Moves the object.
     *
     * @param offset the offset vector added to the current position
     */
    void move(@NotNull Vector2f offset);

    /**
     * Moves this object.
     *
     * @param x the X offset added to the current position
     * @param y the Y offset added to the current position
     */
    void move(float x, float y);

    /**
     * Rotates this object around its origin.
     *
     * @param angle the rotation angle in degrees
     */
    void rotate(float angle);

    /**
     * Scales the object, using its origin as the scaling center.
     *
     * @param scale the scaling factors
     */
    void scale(@NotNull Vector2f scale);

    /**
     * Scales the object, using its origin as the scaling center.
     *
     * @param factor the scaling factor
     */
    void scale(float factor);

    /**
     * Scales the object, using its origin as the scaling center.
     *
     * @param x the X scaling factor
     * @param y the Y scaling factor
     */
    void scale(float x, float y);

    /**
     * Gets the current transformation matrix.
     *
     * @return the current transformation
     */
    Transform getTransform();

    /**
     * Gets the inverse of the current transformation matrix.
     *
     * @return the inverse of the current transform.
     */
    Transform getInverseTransform();
}
