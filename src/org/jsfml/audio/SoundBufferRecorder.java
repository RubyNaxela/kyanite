package org.jsfml.audio;

import com.rubynaxela.kyanite.core.audio.SoundRecorder;
import org.jsfml.internal.Intercom;

@Deprecated
@Intercom
public abstract class SoundBufferRecorder extends SoundRecorder {

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

    protected native long nativeGetBuffer();
}
