/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.audio;

import com.rubynaxela.kyanite.core.Intercom;

/**
 * A bridge between {@link com.rubynaxela.kyanite.audio.Listener} and SFML's {@code sf::Listener}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and 
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class Listener {

    protected Listener() {
    }

    protected static native void nativeSetGlobalVolume(float volume);

    protected static native void nativeSetPosition(float x, float y, float z);

    protected static native void nativeSetDirection(float x, float y, float z);
}
