package org.jsfml.audio;

import org.jsfml.internal.Intercom;
import org.jsfml.internal.SFMLNativeObject;

@Deprecated
@Intercom
public abstract class SoundRecorder extends SFMLNativeObject {

    protected static native boolean isAvailable();

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

    protected native void start(int sampleRate);

    protected native void stop();

    protected native int getSampleRate();

    protected abstract boolean onStart();

    protected abstract boolean onProcessSamples(short[] samples);

    protected abstract void onStop();
}
