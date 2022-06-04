/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.Drawable;
import com.rubynaxela.kyanite.graphics.SFMLNativeTransformable;
import com.rubynaxela.kyanite.graphics.Texture;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.graphics.Shape} and SFML's {@code sf::Shape}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
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
