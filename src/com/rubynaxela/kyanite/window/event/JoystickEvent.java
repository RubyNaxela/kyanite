package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.input.Joystick;

/**
 * Represents generic joystick or gamepad events. Objects of this class are created for events of type
 * {@link Event.Type#JOYSTICK_CONNECETED}, {@link Event.Type#JOYSTICK_DISCONNECTED},
 * {@link Event.Type#JOYSTICK_BUTTON_PRESSED}, {@link Event.Type#JOYSTICK_BUTTON_RELEASED}
 * or {@link Event.Type#JOYSTICK_MOVED}.
 */
public class JoystickEvent extends Event {

    /**
     * The index of the joystick that caused this event. The value is guaranteed to range
     * between 0 (inclusive) and {@link Joystick#JOYSTICK_COUNT} (exclusive)
     */
    public final int joystickId;

    /**
     * Constructs a new joystick event.
     *
     * @param type       the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param joystickId the joystick ID
     */
    public JoystickEvent(int type, int joystickId) {
        super(type);
        this.joystickId = joystickId;
    }
}
