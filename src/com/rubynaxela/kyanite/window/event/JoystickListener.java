package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.window.Window;

/**
 * The listener interface for receiving the joystick movement event. To add a {@code JoystickListener}, call the
 * {@link Window#addJoystickListener} with the parameter of an object implementing
 * this interface. When a joystick movement event occurs, that object's {@code joystickMoved} method is invoked.
 *
 * @see JoystickMoveEvent
 */
public interface JoystickListener {

    /**
     * Invoked when a joystick or gamepad axis was moved while the window had focus.
     *
     * @param e the event to be processed
     */
    void joystickMoved(JoystickMoveEvent e);
}
