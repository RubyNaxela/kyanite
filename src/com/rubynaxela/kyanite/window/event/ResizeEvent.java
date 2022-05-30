package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.core.system.Vector2i;

/**
 * Represents the window reisze event.
 *
 * @param newSize the window size after it's resized
 */
public final record ResizeEvent(Vector2i newSize) {

}
