package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.Drawable;
import com.rubynaxela.kyanite.graphics.SFMLNativeTransformable;
import com.rubynaxela.kyanite.graphics.Texture;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class Sprite extends SFMLNativeTransformable {

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native void nativeSetTexture(Texture texture, boolean resetRect);

    protected native void nativeSetTextureRect(Buffer rect);

    protected native void nativeSetColor(int color);

    protected native void nativeGetLocalBounds(Buffer buf);

    protected native void nativeGetGlobalBounds(Buffer buf);
}
