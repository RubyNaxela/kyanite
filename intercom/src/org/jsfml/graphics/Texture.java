/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLInputStream;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.window.BasicWindow;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.graphics.Texture} and SFML's {@code sf::Texture}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class Texture extends SFMLNativeObject {

    public Texture() {
    }

    protected Texture(long wrap) {
        super(wrap);
    }

    protected static native int nativeGetMaximumSize();

    protected static native void nativeBind(Texture texture, int coordinateType);

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

    protected native boolean nativeCreate(int width, int height);

    protected native boolean nativeLoadFromStream(SFMLInputStream.NativeStreamRef stm, Buffer area);

    protected native boolean nativeLoadFromFile(String path, Buffer area);

    protected native boolean nativeLoadFromImage(com.rubynaxela.kyanite.graphics.Image image, Buffer area);

    protected native long nativeGetSize();

    protected native long nativeCopyToImage();

    protected native void nativeUpdate(com.rubynaxela.kyanite.graphics.Image image, int x, int y);

    protected native void nativeUpdate(BasicWindow window, int x, int y);

    protected native void nativeSetSmooth(boolean smooth);

    protected native boolean nativeIsSmooth();

    protected native void nativeSetRepeated(boolean repeated);

    protected native boolean nativeIsRepeated();
}
