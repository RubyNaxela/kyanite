package org.jsfml.window;

@Deprecated
public abstract class Mouse {

    protected Mouse() {
    }

    protected static native boolean nativeIsButtonPressed(int button);

    protected static native long nativeGetPosition();

    protected static native long nativeGetPosition(Window relativeTo);

    protected static native void nativeSetPosition(long position);

    protected static native void nativeSetPosition(long position, Window relativeTo);
}
