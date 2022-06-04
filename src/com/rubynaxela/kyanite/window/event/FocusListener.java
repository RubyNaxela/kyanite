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
 * The listener interface for receiving the window focus event. To add a {@code FocusListener}, call the
 * {@link Window#addFocusListener} with the parameter of an object
 * implementing this interface. When the window gains focus, that object's {@code focusGained} method
 * is invoked, whereas when the window loses focus, that object's {@code focusLost} method is invoked.
 */
public interface FocusListener extends EventListener {

    /**
     * Invoked when the window lost focus.
     */
    default void focusGained() {
    }

    /**
     * Invoked when the window gained focus.
     */
    default void focusLost() {
    }
}
