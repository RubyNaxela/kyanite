package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.graphics.ConstView;
import com.rubynaxela.kyanite.graphics.RenderTarget;
import com.rubynaxela.kyanite.graphics.View;

@Deprecated
@Intercom
public abstract class RenderTexture extends SFMLNativeObject {

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native boolean nativeCreateTexture(int width, int height, boolean depthBuffer);

    protected native void display();

    protected native long nativeGetTexture();

    protected native void nativeClear(int color);

    protected native void pushGLStates();

    protected native void popGLStates();

    protected native void resetGLStates();

    protected native void nativeSetView(View view);

    protected native long nativeGetDefaultView();

    protected native long nativeMapPixelToCoords(long point, ConstView view);

    protected native long nativeMapCoordsToPixel(long point, ConstView view);
}
