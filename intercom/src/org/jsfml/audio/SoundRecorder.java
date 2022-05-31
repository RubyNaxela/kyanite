package org.jsfml.audio;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;

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
