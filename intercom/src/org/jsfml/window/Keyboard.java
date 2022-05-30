package org.jsfml.window;

@Deprecated
public abstract class Keyboard {

    protected Keyboard() {
    }

    protected static native boolean nativeIsKeyPressed(int key);
}
