package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.TextEvent;

import java.util.EventListener;

public interface TextListener extends EventListener {

    /**
     * Invoked when a text character was entered using the keyboard while the window had focus.
     *
     * @param e the event to be processed
     */
    void textEntered(TextEvent e);
}
