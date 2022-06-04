package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.ConstShader;
import com.rubynaxela.kyanite.graphics.ConstTexture;
import com.rubynaxela.kyanite.graphics.Drawable;
import com.rubynaxela.kyanite.graphics.RenderTarget;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class SFMLNativeDrawer {

    protected static native void nativeDrawVertices(int num, Buffer buffer, int type, RenderTarget target, int blendMode,
                                                    Buffer transform, ConstTexture texture, ConstShader shader);

    protected static native void nativeDrawDrawable(Drawable drawable, RenderTarget target, int blendMode, Buffer transform,
                                                    ConstTexture texture, ConstShader shader);
}
