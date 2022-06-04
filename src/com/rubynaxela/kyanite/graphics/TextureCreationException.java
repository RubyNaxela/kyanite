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

package com.rubynaxela.kyanite.graphics;

import org.jetbrains.annotations.Nullable;

import java.io.Serial;

/**
 * Thrown if a texture failed to be created.
 */
public class TextureCreationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -3423733954575177518L;

    /**
     * Constructs a new texture creation exception with the specified message.
     *
     * @param message the detail message
     */
    public TextureCreationException(@Nullable String message) {
        super(message);
    }
}
