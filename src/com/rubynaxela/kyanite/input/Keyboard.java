/*
 * Copyright (c) 2021-2022 Alex Pawelski
 *
 * Licensed under the Silicon License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://rubynaxela.github.io/Silicon-License/plain_text.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.rubynaxela.kyanite.input;

import com.rubynaxela.kyanite.core.SFMLNative;
import com.rubynaxela.kyanite.math.Direction;
import com.rubynaxela.kyanite.window.event.KeyListener;
import org.jetbrains.annotations.NotNull;

/**
 * Provides access to the real-time state of the keyboard. The methods in this class provide
 * direct access to the keyboard state, that means that they work independently of a window's
 * focus. In order to react to window based events, use the {@link KeyListener} interface instead.
 */
@SuppressWarnings("deprecation")
public final class Keyboard extends org.jsfml.window.Keyboard {

    private static final int NORTH = 0, EAST = 1, SOUTH = 2, WEST = 3;

    static {
        SFMLNative.loadNativeLibraries();
    }

    private Keyboard() {
    }

    /**
     * Checks if a certain key is currently pressed on the keyboard.
     *
     * @param key the key in question
     * @return {@code true} if the key is currently being pressed, {@code false} otherwise
     */
    public static boolean isKeyPressed(@NotNull Key key) {
        return nativeIsKeyPressed(key.ordinal() - 1);
    }

    /**
     * Gets the direction currently inputted on the keyboard, according to the specified control setting.
     * The inter-cardinal directions are allowed (i.e. two keys for perpendicular directions can be pressed
     * at a time). To disable them, use the {@link #getDirection(MovementControls, Direction.Axis)} method.
     *
     * @param controls a control setting
     * @return the direction being currently inputted on the keyboard
     */
    public static Direction getDirection(@NotNull MovementControls controls) {
        final boolean[] keys = getDirectionKeys(controls);
        final boolean north = keys[NORTH] && !keys[SOUTH],
                east = keys[EAST] && !keys[WEST],
                south = keys[SOUTH] && !keys[NORTH],
                west = keys[WEST] && !keys[EAST];
        if (north) {
            if (east) return Direction.NORTH_EAST;
            else if (west) return Direction.NORTH_WEST;
            else return Direction.NORTH;
        } else if (south) {
            if (east) return Direction.SOUTH_EAST;
            else if (west) return Direction.SOUTH_WEST;
            else return Direction.SOUTH;
        } else if (east) return Direction.EAST;
        else if (west) return Direction.WEST;
        return Direction.NULL;
    }

    /**
     * Gets the direction currently inputted on the keyboard, according to the specified control
     * setting. Inter-cardinal directions are not allowed (i.e. two keys for perpendicular directions
     * cannot be pressed at a time). To resolve conflicts, the specified axis is prioritized.
     *
     * @param controls a control setting
     * @param priority the axis to take priority when two keys for perpendicular directions are pressed
     * @return the direction being currently inputted on the keyboard
     */
    public static Direction getDirection(@NotNull MovementControls controls, @NotNull Direction.Axis priority) {
        final boolean[] keys = getDirectionKeys(controls);
        if (priority == Direction.Axis.Y) {
            if (keys[NORTH] && !keys[SOUTH]) return Direction.NORTH;
            else if (keys[SOUTH] && !keys[NORTH]) return Direction.SOUTH;
        }
        if (keys[EAST] && !keys[WEST]) return Direction.EAST;
        else if (keys[WEST] && !keys[EAST]) return Direction.WEST;
        if (keys[NORTH] && !keys[SOUTH]) return Direction.NORTH;
        else if (keys[SOUTH] && !keys[NORTH]) return Direction.SOUTH;
        return Direction.NULL;
    }

    private static boolean[] getDirectionKeys(@NotNull MovementControls controls) {
        boolean[] keys = new boolean[4];
        keys[NORTH] = isKeyPressed(controls.getNorthKey());
        keys[EAST] = isKeyPressed(controls.getEastKey());
        keys[SOUTH] = isKeyPressed(controls.getSouthKey());
        keys[WEST] = isKeyPressed(controls.getWestKey());
        return keys;
    }

    /**
     * Enumeration of supported keys. The keys in this enumeration are named after the standard <i>QWERTY</i> keyboard
     * layout. Their locations and labels may vary on different keyboard layouts, even though the scan codes, and
     * therefore their representation in this enumeration, are the same. There may be some non-standard keys on certain
     * keyboards that are mapped to unknown scan codes. Those special keys will be represented by {@link Key#UNKNOWN}.
     */
    public enum Key {

        /**
         * A keyboard specific key which maps to an unknown scan code.
         */
        UNKNOWN,
        /**
         * The {@code A} key.
         */
        A,
        /**
         * The {@code B} key.
         */
        B,
        /**
         * The {@code C} key.
         */
        C,
        /**
         * The {@code D} key.
         */
        D,
        /**
         * The {@code E} key.
         */
        E,
        /**
         * The {@code F} key.
         */
        F,
        /**
         * The {@code G} key.
         */
        G,
        /**
         * The {@code H} key.
         */
        H,
        /**
         * The {@code I} key.
         */
        I,
        /**
         * The {@code J} key.
         */
        J,
        /**
         * The {@code K} key.
         */
        K,
        /**
         * The {@code L} key.
         */
        L,
        /**
         * The {@code M} key.
         */
        M,
        /**
         * The {@code N} key.
         */
        N,
        /**
         * The {@code O} key.
         */
        O,
        /**
         * The {@code P} key.
         */
        P,
        /**
         * The {@code Q} key.
         */
        Q,
        /**
         * The {@code R} key.
         */
        R,
        /**
         * The {@code S} key.
         */
        S,
        /**
         * The {@code T} key.
         */
        T,
        /**
         * The {@code U} key.
         */
        U,
        /**
         * The {@code V} key.
         */
        V,
        /**
         * The {@code W} key.
         */
        W,
        /**
         * The {@code X} key.
         */
        X,
        /**
         * The {@code Y} key.
         */
        Y,
        /**
         * The {@code Z} key.
         */
        Z,
        /**
         * The {@code 0} key.
         *
         * @see #NUMPAD0 for the {@code 0} key on the numeric keypad
         */
        NUM0,
        /**
         * The {@code 1} key.
         *
         * @see #NUMPAD1 for the {@code 1} key on the numeric keypad
         */
        NUM1,
        /**
         * The {@code 2} key.
         *
         * @see #NUMPAD2 for the {@code 2} key on the numeric keypad
         */
        NUM2,
        /**
         * The {@code 3} key.
         *
         * @see #NUMPAD3 for the {@code 3} key on the numeric keypad
         */
        NUM3,
        /**
         * The {@code 4} key.
         *
         * @see #NUMPAD4 for the {@code 4} key on the numeric keypad
         */
        NUM4,
        /**
         * The {@code 5} key.
         *
         * @see #NUMPAD5 for the {@code 5} key on the numeric keypad
         */
        NUM5,
        /**
         * The {@code 6} key.
         *
         * @see #NUMPAD6 for the {@code 6} key on the numeric keypad
         */
        NUM6,
        /**
         * The {@code 7} key.
         *
         * @see #NUMPAD7 for the {@code 7} key on the numeric keypad
         */
        NUM7,
        /**
         * The {@code 8} key.
         *
         * @see #NUMPAD8 for the {@code 8} key on the numeric keypad
         */
        NUM8,
        /**
         * The {@code 9} key.
         *
         * @see #NUMPAD9 for the {@code 9} key on the numeric keypad
         */
        NUM9,
        /**
         * The escape key.
         */
        ESCAPE,
        /**
         * The left Ctrl key.
         */
        LCONTROL,
        /**
         * The left Shift key.
         */
        LSHIFT,
        /**
         * The left Alt key.
         */
        LALT,
        /**
         * The left system key. This is commonly known as the Windows logo key or the Apple key.
         */
        LSYSTEM,
        /**
         * The right Ctrl key.
         */
        RCONTROL,
        /**
         * The right Shift key.
         */
        RSHIFT,
        /**
         * The right Alt key.
         */
        RALT,
        /**
         * The right system key. This is commonly known as the Windows logo key or the Apple key.
         */
        RSYSTEM,
        /**
         * The menu key.
         */
        MENU,
        /**
         * The {@code [} key.
         */
        LBRACKET,
        /**
         * The {@code ]} key.
         */
        RBRACKET,
        /**
         * The {@code ;} key.
         */
        SEMICOLON,
        /**
         * The {@code ,} key.
         */
        COMMA,
        /**
         * {@code .} key.
         */
        PERIOD,
        /**
         * The {@code '} key.
         */
        QUOTE,
        /**
         * The {@code /} key.
         */
        SLASH,
        /**
         * The {@code \} key.
         */
        BACKSLASH,
        /**
         * The {@code ~} key.
         */
        TILDE,
        /**
         * The {@code =} key.
         */
        EQUAL,
        /**
         * The {@code -} key.
         */
        DASH,
        /**
         * The Space key.
         */
        SPACE,
        /**
         * The Return key or the Enter key on the numeric keypad. Both keys
         * send the same scancode and can therefore not be distinguished.
         */
        RETURN,
        /**
         * The Backspace key.
         */
        BACKSPACE,
        /**
         * The Tab key.
         */
        TAB,
        /**
         * The Page up key.
         */
        PAGEUP,
        /**
         * The Page down key.
         */
        PAGEDOWN,
        /**
         * The End key.
         */
        END,
        /**
         * The Home key.
         */
        HOME,
        /**
         * The Insert key.
         */
        INSERT,
        /**
         * The Delete key.
         */
        DELETE,
        /**
         * The {@code +} (addition) key on the numeric keypad.
         */
        ADD,
        /**
         * The {@code -} (subtraction) key on the numeric keypad.
         */
        SUBTRACT,
        /**
         * The {@code *} (multiplication) key on the numeric keypad.
         */
        MULTIPLY,
        /**
         * The {@code /} (division) key on the numeric keypad.
         */
        DIVIDE,
        /**
         * The left arrow key.
         */
        LEFT,
        /**
         * The right arrow key.
         */
        RIGHT,
        /**
         * The up arrow key.
         */
        UP,
        /**
         * The down arrow key.
         */
        DOWN,
        /**
         * The {@code 0} key on the numeric keypad.
         *
         * @see #NUM0 for the regular {@code 0} key
         */
        NUMPAD0,
        /**
         * The {@code 1} key on the numeric keypad.
         *
         * @see #NUM1 for the regular {@code 1} key
         */
        NUMPAD1,
        /**
         * The {@code 2} key on the numeric keypad.
         *
         * @see #NUM2 for the regular {@code 2} key
         */
        NUMPAD2,
        /**
         * The {@code 3} key on the numeric keypad.
         *
         * @see #NUM3 for the regular {@code 3} key
         */
        NUMPAD3,
        /**
         * The {@code 4} key on the numeric keypad.
         *
         * @see #NUM4 for the regular {@code 4} key
         */
        NUMPAD4,
        /**
         * The {@code 5} key on the numeric keypad.
         *
         * @see #NUM5 for the regular {@code 5} key
         */
        NUMPAD5,
        /**
         * The {@code 6} key on the numeric keypad.
         *
         * @see #NUM6 for the regular {@code 6} key
         */
        NUMPAD6,
        /**
         * The {@code 7} key on the numeric keypad.
         *
         * @see #NUM7 for the regular {@code 7} key
         */
        NUMPAD7,
        /**
         * The {@code 8} key on the numeric keypad.
         *
         * @see #NUM8 for the regular {@code 8} key
         */
        NUMPAD8,
        /**
         * The {@code 9} key on the numeric keypad.
         *
         * @see #NUM9 for the regular {@code 9} key
         */
        NUMPAD9,
        /**
         * The {@code F1} key.
         */
        F1,
        /**
         * The {@code F2} key.
         */
        F2,
        /**
         * The {@code F3} key.
         */
        F3,
        /**
         * The {@code F4} key.
         */
        F4,
        /**
         * The {@code F5} key.
         */
        F5,
        /**
         * The {@code F6} key.
         */
        F6,
        /**
         * The {@code F7} key.
         */
        F7,
        /**
         * The {@code F8} key.
         */
        F8,
        /**
         * The {@code F9} key.
         */
        F9,
        /**
         * The {@code F10} key.
         */
        F10,
        /**
         * The {@code F11} key.
         */
        F11,
        /**
         * The {@code F12} key.
         */
        F12,
        /**
         * The {@code F13} key, where available.
         */
        F13,
        /**
         * The {@code F14} key, where available.
         */
        F14,
        /**
         * The {@code F15} key, where available.
         */
        F15,
        /**
         * The Pause key.
         */
        PAUSE
    }

    /**
     * Interface for 2-axis movement controls. Objects implementing this interface
     * can be used as quick settings of key binding for objects movement control.
     */
    public interface MovementControls {

        /**
         * The key associated with going north (typically forwards, up, or jumping; towards the top of the window).
         *
         * @return the key associated with going north
         */
        @NotNull Key getNorthKey();

        /**
         * The key associated with going east (typically right or forwards; towards the right side of the window).
         *
         * @return the key associated with going east
         */
        @NotNull Key getEastKey();

        /**
         * The key associated with going south (typically backwards down or sneaking; towards the bottom of the window,).
         *
         * @return the key associated with going south
         */
        @NotNull Key getSouthKey();

        /**
         * The key associated with going west (typically left or backwards; towards the left side of the window).
         *
         * @return the key associated with going west
         */
        @NotNull Key getWestKey();
    }

    /**
     * Provides a set of common movement controls.
     */
    public static final class StandardControls {

        /**
         * The standard WASD movement control setting:
         * <ul>
         *     <li>north: {@link Key#W}</li>
         *     <li>east: {@link Key#D}</li>
         *     <li>south: {@link Key#S}</li>
         *     <li>west: {@link Key#A}</li>
         * </ul>
         */
        public static final MovementControls WASD = new MovementControls() {
            @Override
            @NotNull
            public Key getNorthKey() {
                return Key.W;
            }

            @Override
            @NotNull
            public Key getEastKey() {
                return Key.D;
            }

            @Override
            @NotNull
            public Key getSouthKey() {
                return Key.S;
            }

            @Override
            @NotNull
            public Key getWestKey() {
                return Key.A;
            }
        };

        /**
         * The standard arrows movement control setting:
         * <ul>
         *     <li>north: {@link Key#UP}</li>
         *     <li>east: {@link Key#RIGHT}</li>
         *     <li>south: {@link Key#DOWN}</li>
         *     <li>west: {@link Key#LEFT}</li>
         * </ul>
         */
        public static final MovementControls ARROWS = new MovementControls() {
            @Override
            @NotNull
            public Key getNorthKey() {
                return Key.UP;
            }

            @Override
            @NotNull
            public Key getEastKey() {
                return Key.RIGHT;
            }

            @Override
            @NotNull
            public Key getSouthKey() {
                return Key.DOWN;
            }

            @Override
            @NotNull
            public Key getWestKey() {
                return Key.LEFT;
            }
        };
    }
}
