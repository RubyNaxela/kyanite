package org.jsfml.internal;

import com.rubynaxela.kyanite.core.ExPtr;
import com.rubynaxela.kyanite.core.Intercom;

import java.nio.ByteBuffer;
import java.nio.LongBuffer;

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
    }

    @Deprecated
    protected abstract long nativeCreate();

    @Deprecated
    protected abstract void nativeSetExPtr();

    @Deprecated
    protected abstract void nativeDelete();
}
