package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.window.Window;

import java.util.EventListener;

/**
 * The listener interface for receiving the mouse button event. To add a {@code MouseButtonListener}, call
 * the {@link Window#addMouseButtonListener} with the parameter of an object
 * implementing this interface. When a mouse button occurs, that object's {@code mouseButtonPressed} or
 * {@code mouseButtonReleased} method is invoked, depending on whether the event button was pressed or released.
 *
 * @see MouseButtonEvent
 */
public interface MouseButtonListener extends EventListener {

    /**
     * Invoked when a mouse button was pressed while the window had focus.
     *
     * @param e the event to be processed
     */
    default void mouseButtonPressed(MouseButtonEvent e) {
    }

    /**
     * Invoked when a mouse button was released while the window had focus.
     *
     * @param e the event to be processed
     */
    default void mouseButtonReleased(MouseButtonEvent e) {
    }
}
