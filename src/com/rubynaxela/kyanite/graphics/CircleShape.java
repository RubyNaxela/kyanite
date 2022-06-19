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

/**
 * A specialized shape representing a circle.
 */
@SuppressWarnings("deprecation")
public class CircleShape extends org.jsfml.graphics.CircleShape {

    private float radius = 0;

    /**
     * Constructs a new circle shape with a zero radius, approximated using 30 points.
     */
    public CircleShape() {
    }

    /**
     * Constructs a new circle shape with the specified radius, approximated using 30 points.
     *
     * @param radius the circle's radius
     */
    public CircleShape(float radius) {
        this(radius, false);
    }

    /**
     * Constructs a new circle shape with the specified radius, approximated using 30 points.
     *
     * @param radius   the circle's radius
     * @param centered whether this circle shape has to be kept centered
     * @see Shape#setCentered(boolean)
     */
    public CircleShape(float radius, boolean centered) {
        setRadius(radius);
        setCentered(centered);
    }

    /**
     * Constructs a new circle shape with the specified parameters.
     *
     * @param radius the circle's radius
     * @param points the amount of points to approximate the circle with
     * @see CircleShape#setPointCount(int)
     */
    public CircleShape(float radius, int points) {
        this(radius, points, false);
    }

    /**
     * Constructs a new circle shape with the specified parameters.
     *
     * @param radius   the circle's radius
     * @param points   the amount of points to approximate the circle with
     * @param centered whether this circle shape has to be kept centered
     * @see CircleShape#setPointCount(int)
     * @see Shape#setCentered(boolean)
     */
    public CircleShape(float radius, int points, boolean centered) {
        this(radius);
        setPointCount(points);
        setCentered(centered);
    }

    /**
     * Gets the radius of this circle.
     *
     * @return the radius of this circle shape
     */
    public float getRadius() {
        return radius;
    }

    /**
     * Sets the radius of this circle.
     *
     * @param radius the new radius of the circle shape
     */
    public void setRadius(float radius) {
        nativeSetRadius(radius);
        updateOrigin(keepCentered);
        this.radius = radius;
        pointsNeedUpdate = true;
        boundsNeedUpdate = true;
    }

    /**
     * Sets the amount of points the circle should be approximated with. A higher
     * amount of points will yield a smoother result at the cost of performance.
     *
     * @param count the amount of points used to approximate the circle
     */
    public void setPointCount(int count) {
        nativeSetPointCount(count);
        updateOrigin(keepCentered);
        pointsNeedUpdate = true;
        boundsNeedUpdate = true;
    }
}
