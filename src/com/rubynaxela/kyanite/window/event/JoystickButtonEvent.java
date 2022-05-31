package com.rubynaxela.kyanite.window.event;

import com.rubynaxela.kyanite.input.Joystick;

/**
 * Represents joystick or gamepad button events. Objects of this class are created for events of
 * type {@link Event.Type#JOYSTICK_BUTTON_PRESSED} or {@link Event.Type#JOYSTICK_BUTTON_RELEASED}.
 */
public final class JoystickButtonEvent extends JoystickEvent {

    /**
     * The index of the button that was pressed or released. The value is guaranteed to range
     * between 0 (inclusive) and {@link Joystick#BUTTON_COUNT} (exclusive).
     */
    public final int button;

    /**
     * Constructs a new joystick button event.
     *
     * @param type       the type of the event (must be a valid ordinal in the {@link Event.Type} enumeration)
     * @param joystickId the joystick ID
     * @param button     the index of the button that was pressed
     */
    public JoystickButtonEvent(int type, int joystickId, int button) {
        super(type, joystickId);
        this.button = button;
    }
}
