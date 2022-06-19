/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.graphics.ConstView;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.graphics.View} and SFML's {@code sf::View}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class View extends SFMLNativeObject {

    public View() {
    }

    protected View(long wrap) {
        super(wrap);
    }

    @Override
    @Deprecated
    protected final native long nativeCreate();

    @Override
    @Deprecated
    protected void nativeSetExPtr() {
    }

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native void nativeSetCenter(float x, float y);

    protected native void nativeSetSize(float width, float height);

    protected native void nativeSetRotation(float angle);

    protected native void nativeSetViewport(Buffer buffer);

    protected native void nativeReset(Buffer buffer);

    protected native void nativeGetViewport(Buffer buffer);

    protected native long nativeGetSize();

    protected native long nativeGetCenter();

    protected native float nativeGetRotation();

    protected native void nativeGetTransform(Buffer buf);
}
