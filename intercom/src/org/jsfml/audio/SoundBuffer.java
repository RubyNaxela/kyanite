package org.jsfml.audio;

import com.rubynaxela.kyanite.audio.ConstSoundBuffer;
import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class SoundBuffer extends SFMLNativeObject {

    public SoundBuffer() {
    }

    protected SoundBuffer(long wrap) {
        super(wrap);
    }

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected void nativeSetExPtr() {
    }

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native long nativeCopy();

    protected native boolean nativeLoadFromMemory(byte[] memory);

    protected native boolean nativeLoadFromSamples(Buffer samples, int n, int channelCount, int sampleRate);

    protected native boolean nativeSaveToFile(String fileName);

    protected native void nativeGetData(Buffer buffer);

    protected native void nativeGetSamples(int n, Buffer buffer);
}
