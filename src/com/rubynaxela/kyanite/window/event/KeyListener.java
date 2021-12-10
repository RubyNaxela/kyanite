package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.KeyEvent;

import java.util.EventListener;

public interface KeyListener extends EventListener {

    /**
     * Invoked when a keyboard key was pressed while the window had focus.
     *
     * @param e the event to be processed
     */
    default void keyPressed(KeyEvent e) {
    }

    /**
     * Invoked when a keyboard key was released while the window had focus.
     *
     * @param e the event to be processed
     */
    default void keyReleased(KeyEvent e) {
    }
}
