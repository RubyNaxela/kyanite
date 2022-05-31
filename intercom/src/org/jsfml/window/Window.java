package org.jsfml.window;

import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.window.WindowStyle;
import com.rubynaxela.kyanite.graphics.Image;

import java.nio.Buffer;

@Deprecated
public abstract class Window extends SFMLNativeObject implements WindowStyle {

    public Window() {
    }

    protected Window(long ptr) {
        super(ptr);
    }

    protected static native boolean isLegalWindowThread();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native long nativeCreate();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    protected native void nativeDelete();

    protected native void nativeCreateWindow(Buffer buffer, String title);

    protected native void close();

    protected native boolean isOpen();

    protected native long nativeGetPosition();

    protected native void nativeSetPosition(int x, int y);

    protected native long nativeGetSize();

    protected native void nativeSetSize(int x, int y);

    protected native void nativeGetSettings(Buffer buffer);

    protected native void nativePollEvent(Buffer buffer);

    protected native void nativeWaitEvent(Buffer buffer);

    protected native void setVerticalSyncEnabled(boolean enable);

    protected native void setMouseCursorVisible(boolean show);

    protected native void nativeSetTitle(String title);

    protected native void setVisible(boolean show);

    protected native void setKeyRepeatEnabled(boolean enable);

    protected native void nativeSetIcon(Image image);

    protected native boolean nativeSetActive(boolean active);

    protected native void display();

    protected native void setFramerateLimit(int flimit);

    protected native void setJoystickTreshold(float treshold);
}
