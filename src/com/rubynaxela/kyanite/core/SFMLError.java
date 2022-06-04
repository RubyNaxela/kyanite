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

package com.rubynaxela.kyanite.core;

import org.jetbrains.annotations.Nullable;

import java.io.Serial;

/**
 * Error class for severe SFML faults. An error of this type is raised either if Kyanite tried to load
 * its native libraries on an unsupported platform, or if a platform-specific requirement is violated.
 */
public class SFMLError extends Error {

    @Serial
    private static final long serialVersionUID = -8281004117329430845L;

    /**
     * Constructs an SFML error with the specified message.
     *
     * @param message the exception's message text.
     */
    public SFMLError(@Nullable String message) {
        super(message);
    }

    /**
     * Constructs an SFML error with the specified message and cause.
     *
     * @param message the exception's message text.
     * @param cause   the exception's cause, or {@code null} if no cause is known or available.
     */
    public SFMLError(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
