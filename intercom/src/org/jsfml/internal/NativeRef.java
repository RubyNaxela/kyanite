package org.jsfml.internal;

import com.rubynaxela.kyanite.core.Intercom;

/**
 * Helper class for managing Java object references and related native pointers.
 *
 * @param <T> the reference type
 */
@Deprecated
@Intercom
public abstract class NativeRef<T> {

    @Intercom
    protected long ptr = 0;
}
