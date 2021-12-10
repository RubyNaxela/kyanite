package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.JoystickMoveEvent;
import org.jsfml.window.event.MouseWheelEvent;

import java.util.EventListener;

/**
 * The listener interface for receiving the mouse wheel movement event. To add a {@code MouseWheelListener}, call the
 * {@link com.rubynaxela.kyanite.window.Window#addMouseWheelListener} with the parameter of an object implementing
 * this interface. When a mouse wheel movement event occurs, that object's {@code mouseWheelMoved} method is invoked.
 *
 * @see MouseWheelEvent
 */
public interface MouseWheelListener extends EventListener {

    /**
     * Invoked when the mouse wheel was moved while the window had focus.
     *
     * @param e the event to be processed
     */
    void mouseWheelMoved(MouseWheelEvent e);
}
