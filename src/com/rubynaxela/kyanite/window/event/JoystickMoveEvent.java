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
 * Represents joystick or gamepad axis movement events. Objects of this
 * class are created for events of type {@link Event.Type#JOYSTICK_MOVED}.
 */
public final class JoystickMoveEvent extends JoystickEvent {

    /**
     * The joystick or gamepad axis that was moved.
     */
    public final Joystick.Axis joyAxis;

    /**
     * The position that the axis was moved to, ranging between -100 and 100.
     */
    public final float position;

    /**
     * Constructs a new joystick axis event.
     *
     * @param type       the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param joystickId the joystick ID
     * @param joyAxis    the joystick axis that was moved (must be a valid ordinal
     *                   in the {@link Joystick.Axis} enumeration)
     * @param position   the position that the axis was moved to
     */
    public JoystickMoveEvent(int type, int joystickId, int joyAxis, float position) {
        super(type, joystickId);
        this.joyAxis = Joystick.Axis.values()[joyAxis];
        this.position = position;
    }
}
