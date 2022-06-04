/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.window;

import com.rubynaxela.kyanite.core.Intercom;

import java.nio.Buffer;

/**
 * A bridge between {@link com.rubynaxela.kyanite.window.VideoMode} and SFML's {@code sf::VideoMode}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class VideoMode {

    protected static native void nativeGetDesktopMode(Buffer buffer);

    protected static native int nativeGetFullscreenModeCount();

    protected static native void nativeGetFullscreenModes(Buffer buffer);
}
