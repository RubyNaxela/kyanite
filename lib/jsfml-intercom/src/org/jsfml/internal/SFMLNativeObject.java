/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.internal;

import com.rubynaxela.kyanite.core.ExPtr;
import com.rubynaxela.kyanite.core.Intercom;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.core.SFMLNativeObject} and
 * the native methods for managing and communicating with SFML C++ objects.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class SFMLNativeObject {

    @Intercom
    public final LongBuffer exPtr = ByteBuffer.allocateDirect(ExPtr.NUM << 3).asLongBuffer();

    @Intercom
    protected long ptr = 0;

    protected SFMLNativeObject() {
    }

    protected SFMLNativeObject(long wrap) {
        ptr = wrap;
    }

    @Deprecated
    protected abstract long nativeCreate();

    @Deprecated
    protected abstract void nativeSetExPtr();

    @Deprecated
    protected abstract void nativeDelete();
}
