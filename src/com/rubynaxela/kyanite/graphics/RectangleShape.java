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
 * A specialized shape representing a rectangle.
 */
@SuppressWarnings("deprecation")
public class RectangleShape extends org.jsfml.graphics.RectangleShape {

    private Vector2f size = Vector2f.ZERO;

    /**
     * Constructs a new rectangle shape with no dimensions.
     */
    public RectangleShape() {
    }

    /**
     * Constructs a new rectangle shape with the specified dimensions.
     *
     * @param size the rectangle's dimensions
     */
    public RectangleShape(@NotNull Vector2f size) {
        setSize(size);
    }

    /**
     * Gets the dimensions of the rectangle.
     *
     * @return the dimensions of the rectangle
     */
    public Vector2f getSize() {
        return size;
    }

    /**
     * Sets the dimensions of the rectangle.
     *
     * @param size the new dimensions of the rectangle
     */
    public void setSize(Vector2f size) {
        nativeSetSize(size.x, size.y);
        this.size = size;
        pointsNeedUpdate = true;
    }
}
