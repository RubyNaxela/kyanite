package org.jsfml.audio;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;

import java.nio.Buffer;

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
