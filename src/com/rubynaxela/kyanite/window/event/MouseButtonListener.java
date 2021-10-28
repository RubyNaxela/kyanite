package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.MouseButtonEvent;

import java.util.EventListener;

public interface MouseButtonListener extends EventListener {

    /**
     * Invoked when a mouse button was pressed while the window had focus.
     *
     * @param e the event to be processed
     */
    void mouseButtonPressed(MouseButtonEvent e);

    /**
     * Invoked when a mouse button was released while the window had focus.
     *
     * @param e the event to be processed
     */
    void mouseButtonReleased(MouseButtonEvent e);
}
