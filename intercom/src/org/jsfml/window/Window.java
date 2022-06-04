/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.window;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.SFMLNativeObject;
import com.rubynaxela.kyanite.window.WindowStyle;
import com.rubynaxela.kyanite.graphics.Image;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.window.BasicWindow} and SFML's {@code sf::Window}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class Window extends SFMLNativeObject {

    public Window() {
    }

    protected Window(long ptr) {
        super(ptr);
    }

    protected static native boolean isLegalWindowThread();

    @Override
    @Deprecated
    protected native long nativeCreate();

    @Override
    @Deprecated
    protected native void nativeSetExPtr();

    @Override
    @Deprecated
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
