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

package com.rubynaxela.kyanite.window;

import java.io.Serial;

/**
 * Thrown when activating or deactivating an OpenGL fails using {@link Context#setActive} or {@link BasicWindow#setActive}
 */
public class ContextActivationException extends Exception {

    @Serial
    private static final long serialVersionUID = -9207950728636532244L;

    /**
     * Constructs a context activation exception with the specified message.
     *
     * @param message the detail message
     */
    public ContextActivationException(String message) {
        super(message);
    }
}
