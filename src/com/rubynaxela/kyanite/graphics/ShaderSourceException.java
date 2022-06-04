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
 * Thrown if a vertex or fragment shader source could not be compiled or linked.
 */
public class ShaderSourceException extends Exception {

    @Serial
    private static final long serialVersionUID = -6514888818053624276L;

    /**
     * Constructs a shader compilation exception with the specified message.
     *
     * @param msg the error message
     */
    public ShaderSourceException(@Nullable String msg) {
        super(msg);
    }
}
