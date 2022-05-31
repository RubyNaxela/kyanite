package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.Shape;

@Deprecated
@Intercom
public abstract class ConvexShape extends Shape {

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native void nativeSetPointCount(int count);

    protected native void nativeSetPoint(int i, float x, float y);
}
