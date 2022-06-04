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

package com.rubynaxela.kyanite.game.gui;

import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.game.entities.MouseActionListener;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.graphics.CircleShape;
import com.rubynaxela.kyanite.math.Vector2i;

/**
 * A {@link CircleShape} extension implementing the {@link MouseActionListener} interface with a
 * default {@link MouseActionListener#isCursorInside} method. Can be given an action to be executed
 * when a mouse button is pressed or released while the cursor is within radius of this circle.
 */
public class CircleButton extends CircleShape implements MouseActionListener {

    /**
     * Constructs an empty circle button with a zero radius, approximated using 30 points.
     */
    public CircleButton() {
    }

    /**
     * Constructs an empty circle button with the specified radius, approximated using 30 points.
     *
     * @param radius the circle's radius.
     */
    public CircleButton(float radius) {
        super(radius);
    }

    /**
     * Constructs an empty circle button with the specified parameters.
     *
     * @param radius the circle's radius.
     * @param points the amount of points to approximate the circle with.
     * @see CircleShape#setPointCount(int)
     */
    public CircleButton(float radius, int points) {
        super(radius, points);
    }

    /**
     * Detects whether or not the mouse cursor is within this button's radius.
     *
     * @param cursorPosition the mouse cursor position
     * @return whether the mouse cursor is inside this button's radius
     */
    @Override
    public boolean isCursorInside(@NotNull Vector2i cursorPosition) {
        return MathUtils.isInsideCircle(Vec2.f(cursorPosition), GlobalRect.from(getGlobalBounds()).getCenter(), getRadius());
    }
}
