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

import com.rubynaxela.kyanite.input.Mouse;

/**
 * Represents mouse button events. Objects of this class are created for events of type
 * {@link Event.Type#MOUSE_BUTTON_PRESSED} or {@link Event.Type#MOUSE_BUTTON_RELEASED}.
 */
public final class MouseButtonEvent extends MouseEvent {

    /**
     * The mouse button that was pressed or released.
     */
    public final Mouse.Button button;

    /**
     * Constructs a new mouse button event.
     *
     * @param type   the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param x      the X coordinate of the mouse cursor relative to the window
     * @param y      the Y coordinate of the mouse cursor relative to the window
     * @param button the button that was pressed (must be a valid ordinal
     *               in the {@link Mouse.Button} enumeration)
     */
    public MouseButtonEvent(int type, int x, int y, int button) {
        super(type, x, y);
        this.button = Mouse.Button.values()[button];
    }
}
