package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.JoystickMoveEvent;

public interface JoystickListener {

    /**
     * Invoked when a joystick or gamepad axis was moved while the window had focus.
     *
     * @param e the event to be processed
     */
    void joystickMoved(JoystickMoveEvent e);
}
