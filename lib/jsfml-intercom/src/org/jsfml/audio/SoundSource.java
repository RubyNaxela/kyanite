/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.audio;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.audio.SoundSource} and SFML's {@code sf::SoundSource}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class SoundSource extends SFMLNativeObject {

    public SoundSource() {
    }

    @Deprecated
    protected SoundSource(long wrap) {
        super(wrap);
    }

    protected native void nativeGetData(Buffer buffer);

    protected native void nativeSetPitch(float pitch);

    protected native void nativeSetVolume(float volume);

    protected native void nativeSetPosition(float x, float y, float z);

    protected native void nativeSetRelativeToListener(boolean relative);

    protected native void nativeSetMinDistance(float distance);

    protected native void nativeSetAttenuation(float att);

    protected abstract int nativeGetStatus();
}
