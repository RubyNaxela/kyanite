/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.audio;

import com.rubynaxela.kyanite.audio.SoundBuffer;
import com.rubynaxela.kyanite.audio.SoundSource;
import com.rubynaxela.kyanite.core.Intercom;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.audio.Sound} and SFML's {@code sf::Sound}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class Sound extends SoundSource {

    public Sound() {
    }

    protected Sound(long wrap) {
        super(wrap);
    }

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native long nativeCopy();

    protected native void nativeGetData(Buffer buffer);

    protected native void play();

    protected native void pause();

    protected native void stop();

    protected native void nativeSetBuffer(SoundBuffer soundBuffer);

    protected native void nativeSetLoop(boolean loop);

    protected native void nativeSetPlayingOffset(long offset);

    @Override
    protected native int nativeGetStatus();

    @Override
    public Status getStatus() {
        return super.getStatus();
    }
}
