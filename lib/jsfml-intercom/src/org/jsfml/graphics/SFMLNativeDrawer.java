/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.ConstShader;
import com.rubynaxela.kyanite.graphics.ConstTexture;
import com.rubynaxela.kyanite.graphics.Drawable;
import com.rubynaxela.kyanite.graphics.RenderTarget;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.graphics.SFMLNativeDrawer} and native generic
 * implementations of the {@code draw} methods for SFML's {@code sf::Drawable} and {@code sf::Vertex} classes.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class SFMLNativeDrawer {

    protected static native void nativeDrawVertices(int num, Buffer buffer, int type, RenderTarget target, int blendMode,
                                                    Buffer transform, ConstTexture texture, ConstShader shader);

    protected static native void nativeDrawDrawable(Drawable drawable, RenderTarget target, int blendMode, Buffer transform,
                                                    ConstTexture texture, ConstShader shader);
}
