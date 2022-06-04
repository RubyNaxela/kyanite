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

package com.rubynaxela.kyanite.math;

import org.jetbrains.annotations.NotNull;

/**
 * A simple enum for representing the four cardinal and four intercardinal directions on a two-dimensional plane.
 */
public enum Direction {
    /**
     * The north direction.
     */
    NORTH(Axis.Y),
    /**
     * The north-east direction.
     */
    NORTH_EAST(Axis.BOTH),
    /**
     * The east direction.
     */
    EAST(Axis.X),
    /**
     * The south-east direction.
     */
    SOUTH_EAST(Axis.BOTH),
    /**
     * The south direction.
     */
    SOUTH(Axis.Y),
    /**
     * The south-west direction.
     */
    SOUTH_WEST(Axis.BOTH),
    /**
     * The west direction.
     */
    WEST(Axis.X),
    /**
     * The north-west direction.
     */
    NORTH_WEST(Axis.BOTH);

    public final Axis axis;

    Direction(@NotNull Axis axis) {
        this.axis = axis;
    }

    /**
     * Two axes of a 2D plane - X and Y
     */
    public enum Axis {
        /**
         * The X-axis of a 2D plane
         */
        X,
        /**
         * The Y-axis of a 2D plane
         */
        Y,
        /**
         * If the X and Y values are used to indicate that a vector is parallel with one of the
         * axes of the cartesian coordinate system, BOTH means that the vector is not parallel to
         * any axis (such a vector can be decomposed into two non-zero axis-aligned components).
         */
        BOTH
    }
}
