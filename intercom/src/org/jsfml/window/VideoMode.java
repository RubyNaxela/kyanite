package org.jsfml.window;

import java.nio.Buffer;

@Deprecated
public abstract class VideoMode {

    protected static native void nativeGetDesktopMode(Buffer buffer);

    protected static native int nativeGetFullscreenModeCount();

    protected static native void nativeGetFullscreenModes(Buffer buffer);
}
