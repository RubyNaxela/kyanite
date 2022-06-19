/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.audio;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;

/**
 * A bridge between {@link com.rubynaxela.kyanite.audio.SoundRecorder} and SFML's {@code sf::SoundRecorder}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class SoundRecorder extends SFMLNativeObject {

    protected static native boolean isAvailable();

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native void start(int sampleRate);

    protected native void stop();

    protected native int getSampleRate();

    protected abstract boolean onStart();

    protected abstract boolean onProcessSamples(short[] samples);

    protected abstract void onStop();
}
