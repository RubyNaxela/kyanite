package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class Image extends SFMLNativeObject {

    public Image() {
    }

    @Deprecated
    protected Image(long wrap) {
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

    protected native boolean nativeLoadFromMemory(byte[] memory);

    protected native boolean nativeSaveToFile(String fileName);

    protected native long nativeGetSize();

    protected native void nativeSync(int width, int height, Buffer buffer);

    protected native void nativeCommit(int width, int height, Buffer buffer);
}
