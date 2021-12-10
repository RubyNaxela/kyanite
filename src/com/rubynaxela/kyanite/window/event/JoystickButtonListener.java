package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.JoystickButtonEvent;

import java.util.EventListener;

public interface JoystickButtonListener extends EventListener {

    /**
     * Invoked when a joystick or gamepad button was pressed while the window had focus.
     *
     * @param e the event to be processed
     */
    default void joystickButtonPressed(JoystickButtonEvent e) {
    }

    /**
     * Invoked when a joystick or gamepad button was released while the window had focus.
     *
     * @param e the event to be processed
     */
    default void joystickButtonReleased(JoystickButtonEvent e) {
    }
}
