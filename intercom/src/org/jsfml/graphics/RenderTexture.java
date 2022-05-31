package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.graphics.ConstView;
import com.rubynaxela.kyanite.graphics.RenderTarget;
import com.rubynaxela.kyanite.graphics.View;

@Deprecated
@Intercom
public abstract class RenderTexture extends SFMLNativeObject implements RenderTarget {

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

    protected native boolean nativeCreateTexture(int width, int height, boolean depthBuffer);

    protected native void display();

    protected native long nativeGetTexture();

    protected native void nativeClear(int color);

    @Override
    public native void pushGLStates();

    @Override
    public native void popGLStates();

    @Override
    public native void resetGLStates();

    protected native void nativeSetView(View view);

    protected native long nativeGetDefaultView();

    protected native long nativeMapPixelToCoords(long point, ConstView view);

    protected native long nativeMapCoordsToPixel(long point, ConstView view);
}
