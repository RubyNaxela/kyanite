/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.audio;

import com.rubynaxela.kyanite.audio.SoundStream;
import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLInputStream;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.audio.Music} and SFML's {@code sf::Music}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class Music extends SoundStream {

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native long nativeCreate();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeDelete();

    protected native boolean nativeOpenFromStream(SFMLInputStream.NativeStreamRef stream);

    protected native void nativeGetData(Buffer buffer);
}
