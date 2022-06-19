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

package com.rubynaxela.kyanite.window;

import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.game.assets.AudioHandler;
import com.rubynaxela.kyanite.game.assets.Icon;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.game.entities.MouseActionListener;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.input.Mouse;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.util.Utils;
import com.rubynaxela.kyanite.window.event.*;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provides a window that can serve as a target for 2D drawing. The window is already initialized
 * with an empty scene to which {@link Drawable} objects can be added. The game window should
 * not be stored in static fields because after the game restarts, the window is a new object.
 */
public class Window extends RenderWindow {

    private final AudioHandler audioHandler;

    private final List<ResizeListener> resizeListeners = new ArrayList<>();
    private final List<FocusListener> focusListeners = new ArrayList<>();
    private final List<TextListener> textListeners = new ArrayList<>();
    private final List<KeyListener> keyListeners = new ArrayList<>();
    private final List<MouseWheelListener> mouseWheelListeners = new ArrayList<>();
    private final List<MouseButtonListener> mouseButtonListeners = new ArrayList<>();
    private final List<MouseListener> mouseListeners = new ArrayList<>();
    private final List<JoystickButtonListener> joystickButtonListeners = new ArrayList<>();
    private final List<JoystickListener> joystickListeners = new ArrayList<>();
    private final List<JoystickConnectionListener> joystickConnectionListeners = new ArrayList<>();
    private CloseListener closeListener = this::close;

    private boolean running = false, resizable = false;
    private int framerateLimit = 60;
    private String title;
    private Vector2i lastSetSize;
    private Scene scene = new Scene() {
        @Override
        public void init() {
        }

        @Override
        public void loop() {
        }
    };
    private HUD hud = new HUD() {
        @Override
        protected void init() {
        }
    };

    /**
     * Constructs a new render window and creates it with the specified parameters.
     *
     * @param videoMode       the video mode to use for rendering
     * @param title           the window title
     * @param style           the window style
     * @param contextSettings the settings for the OpenGL context
     * @param audioHandler    reference to the audio handler
     */
    public Window(@NotNull VideoMode videoMode, @NotNull String title,
                  @MagicConstant(flagsFromClass = WindowStyle.class) int style, @NotNull ContextSettings contextSettings,
                  @NotNull AudioHandler audioHandler) {
        super(videoMode, title, style, contextSettings);
        this.audioHandler = audioHandler;
        this.title = title;
        lastSetSize = Vec2.i(videoMode.width, videoMode.height);
        try {
            setIcon(new Icon(Objects.requireNonNull(getClass().getResourceAsStream("/res/kyanite.png"))));
        } catch (NullPointerException ignored) {
        }
        setFramerateLimit(60);
    }

    /**
     * Constructs a new render window and creates it with default context settings.
     *
     * @param videoMode    the video mode to use for rendering
     * @param title        the window title
     * @param style        the window style
     * @param audioHandler reference to the audio handler
     */
    public Window(@NotNull VideoMode videoMode, @NotNull String title,
                  @MagicConstant(flagsFromClass = WindowStyle.class) int style, @NotNull AudioHandler audioHandler) {
        this(videoMode, title, style, new ContextSettings(), audioHandler);
    }

    /**
     * Constructs a new render window and creates it with default style and context settings.
     *
     * @param videoMode    the video mode to use for rendering
     * @param title        the window title
     * @param audioHandler reference to the audio handler
     */
    public Window(@NotNull VideoMode videoMode, @NotNull String title, @NotNull AudioHandler audioHandler) {
        this(videoMode, title, WindowStyle.DEFAULT, audioHandler);
    }

    /**
     * @return a {@code GlobalRect} covering the entire window
     */
    @Contract(pure = true)
    public GlobalRect getBounds() {
        final Vector2f size = Vec2.f(getSize());
        return new GlobalRect(0, size.x, size.y, 0);
    }

    /**
     * Checks if any portion of the specified {@link Shape} object bounding rectangle is inside the window bounds.
     *
     * @param object a {@link Shape} object
     * @return whether any portion of the object bounding rectangle is inside the window
     */
    public boolean isInside(@NotNull Shape object) {
        return getBounds().intersects(GlobalRect.from(object.getGlobalBounds()));
    }

    /**
     * Checks if any portion of the specified {@link Sprite} object bounding rectangle is inside the window bounds.
     *
     * @param object a {@link Sprite} object
     * @return whether any portion of the object bounding rectangle is inside the window
     */
    public boolean isInside(@NotNull Sprite object) {
        return getBounds().intersects(GlobalRect.from(object.getGlobalBounds()));
    }

    /**
     * @return the frame rate limit in frames per second (this may be
     * {@link Window.Framerate#UNLIMITED} or {@link Window.Framerate#VSYNC})
     */
    public int getFramerateLimit() {
        return framerateLimit;
    }

    /**
     * Sets the maximum frame rate in frames per second. If a limit is set, the window loop will block for a short
     * delay time after flushing the buffer in order to possibly maintain a constant frame rate.<br>Activating
     * vertical synchronization is possible by calling this function with the {@link Window.Framerate#VSYNC} parameter.
     * This will limit the number of frames displayed to the refresh rate of the monitor, which can avoid some visual
     * artifacts, and limit the framerate to a good value (but not constant across different computers).
     *
     * @param framerateLimit the maximum frame rate in frames per second, {@link Window.Framerate#UNLIMITED} to
     *                       disable the limit, or {@link Window.Framerate#VSYNC} to enable vertical synchronization
     */
    @Override
    public void setFramerateLimit(int framerateLimit) {
        this.framerateLimit = framerateLimit;
        super.setVerticalSyncEnabled(framerateLimit == Framerate.VSYNC);
        if (framerateLimit <= 0) super.setFramerateLimit(Framerate.UNLIMITED);
        else super.setFramerateLimit(framerateLimit);
        scene.setMaxLagFactor(scene.getMaxLagFactor());
    }

    /**
     * Sets the icon of the window.
     *
     * @param icon the icon asset
     */
    public void setIcon(@NotNull Icon icon) {
        icon.apply(this);
    }

    /**
     * Sets the size of the window.
     *
     * @param width  the new width of the window
     * @param height the new height of the window
     */
    public void setSize(int width, int height) {
        setSize(Vec2.i(width, height));
    }

    /**
     * @return whether this window can be resized by the user
     */
    public boolean isResizable() {
        return resizable;
    }

    /**
     * Sets the ability for the user to resize this window. The default value is {@code false}. This only affects <b>the user's
     * </b> ability to resize this window. Even if this is set to {@code false}, {@link Window#setSize} can still be used.
     *
     * @param resizable true to enable looping, false to disable
     */
    public void setResizable(boolean resizable) {
        this.resizable = resizable;
    }

    /**
     * Sets the size of the window to fill the whole screen, excluding system toolbars.
     */
    public void maximize() {
        final VideoMode fillScreenMode = VideoMode.getDesktopMode();
        setSize(fillScreenMode.width, fillScreenMode.height);
        setPosition(Vec2.i(0, 0));
    }

    /**
     * Sets the specified close listener to receive the close button press event from this window.
     *
     * @param listener the close button press event listener
     */
    public void setCloseListener(@NotNull CloseListener listener) {
        closeListener = listener;
    }

    /**
     * Adds the specified resize listener to receive resize events from this window.
     *
     * @param listener the resize event listener
     */
    public void addResizeListener(@NotNull ResizeListener listener) {
        resizeListeners.add(listener);
    }

    /**
     * Removes the specified resize listener from this window.
     *
     * @param listener the resize event listener
     */
    public void removeResizeListener(@NotNull ResizeListener listener) {
        resizeListeners.remove(listener);
    }

    /**
     * Removes all previously added resize listeners of this window.
     */
    public void clearResizeListeners() {
        resizeListeners.clear();
    }

    /**
     * Adds the specified focus listener to receive focus events from this window.
     *
     * @param listener the focus event listener
     */
    public void addFocusListener(@NotNull FocusListener listener) {
        focusListeners.add(listener);
    }

    /**
     * Removes the specified focus listener from this window.
     *
     * @param listener the focus event listener
     */
    public void removeFocusListener(@NotNull FocusListener listener) {
        focusListeners.remove(listener);
    }

    /**
     * Removes all previously added focus listeners of this window.
     */
    public void clearFocusListeners() {
        focusListeners.clear();
    }

    /**
     * Adds the specified text listener to receive text input events from this window.
     *
     * @param listener the text event listener
     */
    public void addTextListener(@NotNull TextListener listener) {
        textListeners.add(listener);
    }

    /**
     * Removes the specified text listener from this window.
     *
     * @param listener the text event listener
     */
    public void removeTextListener(@NotNull TextListener listener) {
        textListeners.remove(listener);
    }

    /**
     * Removes all previously added text listeners of this window.
     */
    public void clearTextListeners() {
        textListeners.clear();
    }

    /**
     * Adds the specified key listener to receive key input events from this window.
     *
     * @param listener the key event listener
     */
    public void addKeyListener(@NotNull KeyListener listener) {
        keyListeners.add(listener);
    }

    /**
     * Removes the specified key listener from this window.
     *
     * @param listener the key event listener
     */
    public void removeKeyListener(@NotNull KeyListener listener) {
        keyListeners.remove(listener);
    }

    /**
     * Removes all previously added key listeners of this window.
     */
    public void clearKeyListeners() {
        keyListeners.clear();
    }

    /**
     * Adds the specified mouse wheel listener to receive mouse wheel input events from this window.
     *
     * @param listener the mouse wheel event listener
     */
    public void addMouseWheelListener(@NotNull MouseWheelListener listener) {
        mouseWheelListeners.add(listener);
    }

    /**
     * Removes the specified mouse wheel listener from this window.
     *
     * @param listener the mouse wheel event listener
     */
    public void removeMouseWheelListener(@NotNull MouseWheelListener listener) {
        mouseWheelListeners.remove(listener);
    }

    /**
     * Removes all previously added mouse wheel listeners of this window.
     */
    public void clearMouseWheelListeners() {
        mouseWheelListeners.clear();
    }

    /**
     * Adds the specified mouse button listener to receive mouse button events from this window.
     *
     * @param listener the mouse button event listener
     */
    public void addMouseButtonListener(@NotNull MouseButtonListener listener) {
        mouseButtonListeners.add(listener);
    }

    /**
     * Removes the specified mouse button listener from this window.
     *
     * @param listener the mouse button event listener
     */
    public void removeMouseButtonListener(@NotNull MouseButtonListener listener) {
        mouseButtonListeners.remove(listener);
    }

    /**
     * Removes all previously added mouse button listeners of this window.
     */
    public void clearMouseButtonListeners() {
        mouseButtonListeners.clear();
    }

    /**
     * Adds the specified mouse listener to receive mouse events from this window.
     *
     * @param listener the mouse button event listener
     */
    public void addMouseListener(@NotNull MouseListener listener) {
        mouseListeners.add(listener);
    }

    /**
     * Removes the specified mouse listener from this window.
     *
     * @param listener the mouse button event listener
     */
    public void removeMouseListener(@NotNull MouseListener listener) {
        mouseListeners.remove(listener);
    }

    /**
     * Removes all previously added mouse listeners of this window.
     */
    public void clearMouseListeners() {
        mouseListeners.clear();
    }

    /**
     * Adds the specified joystick button listener to receive joystick button events from this window.
     *
     * @param listener the joystick button event listener
     */
    public void addJoystickButtonListener(@NotNull JoystickButtonListener listener) {
        joystickButtonListeners.add(listener);
    }

    /**
     * Removes the specified joystick button listener from this window.
     *
     * @param listener the joystick button event listener
     */
    public void removeJoystickButtonListener(@NotNull JoystickButtonListener listener) {
        joystickButtonListeners.remove(listener);
    }

    /**
     * Removes all previously added joystick button listeners of this window.
     */
    public void clearJoystickButtonListeners() {
        joystickButtonListeners.clear();
    }

    /**
     * Adds the specified joystick movement listener to receive joystick movement events from this window.
     *
     * @param listener the joystick movement event listener
     */
    public void addJoystickListener(@NotNull JoystickListener listener) {
        joystickListeners.add(listener);
    }

    /**
     * Removes the specified joystick movement listener from this window.
     *
     * @param listener the joystick movement event listener
     */
    public void removeJoystickListener(@NotNull JoystickListener listener) {
        joystickListeners.remove(listener);
    }

    /**
     * Removes all previously added joystick movement listeners of this window.
     */
    public void clearJoystickListeners() {
        joystickListeners.clear();
    }

    /**
     * Adds the specified joystick connection listener to receive joystick connection and disconnection events from this window.
     *
     * @param listener the joystick connection event listener
     */
    public void addJoystickConnectionListener(@NotNull JoystickConnectionListener listener) {
        joystickConnectionListeners.add(listener);
    }

    /**
     * Removes the specified joystick connection listener from this window.
     *
     * @param listener the joystick connection event listener
     */
    public void removeJoystickConnectionListener(@NotNull JoystickConnectionListener listener) {
        joystickConnectionListeners.remove(listener);
    }

    /**
     * Removes all previously added joystick connection listeners of this window.
     */
    public void clearJoystickConnectionListener() {
        joystickConnectionListeners.clear();
    }

    /**
     * @return title of this window
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the window's title.
     *
     * @param title the window's new title.
     */
    public void setTitle(@NotNull String title) {
        super.setTitle(title);
        this.title = title;
    }

    /**
     * @param <T> the current render scene class
     * @return reference to the current render scene of this window.
     */
    @SuppressWarnings("unchecked")
    public <T extends Scene> T getScene() {
        return (T) scene;
    }

    /**
     * Changes the current scene of this window and initializes it (calls its {@code init()} method).
     *
     * @param scene the new scene to be displayed on this window
     * @return the reference to this window
     */
    public Window setScene(@NotNull Scene scene) {
        this.scene = scene;
        scene.fullInit();
        scene.setMaxLagFactor(scene.getMaxLagFactor());
        return this;
    }

    /**
     * @param <T> the current HUD layer class
     * @return reference to the current HUD layer of this window.
     */
    @SuppressWarnings("unchecked")
    public <T extends HUD> T getHUD() {
        return (T) hud;
    }

    /**
     * Changes the current HUD of this window and initializes it (calls its {@code init()} method).
     *
     * @param hud the new HUD to be displayed on this window
     * @return the reference to this window
     */
    public Window setHUD(@NotNull HUD hud) {
        this.hud = hud;
        hud.fullInit();
        return this;
    }

    /**
     * Starts the window loop.
     */
    public void startLoop() {
        if (!running) {
            running = true;
            while (isOpen()) {
                clear(scene.getBackgroundColor());
                handleEvents();
                scene.fullLoop(this);
                hud.refresh(this);
                display();
                audioHandler.gc();
            }
        } else throw new IllegalStateException("The window loop is already running");
    }

    private void handleEvents() {
        for (Event event = pollEvent(); event != null; event = pollEvent()) {
            final Event ev = event;
            switch (ev.type) {
                case CLOSED -> closeListener.closed();
                case RESIZED -> {
                    if (resizable) {
                        final Vector2f size = Vec2.f(getSize());
                        final Vector2i position = getPosition();
                        setView(Utils.lambdaInit((View) getView(),
                                                 v -> v.setSize(size), v -> v.setCenter(Vec2.multiply(size, 0.5f))));
                        setPosition(position);
                        scene.refreshBackgroundTexture();
                        hud.refreshBackgroundTexture();
                        new ArrayList<>(resizeListeners).forEach(action -> action.resized(new ResizeEvent(Vec2.i(size))));
                        lastSetSize = getSize();
                    } else setSize(lastSetSize);
                }
                case LOST_FOCUS -> new ArrayList<>(focusListeners).forEach(FocusListener::focusLost);
                case GAINED_FOCUS -> new ArrayList<>(focusListeners).forEach(FocusListener::focusGained);
                case TEXT_ENTERED -> new ArrayList<>(textListeners).forEach(l -> l.textEntered((TextEvent) ev));
                case KEY_PRESSED -> new ArrayList<>(keyListeners).forEach(l -> l.keyPressed((KeyEvent) ev));
                case KEY_RELEASED -> new ArrayList<>(keyListeners).forEach(l -> l.keyReleased((KeyEvent) ev));
                case MOUSE_WHEEL_MOVED ->
                        new ArrayList<>(mouseWheelListeners).forEach(l -> l.mouseWheelMoved((MouseWheelEvent) ev));
                case MOUSE_BUTTON_PRESSED -> {
                    new ArrayList<>(mouseButtonListeners).forEach(l -> l.mouseButtonPressed((MouseButtonEvent) ev));
                    boolean hudClicked = false;
                    for (int i = hud.size() - 1; i >= 0; i--) {
                        if (hud.get(i) instanceof final CompoundEntity compoundEntity) {
                            // TODO Make this work with unlimited CompoundEntity nesting, and with Scene
                            for (int j = compoundEntity.getComponents().size() - 1; j >= 0; j--) {
                                final MouseActionListener listener = Utils.cast(compoundEntity.getComponents().get(j),
                                                                                MouseActionListener.class);
                                if (listener != null &&
                                    listener.isCursorInside(Vec2.add(Vec2.subtract(Mouse.getPosition(this),
                                                                                   Vec2.i(compoundEntity.getPosition())),
                                                                     Vec2.i(compoundEntity.getOrigin())))) {
                                    listener.mouseButtonPressed((MouseButtonEvent) ev);
                                    hudClicked = true;
                                    break;
                                }
                            }
                        } else {
                            final MouseActionListener listener = Utils.cast(hud.get(i), MouseActionListener.class);
                            if (listener != null && listener.isCursorInside(Mouse.getPosition(this))) {
                                listener.mouseButtonPressed((MouseButtonEvent) ev);
                                hudClicked = true;
                                break;
                            }
                        }
                    }
                    if (!hudClicked) for (int i = scene.size() - 1; i >= 0; i--) {
                        final MouseActionListener listener = Utils.cast(scene.get(i), MouseActionListener.class);
                        if (listener != null && listener.isCursorInside(Mouse.getPosition(this))) {
                            listener.mouseButtonPressed((MouseButtonEvent) ev);
                            break;
                        }
                    }
                }
                case MOUSE_BUTTON_RELEASED -> {
                    new ArrayList<>(mouseButtonListeners).forEach(l -> l.mouseButtonReleased((MouseButtonEvent) ev));
                    for (int i = scene.size() - 1; i >= 0; i--) {
                        final MouseActionListener listener = Utils.cast(scene.get(i), MouseActionListener.class);
                        if (listener != null && listener.isCursorInside(Mouse.getPosition(this))) {
                            listener.mouseButtonReleased((MouseButtonEvent) ev);
                            break;
                        }
                    }
                }
                case MOUSE_MOVED -> new ArrayList<>(mouseListeners).forEach(l -> l.mouseMoved((MouseEvent) ev));
                case MOUSE_ENTERED -> new ArrayList<>(mouseListeners).forEach(l -> l.mouseEntered((MouseEvent) ev));
                case MOUSE_LEFT -> new ArrayList<>(mouseListeners).forEach(l -> l.mouseLeft((MouseEvent) ev));
                case JOYSTICK_BUTTON_PRESSED -> new ArrayList<>(joystickButtonListeners).forEach(
                        l -> l.joystickButtonPressed((JoystickButtonEvent) ev));
                case JOYSTICK_BUTTON_RELEASED -> new ArrayList<>(joystickButtonListeners).forEach(
                        l -> l.joystickButtonReleased((JoystickButtonEvent) ev));
                case JOYSTICK_MOVED -> new ArrayList<>(joystickListeners).forEach(l -> l.joystickMoved((JoystickMoveEvent) ev));
                case JOYSTICK_CONNECETED -> new ArrayList<>(joystickConnectionListeners).forEach(
                        l -> l.joystickConnected((JoystickEvent) ev));
                case JOYSTICK_DISCONNECTED -> new ArrayList<>(joystickConnectionListeners).forEach(
                        l -> l.joystickDisconnected((JoystickEvent) ev));
            }
        }
    }

    /**
     * Predefined framerate modes
     */
    public static class Framerate {

        /**
         * Unlimited framerate: game ticks are triggered as frequently as the computing power of the computer allows.
         */
        public static final int UNLIMITED = 0;

        /**
         * Vertical synchronization mode: game ticks are triggered at most once every monitor framerate.
         */
        public static final int VSYNC = -1;
    }
}
