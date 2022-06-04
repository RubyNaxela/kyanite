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

import com.rubynaxela.kyanite.core.Const;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vector2f;

/**
 * Interface for read-only views. It provides methods to can gain information from a view,
 * but none to modify it in any way. Note that this interface is expected to be implemented
 * by a {@link View}. It is not recommended to be implemented outside of the Kyanite API.
 *
 * @see Const
 */
public interface ConstView extends Const {

    /**
     * Gets the current center of the view.
     *
     * @return the current center of the view
     */
    Vector2f getCenter();

    /**
     * Gets the current dimensions of the view in pixels.
     *
     * @return the current dimensions of the view in pixels
     */
    Vector2f getSize();

    /**
     * Gets the current rotation angle of the view in degrees.
     *
     * @return the current rotation angle of the view in degrees
     */
    float getRotation();

    /**
     * Gets the current viewport rectangle of this view.
     *
     * @return the current viewport rectangle of this view
     */
    FloatRect getViewport();

    /**
     * Gets the view's current transformation matrix as determined by its center,
     * size and rotation angle.
     *
     * @return the view's current transformation matrix
     */
    Transform getTransform();

    /**
     * Gets the inverse of the view's current transformation matrix as determined
     * by its center, size and rotation angle.
     *
     * @return the inverse of the view's current transformation matrix
     */
    Transform getInverseTransform();
}
