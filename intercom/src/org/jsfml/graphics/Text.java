package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.Typeface;
import com.rubynaxela.kyanite.graphics.SFMLNativeTransformable;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class Text extends SFMLNativeTransformable {

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native void nativeSetString(String string);

    protected native void nativeSetFont(Typeface typeFace);

    protected native void nativeSetCharacterSize(int characterSize);

    protected native void nativeSetStyle(int style);

    protected native void nativeSetColor(int color);

    protected native long nativeFindCharacterPos(int i);

    protected native void nativeGetLocalBounds(Buffer buf);

    protected native void nativeGetGlobalBounds(Buffer buf);
}
