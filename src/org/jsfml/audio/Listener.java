package org.jsfml.audio;

import com.rubynaxela.kyanite.core.Intercom;

@Deprecated
@Intercom
public abstract class Listener {

    protected Listener() {
    }

    protected static native void nativeSetGlobalVolume(float volume);

    protected static native void nativeSetPosition(float x, float y, float z);

    protected static native void nativeSetDirection(float x, float y, float z);
}
