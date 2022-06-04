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
import com.rubynaxela.kyanite.graphics.CircleShape;
import com.rubynaxela.kyanite.math.MathUtils;
import com.rubynaxela.kyanite.math.Vec2;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.graphics.RectangleShape;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector2i;

/**
 * A {@link RectangleShape} extension implementing the {@link MouseActionListener} interface with a
 * default {@link MouseActionListener#isCursorInside} method. Can be given an action to be executed
 * when a mouse button is pressed or released while the cursor is within bounds of this rectangle.
 */
public class RectangleButton extends RectangleShape implements MouseActionListener {

    /**
     * Constructs an empty button with no dimension.
     */
    public RectangleButton() {
    }

    /**
     * Constructs an empty button with the specified size.
     *
     * @param size the button's size
     */
    public RectangleButton(Vector2f size) {
        super(size);
    }

    /**
     * Detects whether or not the mouse cursor is within this button's radius assuming the shape of this button is a circle.
     *
     * @param cursorPosition the mouse cursor position
     * @return whether the mouse cursor is inside this button's radius
     */
    public final boolean isCursorInsideCircle(@NotNull Vector2i cursorPosition) {
        final FloatRect bounds = getGlobalBounds();
        if (bounds.width != bounds.height)
            throw new IllegalStateException("For a RectangleButton to be considered a circle, " +
                                            "it must be the same width and height");
        return MathUtils.isInsideCircle(Vec2.f(cursorPosition), GlobalRect.from(bounds).getCenter(),
                                        getGlobalBounds().width / 2);
    }

    /**
     * Detects whether or not the mouse cursor is inside this button's global bounds. This method should be overridden
     * if the shape of the texture applied on this button is different than a rectangle matching this button's size.
     * For instance, if the entity is circle-shaped and its origin in its middle, this method may look like this:<pre>
     * &#64;Override
     * public boolean isCursorInside(@NotNull Vector2i cursorPosition) {
     *     return isCursorInsideCircle(cursorPosition);
     * }</pre>
     *
     * @param cursorPosition the mouse cursor position
     * @return whether the mouse cursor is inside this button's global bounds
     */
    @Override
    public boolean isCursorInside(@NotNull Vector2i cursorPosition) {
        return getGlobalBounds().contains(Vec2.f(cursorPosition));
    }
}