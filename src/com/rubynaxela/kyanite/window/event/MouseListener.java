package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.MouseEvent;

import java.util.EventListener;

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
