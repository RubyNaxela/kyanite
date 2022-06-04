/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.window;

import com.rubynaxela.kyanite.core.Intercom;

/**
 * A bridge between {@link com.rubynaxela.kyanite.input.Joystick} and SFML's {@code sf::Joystick}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
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
