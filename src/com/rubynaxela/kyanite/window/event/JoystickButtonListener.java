package com.rubynaxela.kyanite.window.event;

import org.jsfml.window.event.JoystickButtonEvent;

import java.util.EventListener;

/**
 * The listener interface for receiving the joystick button event. To add a {@code JoystickButtonListener}, call
 * the {@link com.rubynaxela.kyanite.window.Window#addJoystickButtonListener} with the parameter of an object
 * implementing this interface. When a joystick button event occurs, that object's {@code joystickButtonPressed} or
 * {@code joystickButtonReleased} method is invoked, depending on whether the event button was pressed or released.
 *
 * @see JoystickButtonEvent
 */
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
