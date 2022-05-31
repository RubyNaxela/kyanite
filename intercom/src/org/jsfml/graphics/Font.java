package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.graphics.ConstFont;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class Font extends SFMLNativeObject {

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

    protected native long nativeLoadFromMemory(byte[] memory);

    protected native void nativeReleaseMemory(byte[] memory, long ptr);

    protected native long nativeGetTexture(int characterSize);

    protected native int nativeGetLineSpacing(int characterSize);

    protected native void nativeGetGlyph(int unicode, int characterSize, boolean bold, Buffer buf);

    protected native int nativeGetKerning(int first, int second, int characterSize);
}
