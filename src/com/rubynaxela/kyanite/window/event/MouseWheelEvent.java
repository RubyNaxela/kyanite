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

/**
 * Represents mouse wheel movement events. Objects of this class are
 * created for events of type {@link Event.Type#MOUSE_WHEEL_MOVED}.
 */
public final class MouseWheelEvent extends MouseEvent {

    /**
     * The amount of ticks that the mouse wheel was moved.
     */
    public final int delta;

    /**
     * Constructs a new mouse wheel event.
     *
     * @param type  the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param x     the X coordinate of the mouse cursor relative to the window
     * @param y     the Y coordinate of the mouse cursor relative to the window
     * @param delta the amount of ticks that the mouse wheel was moved
     */
    public MouseWheelEvent(int type, int x, int y, int delta) {
        super(type, x, y);
        this.delta = delta;
    }
}
