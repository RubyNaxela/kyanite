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
