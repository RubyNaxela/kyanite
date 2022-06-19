/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.graphics.Typeface} and SFML's {@code sf::Font}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
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
