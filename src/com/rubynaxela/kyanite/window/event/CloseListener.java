package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.window.Window;

import java.util.EventListener;

/**
 * The listener interface for receiving the window close event. To add a {@code CloseListener}, call the
 * {@link Window#setCloseListener} method with the parameter of an object
 * implementing this interface. When the close event occurs, that object's {@code closed} method is invoked.
 */
public interface CloseListener extends EventListener {

    /**
     * Invoked when the user clicked on the window's close button.
     */
    void closed();
}
