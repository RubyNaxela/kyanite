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
     * Constructs a JSFML error with the specified message.
     *
     * @param message the exception's message text.
     */
    public SFMLError(@Nullable String message) {
        super(message);
    }

    /**
     * Constructs a JSFML error with the specified message and cause.
     *
     * @param message the exception's message text.
     * @param cause   the exception's cause, or {@code null} if no cause is known or available.
     */
    public SFMLError(@Nullable String message, @Nullable Throwable cause) {
        super(message, cause);
    }
}
