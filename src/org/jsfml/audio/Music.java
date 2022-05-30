package org.jsfml.audio;

import com.rubynaxela.kyanite.audio.SoundStream;
import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLInputStream;

import java.nio.Buffer;

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
