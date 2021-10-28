package com.rubynaxela.kyanite.window.event;

import java.util.EventListener;

public interface ResizeListener extends EventListener {

    /**
     * Invoked when the window was resized.
     *
     * @param e the event to be processed
     */
    void resized(ResizeEvent e);
}
