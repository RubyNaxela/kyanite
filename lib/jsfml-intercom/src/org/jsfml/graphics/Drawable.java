/*
 * This file is a part of an altered version of the JSFML library. More information in the LEGAL.txt file.
 */

package org.jsfml.graphics;

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.graphics.RenderStates;
import com.rubynaxela.kyanite.graphics.RenderTarget;
import org.jetbrains.annotations.NotNull;

/**
 * A bridge between {@link com.rubynaxela.kyanite.graphics.Drawable} and SFML's {@code sf::Drawable}.
 *
 * @deprecated This is part of the intercom module which is the link between Kyanite and
 * SFML. Classes from this module are not indented to be used outside the Kyanite framework.
 */
@Deprecated
@Intercom
public interface Drawable {

    @Intercom
    void draw(@NotNull RenderTarget target, @NotNull RenderStates states);
}
