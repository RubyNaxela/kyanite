package com.rubynaxela.kyanite.core;

import org.jetbrains.annotations.Nullable;

/**
 * Helper class for managing Java object references and related native pointers.
 *
 * @param <T> the reference type
 */
@SuppressWarnings("deprecation")
@Intercom
public abstract class NativeRef<T> extends org.jsfml.internal.NativeRef<T> {

    private T ref = null;

    /**
     * Constructs a native reference.
     */
    public NativeRef() {
    }

    /**
     * Initializes a native pointer related to the specified Java object.
     *
     * @param ref the related Java object, or {@code null} if there is none
     * @return a native pointer, or 0 if none was initialized
     */
    protected abstract long nativeInitialize(@Nullable T ref);

    /**
     * Releases the Java reference, if any, and the managed native pointer, if
     * any. This also is responsible for natively allocated memory being freed.
     *
     * @param ref the Java object reference, or {@code null} if there is none
     * @param ptr the native pointer, if any
     */
    protected abstract void nativeRelease(@Nullable T ref, long ptr);

    /**
     * Initializes a native reference with the specified Java object.
     *
     * @param ref the Java object, or {@code null} if none is required
     */
    public void initialize(@Nullable T ref) {
        release();
        this.ref = ref;
        this.ptr = nativeInitialize(ref);
    }

    /**
     * Releases the managed Java reference, if any, and frees the managed
     * native pointer, if any, including freeing any natively allocated memory.
     */
    public void release() {
        if (ptr != 0) {
            nativeRelease(ref, ptr);
            ptr = 0;
        }
        ref = null;
    }

    /**
     * Tests whether this reference has a null native pointer.
     *
     * @return {@code false} if the pointer is non-zero, {@code true} if it is null
     */
    public boolean isNull() {
        return ptr == 0;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        release();
        super.finalize();
    }
}
