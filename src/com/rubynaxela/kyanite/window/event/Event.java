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

import com.rubynaxela.kyanite.window.BasicWindow;
import org.intellij.lang.annotations.MagicConstant;

/**
 * Base class for window events. Window events must be polled from a window on a regular
 * basis to keep the window responsive. Events are of a certain type, some of which
 * provide additional data. For various special types, subclasses of this class exist.
 *
 * @see BasicWindow#pollEvents()
 * @see Type
 */
public class Event {

    /**
     * The type of the window event.
     *
     * @see Type
     */
    public final Type type;

    /**
     * Constructs a new event.
     *
     * @param type the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     */
    public Event(@MagicConstant(valuesFromClass = Type.class) int type) {
        this.type = Type.values()[type];
    }

    /**
     * Enumeration of available window event types.
     */
    public enum Type {
        /**
         * Fired when the user clicked on the window's close button.
         */
        CLOSED,
        /**
         * Fired when the window was resized.
         */
        RESIZED,
        /**
         * Fired when the window lost focus.
         */
        LOST_FOCUS,
        /**
         * Fired when the window gained focus.
         */
        GAINED_FOCUS,
        /**
         * Fired when a text character was entered using the keyboard while the window had focus.
         */
        TEXT_ENTERED,
        /**
         * Fired when a keyboard key was pressed while the window had focus.
         */
        KEY_PRESSED,
        /**
         * Fired when a keyboard key was released while the window had focus.
         */
        KEY_RELEASED,
        /**
         * Fired when the mouse wheel was moved while the window had focus.
         */
        MOUSE_WHEEL_MOVED,
        /**
         * Fired when a mouse button was pressed while the window had focus.
         */
        MOUSE_BUTTON_PRESSED,
        /**
         * Fired when a mouse button was released while the window had focus.
         */
        MOUSE_BUTTON_RELEASED,
        /**
         * Fired when the mouse cursor was moved within the window's boundaries.
         */
        MOUSE_MOVED,
        /**
         * Fired when the mouse cursor entered the window's boundaries.
         */
        MOUSE_ENTERED,
        /**
         * Fired when the mouse cursor left the window's boundaries.
         */
        MOUSE_LEFT,
        /**
         * Fired when a joystick or gamepad button was pressed while the window had focus.
         */
        JOYSTICK_BUTTON_PRESSED,
        /**
         * Fired when a joystick or gamepad button was released while the window had focus.
         */
        JOYSTICK_BUTTON_RELEASED,
        /**
         * Fired when a joystick or gamepad axis was moved while the window had focus.
         */
        JOYSTICK_MOVED,
        /**
         * Fired when a joystick or gamepad was connected.
         */
        JOYSTICK_CONNECETED,
        /**
         * Fired when a joystick or gamepad was disconnected.
         */
        JOYSTICK_DISCONNECTED
    }
}
