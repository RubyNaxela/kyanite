package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.window.Window;

import java.util.EventListener;

/**
 * The listener interface for receiving the text event. To add a {@code TextListener},
 * call the {@link Window#addTextListener} with the parameter
 * of an object implementing this interface. When a text character was entered using the
 * keyboard while the window had focus, that object's {@code textEntered} method is invoked.
 *
 * @see TextEvent
 */
public interface TextListener extends EventListener {

    /**
     * Invoked when a text character was entered using the keyboard while the window had focus.
     *
     * @param e the event to be processed
     */
    void textEntered(TextEvent e);
}
