package org.jsfml.internal;

import com.rubynaxela.kyanite.core.NativeRef;

import java.nio.ByteBuffer;

@Deprecated
public abstract class SFMLInputStream {

    protected abstract long read(ByteBuffer buffer, long n);

    protected abstract long seek(long position);

    protected abstract long tell();

    protected abstract long getSize();

    protected abstract void close();

    @Deprecated
    public static class NativeStreamRef extends NativeRef<com.rubynaxela.kyanite.core.SFMLInputStream> {

        protected native long nativeInitialize(com.rubynaxela.kyanite.core.SFMLInputStream ref);

        protected native void nativeRelease(com.rubynaxela.kyanite.core.SFMLInputStream ref, long ptr);
    }
}
