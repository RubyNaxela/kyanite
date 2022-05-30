package org.jsfml.internal;

@Deprecated
public abstract class SFMLErrorCapture {

    protected SFMLErrorCapture() {
    }

    protected static native void nativeStart();

    protected static native String nativeFinish();
}
