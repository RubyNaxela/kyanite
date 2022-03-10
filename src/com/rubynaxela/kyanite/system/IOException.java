package com.rubynaxela.kyanite.system;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Signals that an I/O operation failed or was interrupted.
 */
public class IOException extends RuntimeException {

    /**
     * Constructs a new {@code IOException} with the specified detail message. The cause is
     * not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public IOException(@NotNull String message) {
        super(message);
    }

    /**
     * Constructs a new {@code IOException} with the specified detail message and cause. Note that the detail message
     * associated with {@code cause} is not automatically incorporated in this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null}
     *                value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public IOException(@NotNull String message, @Nullable Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code IOException} with the specified cause. The detail message is specified
     * as {@code cause.getMessage()} and can be later retrieved by the {@link #getMessage()} method.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null}
     *              value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public IOException(@NotNull Exception cause) {
        this(cause.getMessage(), cause);
    }
}
