/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.graphics.Transformable;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.graphics.SFMLNativeTransformable}
 * and native generic implementation of SFML's {@code sf::Transformable}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class SFMLNativeTransformable extends SFMLNativeObject {

    protected native void nativeSetPosition(float x, float y);

    protected native void nativeSetRotation(float angle);

    protected native void nativeSetScale(float x, float y);

    protected native void nativeSetOrigin(float x, float y);

    protected native void nativeGetTransform(Buffer buf);
}
