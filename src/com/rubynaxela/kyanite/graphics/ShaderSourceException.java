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
