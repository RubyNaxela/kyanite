package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.Drawable;
import com.rubynaxela.kyanite.graphics.SFMLNativeTransformable;
import com.rubynaxela.kyanite.graphics.Texture;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class Shape extends SFMLNativeTransformable {

    protected native void nativeSetTexture(Texture texture, boolean resetRect);

    protected native void nativeSetTextureRect(Buffer buffer);

    protected native void nativeSetFillColor(int color);

    protected native void nativeSetOutlineColor(int color);

    protected native void nativeSetOutlineThickness(float thickness);

    protected native int nativeGetPointCount();

    protected native void nativeGetPoints(int n, Buffer buffer);

    protected native void nativeGetLocalBounds(Buffer buf);

    protected native void nativeGetGlobalBounds(Buffer buf);
}
