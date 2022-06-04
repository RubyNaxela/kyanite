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

import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.window.event.MouseButtonEvent;

/**
 * This interface serves the purpose of simplifying the handling of actions triggered by the mouse buttons.
 */
public interface MouseActionListener {

    /**
     * This method should be implemented in such a way that it returns {@code true} or {@code false} depending on whether the
     * mouse cursor position is inside the object global bounds. This method is necessary because not every entity has to have
     * rectangular bounds. For instance, if the entity is circle-shaped and its origin in its middle, this method may look
     * like this:<pre>
     * boolean isCursorInside(@NotNull Vector2i cursorPosition) {
     *     final float deltaX = cursorPosition.x - getPosition().x;
     *     final float deltaY = cursorPosition.y - getPosition().y;
     *     final float r = getRadius();
     *     return deltaX * deltaX + deltaY * deltaY &lt;= r * r;
     * }</pre>
     *
     * @param cursorPosition the mouse cursor position
     * @return whether the specified position is inside the object global bounds
     */
    boolean isCursorInside(@NotNull Vector2i cursorPosition);

    /**
     * Invoked when a mouse button was pressed, this object belongs to the
     * window's current scene and the {@link #isCursorInside} returned {@code true}.
     *
     * @param event the event to be processed
     */
    default void mouseButtonPressed(@NotNull MouseButtonEvent event) {
    }

    /**
     * Invoked when a mouse button was released, this object belongs to the
     * window's current scene and the {@link #isCursorInside} returned {@code true}.
     *
     * @param event the event to be processed
     */
    default void mouseButtonReleased(@NotNull MouseButtonEvent event) {
    }
}
