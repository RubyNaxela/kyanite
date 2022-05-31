package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.Drawable;
import com.rubynaxela.kyanite.graphics.Font;
import com.rubynaxela.kyanite.graphics.SFMLNativeTransformable;
import com.rubynaxela.kyanite.graphics.TextStyle;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class Text extends SFMLNativeTransformable implements Drawable, TextStyle {

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

    protected native void nativeSetString(String string);

    protected native void nativeSetFont(Font font);

    protected native void nativeSetCharacterSize(int characterSize);

    protected native void nativeSetStyle(int style);

    protected native void nativeSetColor(int color);

    protected native long nativeFindCharacterPos(int i);

    protected native void nativeGetLocalBounds(Buffer buf);

    protected native void nativeGetGlobalBounds(Buffer buf);
}
