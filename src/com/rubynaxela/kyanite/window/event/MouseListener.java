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
 * The listener interface for receiving the mouse movement event. To add a {@code MouseListener}, call
 * the {@link Window#addMouseListener} with the parameter of an object
 * implementing this interface. When a mouse movement event occurs inside the window, that object's
 * {@code mouseMoved} method is invoked. When the cursor enters the window, the {@code mouseEntered} method
 * is invoked. Likewise, when the cursor leaves the window, the {@code mouseLeft} method is invoked.
 *
 * @see MouseEvent
 */
public interface MouseListener extends EventListener {

    /**
     * Invoked when the mouse cursor was moved within the window's boundaries.
     *
     * @param e the event to be processed
     */
    default void mouseMoved(MouseEvent e) {
    }

    /**
     * Invoked when the mouse cursor entered the window's boundaries.
     *
     * @param e the event to be processed
     */
    default void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse cursor left the window's boundaries.
     *
     * @param e the event to be processed
     */
    default void mouseLeft(MouseEvent e) {
    }
}
