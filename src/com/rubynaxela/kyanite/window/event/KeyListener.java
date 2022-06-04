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
 * The listener interface for receiving the key event. To add a {@code KeyListener}, call the
 * {@link Window#addKeyListener} with the parameter of an object
 * implementing this interface. When a key event occurs, that object's {@code keyPressed} or
 * {@code keyReleased} method is invoked, depending on whether the event key was pressed or released.
 *
 * @see KeyEvent
 */
public interface KeyListener extends EventListener {

    /**
     * Invoked when a keyboard key was pressed while the window had focus.
     *
     * @param e the event to be processed
     */
    default void keyPressed(KeyEvent e) {
    }

    /**
     * Invoked when a keyboard key was released while the window had focus.
     *
     * @param e the event to be processed
     */
    default void keyReleased(KeyEvent e) {
    }
}
