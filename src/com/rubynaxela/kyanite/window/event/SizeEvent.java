package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.math.Vector2i;

/**
 * Represents window resize events. Objects of this class are created for events of type {@link Event.Type#RESIZED}.
 */
public final class SizeEvent extends Event {

    /**
     * The new size of the window in pixels.
     */
    public final Vector2i size;

    /**
     * Constructs a new window resize event.
     *
     * @param type   the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param width  the new width of the window in pixels
     * @param height the new height of the window in pixels
     */
    public SizeEvent(int type, int width, int height) {
        super(type);
        this.size = new Vector2i(width, height);
    }
}