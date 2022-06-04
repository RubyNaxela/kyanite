/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.internal;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.NativeRef;

import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.core.SFMLInputStream} and the native pipe
 * for enabling stream from a Java {@link InputStream} into an {@code sf::InputStream}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
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
