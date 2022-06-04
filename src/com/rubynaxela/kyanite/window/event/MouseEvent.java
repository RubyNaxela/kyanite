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

package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.math.Vector2i;

/**
 * Represents generic mouse events. Objects of this class are created for events of type
 * {@link Event.Type#MOUSE_ENTERED}, {@link Event.Type#MOUSE_MOVED},
 * {@link Event.Type#MOUSE_LEFT}, {@link Event.Type#MOUSE_WHEEL_MOVED},
 * {@link Event.Type#MOUSE_BUTTON_PRESSED} or {@link Event.Type#MOUSE_BUTTON_RELEASED}.
 */
public class MouseEvent extends Event {

    /**
     * The position of the mouse pointer in pixels, relative to the window's top left corner.
     */
    public final Vector2i position;

    /**
     * /**
     * Constructs a new mouse button event.
     *
     * @param type the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param x    the X coordinate of the mouse cursor relative to the window
     * @param y    the Y coordinate of the mouse cursor relative to the window
     */
    public MouseEvent(int type, int x, int y) {
        super(type);
        this.position = new Vector2i(x, y);
    }
}
