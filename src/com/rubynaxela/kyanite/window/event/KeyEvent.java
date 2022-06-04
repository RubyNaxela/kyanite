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

import com.rubynaxela.kyanite.input.Keyboard;

/**
 * Represents keyboard key events. Objects of this class are created for events
 * of type {@link Event.Type#KEY_PRESSED} or {@link Event.Type#KEY_RELEASED}.
 */
public final class KeyEvent extends Event {

    /**
     * The code of the key that was pressed or released.
     */
    public final Keyboard.Key key;

    /**
     * {@code true} if an {@code ALT} key (left or right) was pressed when the event occured, {@code false} if not.
     */
    public final boolean alt;

    /**
     * {@code true} if a {@code SHIFT} key (left or right) was pressed when the event occured, {@code false} if not.
     */
    public final boolean shift;

    /**
     * {@code true} if a {@code CTRL} key (left or right) was pressed when the event occured, {@code false} if not.
     */
    public final boolean control;

    /**
     * {@code true} if a system key was pressed when the event occured, {@code false} if not.
     */
    public final boolean system;

    /**
     * Constructs a new key event.
     *
     * @param type    the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param keyCode the code of the key that was pressed (must be a valid ordinal
     *                in the {@link Keyboard.Key} enumeration)
     * @param alt     {@code true} to indicate that an {@code ALT} key was pressed
     * @param shift   {@code true} to indicate that a {@code SHIFT} key was pressed
     * @param control {@code true} to indicate that a {@code CTRL} key was pressed
     * @param system  {@code true} to indicate that a system key was pressed
     */
    public KeyEvent(int type, int keyCode, boolean alt, boolean shift, boolean control, boolean system) {
        super(type);
        this.key = Keyboard.Key.values()[keyCode + 1];
        this.alt = alt;
        this.shift = shift;
        this.control = control;
        this.system = system;
    }
}
