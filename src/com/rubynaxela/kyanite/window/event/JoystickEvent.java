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

import com.rubynaxela.kyanite.input.Joystick;

/**
 * Represents generic joystick or gamepad events. Objects of this class are created for events of type
 * {@link Event.Type#JOYSTICK_CONNECETED}, {@link Event.Type#JOYSTICK_DISCONNECTED},
 * {@link Event.Type#JOYSTICK_BUTTON_PRESSED}, {@link Event.Type#JOYSTICK_BUTTON_RELEASED}
 * or {@link Event.Type#JOYSTICK_MOVED}.
 */
public class JoystickEvent extends Event {

    /**
     * The index of the joystick that caused this event. The value is guaranteed to range
     * between 0 (inclusive) and {@link Joystick#JOYSTICK_COUNT} (exclusive)
     */
    public final int joystickId;

    /**
     * Constructs a new joystick event.
     *
     * @param type       the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param joystickId the joystick ID
     */
    public JoystickEvent(int type, int joystickId) {
        super(type);
        this.joystickId = joystickId;
    }
}
