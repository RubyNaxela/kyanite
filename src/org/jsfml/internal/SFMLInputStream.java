package org.jsfml.internal;

import java.nio.ByteBuffer;

@Deprecated
public abstract class SFMLInputStream {

    protected abstract long read(ByteBuffer buffer, long n);

    protected abstract long seek(long position);

    protected abstract long tell();

    protected abstract long getSize();

    protected abstract void close();
}
