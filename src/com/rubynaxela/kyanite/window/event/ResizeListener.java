package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.window.Window;

import java.util.EventListener;

/**
 * The listener interface for receiving the window resize event. To add a {@code ResizeListener}, call
 * the {@link Window#addResizeListener} with the parameter of an object
 * implementing this interface. When the window is resized, that object's {@code resized} method is invoked.
 *
 * @see ResizeEvent
 */
public interface ResizeListener extends EventListener {

    /**
     * Invoked when the window was resized.
     *
     * @param e the event to be processed
     */
    void resized(ResizeEvent e);
}
