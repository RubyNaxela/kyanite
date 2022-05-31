package com.rubynaxela.kyanite.graphics;

import org.jetbrains.annotations.Nullable;

import java.io.Serial;

/**
 * Thrown if a texture failed to be created.
 */
public class TextureCreationException extends Exception {

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
