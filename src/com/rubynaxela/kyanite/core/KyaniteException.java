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

/**
 * Exception class for severe Kyanite faults.
 */
public class KyaniteException extends RuntimeException {

    /**
     * Constructs a new Kyanite exception with {@code null} as its detail message. The cause
     * is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public KyaniteException() {
    }

    /**
     * Constructs a new Kyanite exception with the specified detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message; saved for later retrieval by the {@link #getMessage()} method
     */
    public KyaniteException(@Nullable String message) {
        super(message);
    }

    /**
     * Constructs a new Kyanite exception with the specified detail message and cause. Note that the detail message
     * associated with {@code cause} is <i>not</i> automatically incorporated in this exception's detail message.
     *
     * @param message message the detail message; saved for later retrieval by the {@link #getMessage()} method
     * @param cause   the cause; saved for later retrieval by the {@link #getCause()} method)
     */
    public KyaniteException(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new Kyanite exception with the specified cause and a detail message of {@code (cause==null ?
     * null : cause.toString())} (which typically contains the class and detail message of {@code cause}).
     * This constructor is useful for Kyanite exceptions that are little more than wrappers for other throwables.
     *
     * @param cause the cause; saved for later retrieval by the {@link #getCause()} method)
     */
    public KyaniteException(@Nullable Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new Kyanite exception with the specified detail message, cause,
     * suppression enabled or disabled, and writable stack trace enabled or disabled.
     *
     * @param message            message the detail message; saved for later retrieval by the {@link #getMessage()} method
     * @param cause              the cause; saved for later retrieval by the {@link #getCause()} method)
     * @param enableSuppression  whether or not suppression is enabled or disabled
     * @param writableStackTrace whether or not the stack trace should be writable
     */
    protected KyaniteException(@Nullable String message, @Nullable Throwable cause,
                               boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
