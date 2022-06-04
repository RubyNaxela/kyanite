/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.window;

import com.rubynaxela.kyanite.core.Intercom;

/**
 * A bridge between {@link com.rubynaxela.kyanite.input.Mouse} and SFML's {@code sf::Mouse}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class Mouse {

    protected Mouse() {
    }

    protected static native boolean nativeIsButtonPressed(int button);

    protected static native long nativeGetPosition();

    protected static native long nativeGetPosition(Window relativeTo);

    protected static native void nativeSetPosition(long position);

    protected static native void nativeSetPosition(long position, Window relativeTo);
}
