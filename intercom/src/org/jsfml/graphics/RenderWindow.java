package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.ConstView;
import com.rubynaxela.kyanite.graphics.RenderTarget;
import com.rubynaxela.kyanite.graphics.View;
import com.rubynaxela.kyanite.window.BasicWindow;

@Deprecated
@Intercom
public abstract class RenderWindow extends BasicWindow {

    public RenderWindow() {
    }

    protected RenderWindow(long ptr) {
        super(ptr);
    }

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native long nativeCapture();

    protected native void nativeClear(int color);

    protected native void pushGLStates();

    protected native void popGLStates();

    protected native void resetGLStates();

    protected native void nativeSetView(View view);

    protected native long nativeGetDefaultView();

    protected native long nativeMapPixelToCoords(long point, ConstView view);

    protected native long nativeMapCoordsToPixel(long point, ConstView view);
}
