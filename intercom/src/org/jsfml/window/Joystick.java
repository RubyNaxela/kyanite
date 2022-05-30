package org.jsfml.window;

@Deprecated
public abstract class Joystick {

    protected Joystick() {
    }

    protected static native boolean nativeIsConnected(int joystick);

    protected static native int nativeGetButtonCount(int joystick);

    protected static native boolean nativeHasAxis(int joystick, int axis);

    protected static native boolean nativeIsButtonPressed(int joystick, int button);

    protected static native float nativeGetAxisPosition(int joystick, int axis);

    protected static native void update();
}
