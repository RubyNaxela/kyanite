/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.internal;

import com.rubynaxela.kyanite.core.Intercom;


/**
 * A bridge between {@link com.rubynaxela.kyanite.core.NativeRef} and a native C++ pointer.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public abstract class NativeRef {

    @Intercom
    protected long ptr = 0;
}
