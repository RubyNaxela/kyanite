package com.rubynaxela.kyanite.window.event;

import java.util.EventListener;

public interface FocusListener extends EventListener {

    /**
     * Invoked when the window lost focus.
     */
    default void focusGained() {
    }

    /**
     * Invoked when the window gained focus.
     */
    default void focusLost() {
    }
}
