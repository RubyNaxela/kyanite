package org.jsfml.window;

import com.rubynaxela.kyanite.core.SFMLNativeObject;

@Deprecated
public abstract class Context extends SFMLNativeObject {

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

    protected native boolean nativeSetActive(boolean active);
}
