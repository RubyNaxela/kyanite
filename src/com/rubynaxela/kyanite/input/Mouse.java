package com.rubynaxela.kyanite.input;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.core.SFMLNative;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.window.BasicWindow;
import com.rubynaxela.kyanite.window.event.MouseButtonListener;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Provides access to the the real-time state of the mouse. The methods in this class provide
 * direct access to the mouse state, that means that they work independently of a window's focus.
 * In order to react to window based events, use the {@link MouseButtonListener} interface instead.
 */
@SuppressWarnings("deprecation")
public final class Mouse extends org.jsfml.window.Mouse {

    static {
        SFMLNative.loadNativeLibraries();
    }

    private Mouse() {
    }

    /**
     * Checks if a mouse button is currently pressed.
     *
     * @param button the mouse button in question
     * @return {@code true} if the button is currently being pressed, {@code false} otherwise
     */
    public static boolean isButtonPressed(@NotNull Button button) {
        return nativeIsButtonPressed(button.ordinal());
    }

    /**
     * Retrieves the absolute position of the mouse cursor on the screen.
     *
     * @return the absolute position of the mouse cursor on the screen
     */
    public static Vector2i getPosition() {
        return IntercomHelper.decodeVector2i(nativeGetPosition());
    }

    /**
     * Sets the absolute position of the mouse cursor on the screen.
     *
     * @param position the new absolute position of the mouse cursor on the screen
     */
    public static void setPosition(@NotNull Vector2i position) {
        nativeSetPosition(IntercomHelper.encodeVector2i(position));
    }

    /**
     * Retrieves the position of the mouse cursor relative to a window.
     *
     * @param relativeTo the window in question
     * @return the position of the mouse cursor relative to the window's top left corner
     */
    public static Vector2i getPosition(@NotNull BasicWindow relativeTo) {
        return IntercomHelper.decodeVector2i(nativeGetPosition(Objects.requireNonNull(relativeTo)));
    }

    /**
     * Sets the position of the mouse cursor relative to a window.
     *
     * @param position   the new position of the mouse cursor relative to the window's top left corner
     * @param relativeTo the window in question
     */
    public static void setPosition(@NotNull Vector2i position, @NotNull BasicWindow relativeTo) {
        nativeSetPosition(IntercomHelper.encodeVector2i(position), Objects.requireNonNull(relativeTo));
    }

    /**
     * Enumeration of supported mouse buttons.
     */
    public enum Button {

        /**
         * The left mouse button.
         */
        LEFT,
        /**
         * The right mouse button.
         */
        RIGHT,
        /**
         * The middle mouse button, or mouse wheel on many mouses, if available.
         */
        MIDDLE,
        /**
         * The first extra mouse button, if available.
         */
        XBUTTON1,
        /**
         * The second extra mouse button, if available.
         */
        XBUTTON2,
    }
}
