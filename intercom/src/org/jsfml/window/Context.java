package org.jsfml.window;

import com.rubynaxela.kyanite.core.SFMLNativeObject;

@Deprecated
public abstract class Context extends SFMLNativeObject {

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

    protected native boolean nativeSetActive(boolean active);
}
