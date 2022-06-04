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

package com.rubynaxela.kyanite.data;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

/**
 * This exception is thrown when a JSON parsing or serializaion operation has failed.
 */
public class JSONProcessingException extends JSONException {

    /**
     * Constructs a new {@code JSONProcessingException} with the specified detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message (saved for later retrieval by the {@link #getMessage()} method)
     */
    public JSONProcessingException(@NotNull String message) {
        super(message);
    }
}
