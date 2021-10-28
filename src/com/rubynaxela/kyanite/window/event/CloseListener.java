package com.rubynaxela.kyanite.window.event;

import java.util.EventListener;

public interface CloseListener extends EventListener {

    /**
     * Invoked when the user clicked on the window's close button.
     */
    void closed();
}
