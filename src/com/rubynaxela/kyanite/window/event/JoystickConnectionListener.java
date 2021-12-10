package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.JoystickEvent;

public interface JoystickConnectionListener {

    /**
     * Invoked when a joystick or gamepad was connected..
     *
     * @param e the event to be processed
     */
    default void joystickConnected(JoystickEvent e) {
    }

    /**
     * Invoked when a joystick or gamepad was disconnected.
     *
     * @param e the event to be processed
     */
    default void joystickDisconnected(JoystickEvent e) {
    }
}
