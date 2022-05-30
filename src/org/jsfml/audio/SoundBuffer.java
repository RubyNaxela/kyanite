package org.jsfml.audio;

import com.rubynaxela.kyanite.core.audio.ConstSoundBuffer;
import org.jsfml.internal.Intercom;
import org.jsfml.internal.SFMLNativeObject;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class SoundBuffer extends SFMLNativeObject implements ConstSoundBuffer {

    public SoundBuffer() {
    }

    protected SoundBuffer(long wrap) {
        super(wrap);
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native long nativeCreate();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected void nativeSetExPtr() {
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeDelete();

    protected native long nativeCopy();

    protected native boolean nativeLoadFromMemory(byte[] memory);

    protected native boolean nativeLoadFromSamples(Buffer samples, int n, int channelCount, int sampleRate);

    protected native boolean nativeSaveToFile(String fileName);

    protected native void nativeGetData(Buffer buffer);

    protected native void nativeGetSamples(int n, Buffer buffer);
}
