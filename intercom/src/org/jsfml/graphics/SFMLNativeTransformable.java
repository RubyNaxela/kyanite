package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.graphics.Transformable;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class SFMLNativeTransformable extends SFMLNativeObject {

    protected native void nativeSetPosition(float x, float y);

    protected native void nativeSetRotation(float angle);

    protected native void nativeSetScale(float x, float y);

    protected native void nativeSetOrigin(float x, float y);

    protected native void nativeGetTransform(Buffer buf);
}
