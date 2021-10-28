package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.MouseWheelEvent;

import java.util.EventListener;

public interface MouseWheelListener extends EventListener {

    /**
     * Invoked when the mouse wheel was moved while the window had focus.
     *
     * @param e the event to be processed
     */
    void mouseWheelMoved(MouseWheelEvent e);
}
