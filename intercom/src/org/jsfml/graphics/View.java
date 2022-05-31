package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.graphics.ConstView;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class View extends SFMLNativeObject implements ConstView {

    public View() {
    }

    protected View(long wrap) {
        super(wrap);
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected final native long nativeCreate();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected void nativeSetExPtr() {
    }

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
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
