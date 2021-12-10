package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.MouseEvent;

import java.util.EventListener;

/**
 * The listener interface for receiving the mouse movement event. To add a {@code MouseListener}, call
 * the {@link com.rubynaxela.kyanite.window.Window#addMouseListener} with the parameter of an object
 * implementing this interface. When a mouse movement event occurs inside the window, that object's
 * {@code mouseMoved} method is invoked. When the cursor enters the window, the {@code mouseEntered} method
 * is invoked. Likewise, when the cursor leaves the window, the {@code mouseLeft} method is invoked.
 *
 * @see MouseEvent
 */
public interface MouseListener extends EventListener {

    /**
     * Invoked when the mouse cursor was moved within the window's boundaries.
     *
     * @param e the event to be processed
     */
    default void mouseMoved(MouseEvent e) {
    }

    /**
     * Invoked when the mouse cursor entered the window's boundaries.
     *
     * @param e the event to be processed
     */
    default void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse cursor left the window's boundaries.
     *
     * @param e the event to be processed
     */
    default void mouseLeft(MouseEvent e) {
    }
}
