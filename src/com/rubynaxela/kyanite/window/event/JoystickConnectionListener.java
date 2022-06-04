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

import com.rubynaxela.kyanite.window.Window;

/**
 * The listener interface for receiving the joystick connection event. To add a {@code JoystickConnectionListener},
 * call the {@link Window#addJoystickConnectionListener} with the parameter of an object
 * implementing this interface. When a joystick button event occurs, that object's {@code joystickConnected} or
 * {@code joystickDisconnected} method is invoked, depending on whether the connected was pressed or disconnected.
 *
 * @see JoystickEvent
 */
public interface JoystickConnectionListener {

    /**
     * Invoked when a joystick or gamepad was connected..
     *
     * @param e the event to be processed
     */
    default void joystickConnected(JoystickEvent e) {
    }

    /**
     * Invoked when a joystick or gamepad was disconnected.
     *
     * @param e the event to be processed
     */
    default void joystickDisconnected(JoystickEvent e) {
    }
}
