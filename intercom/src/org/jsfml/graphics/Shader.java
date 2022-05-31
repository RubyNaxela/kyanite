package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.graphics.ConstShader;
import com.rubynaxela.kyanite.graphics.Texture;

import java.nio.Buffer;

@Deprecated
@Intercom
public abstract class Shader extends SFMLNativeObject {

    protected static native void bind(ConstShader shader);

    protected static native boolean isAvailable();

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected void nativeSetExPtr() {
    }

    @Override
    @Deprecated
    protected native void nativeDelete();

    protected native boolean nativeLoadFromSource1(String source, int shaderType);

    protected native boolean nativeLoadFromSource2(String vertSource, String fragSource);

    protected native void nativeSetParameterFloat(String name, float x);

    protected native void nativeSetParameterVec2(String name, float x, float y);

    protected native void nativeSetParameterVec3(String name, float x, float y, float z);

    protected native void nativeSetParameterVec4(String name, float x, float y, float z, float w);

    protected native void nativeSetParameterMat4(String name, Buffer xform);

    protected native void nativeSetParameterSampler2d(String name, Texture texture);

    protected native void nativeSetParameterCurrentTexture(String name);
}
