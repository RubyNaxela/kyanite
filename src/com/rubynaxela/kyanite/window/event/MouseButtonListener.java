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

import java.util.EventListener;

/**
 * The listener interface for receiving the mouse button event. To add a {@code MouseButtonListener}, call
 * the {@link Window#addMouseButtonListener} with the parameter of an object
 * implementing this interface. When a mouse button occurs, that object's {@code mouseButtonPressed} or
 * {@code mouseButtonReleased} method is invoked, depending on whether the event button was pressed or released.
 *
 * @see MouseButtonEvent
 */
public interface MouseButtonListener extends EventListener {

    /**
     * Invoked when a mouse button was pressed while the window had focus.
     *
     * @param e the event to be processed
     */
    default void mouseButtonPressed(MouseButtonEvent e) {
    }

    /**
     * Invoked when a mouse button was released while the window had focus.
     *
     * @param e the event to be processed
     */
    default void mouseButtonReleased(MouseButtonEvent e) {
    }
}
