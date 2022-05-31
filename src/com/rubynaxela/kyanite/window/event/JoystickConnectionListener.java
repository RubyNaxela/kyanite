package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.window.Window;

/**
 * The listener interface for receiving the joystick connection event. To add a {@code JoystickConnectionListener},
 * call the {@link Window#addJoystickConnectionListener} with the parameter of an object
 * implementing this interface. When a joystick button event occurs, that object's {@code joystickConnected} or
 * {@code joystickDisconnected} method is invoked, depending on whether the connected was pressed or disconnected.
 *
 * @see JoystickEvent
 */
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
