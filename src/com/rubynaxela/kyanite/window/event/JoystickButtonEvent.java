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
 * Represents joystick or gamepad button events. Objects of this class are created for events of
 * type {@link Event.Type#JOYSTICK_BUTTON_PRESSED} or {@link Event.Type#JOYSTICK_BUTTON_RELEASED}.
 */
public final class JoystickButtonEvent extends JoystickEvent {

    /**
     * The index of the button that was pressed or released. The value is guaranteed to range
     * between 0 (inclusive) and {@link Joystick#BUTTON_COUNT} (exclusive).
     */
    public final int button;

    /**
     * Constructs a new joystick button event.
     *
     * @param type       the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param joystickId the joystick ID
     * @param button     the index of the button that was pressed
     */
    public JoystickButtonEvent(int type, int joystickId, int button) {
        super(type, joystickId);
        this.button = button;
    }
}
