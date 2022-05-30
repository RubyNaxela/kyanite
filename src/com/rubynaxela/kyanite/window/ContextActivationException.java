package com.rubynaxela.kyanite.window;

import java.io.Serial;

/**
 * Thrown when activating or deactivating an OpenGL fails using {@link Context#setActive} or {@link BasicWindow#setActive}
 */
public final class ContextActivationException extends Exception {

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
