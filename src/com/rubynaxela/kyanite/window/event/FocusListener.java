package com.rubynaxela.kyanite.window.event;

import java.util.EventListener;

/**
 * The listener interface for receiving the window focus event. To add a {@code FocusListener}, call the
 * {@link com.rubynaxela.kyanite.window.Window#addFocusListener} with the parameter of an object
 * implementing this interface. When the window gains focus, that object's {@code focusGained} method
 * is invoked, whereas when the window loses focus, that object's {@code focusLost} method is invoked.
 */
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
