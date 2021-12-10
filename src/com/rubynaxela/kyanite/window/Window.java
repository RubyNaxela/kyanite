package com.rubynaxela.kyanite.window;

import com.rubynaxela.kyanite.game.HUD;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.game.assets.Icon;
import com.rubynaxela.kyanite.game.entities.MouseActionListener;
import com.rubynaxela.kyanite.util.Utils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.event.*;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.ContextSettings;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provides a window that can serve as a target for 2D drawing. The window is already
 * initialized with an empty scene to which {@link Drawable} objects can be added.
 */
public class Window extends RenderWindow {

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
     */
    public Window(@NotNull VideoMode videoMode, @NotNull String title,
                  @MagicConstant(flagsFromClass = WindowStyle.class) int style, @NotNull ContextSettings contextSettings) {
        super(videoMode, title, style, contextSettings);
        lastSetSize = Vec2.i(videoMode.width, videoMode.height);
        try {
            setIcon(new Icon(Objects.requireNonNull(getClass().getResourceAsStream("/res/kyanite.png"))));
        } catch (NullPointerException ignored) {
        }
    }

    /**
     * Constructs a new render window and creates it with default context settings.
     *
     * @param videoMode the video mode to use for rendering
     * @param title     the window title
     * @param style     the window style
     */
    public Window(@NotNull VideoMode videoMode, @NotNull String title,
                  @MagicConstant(flagsFromClass = WindowStyle.class) int style) {
        this(videoMode, title, style, new ContextSettings());
    }

    /**
     * Constructs a new render window and creates it with default style and context settings.
     *
     * @param videoMode the video mode to use for rendering
     * @param title     the window title
     */
    public Window(@NotNull VideoMode videoMode, @NotNull String title) {
        this(videoMode, title, WindowStyle.DEFAULT);
    }

    /**
     * Constructs a new render window without actually creating (opening) it.
     */
    public Window() {
        super();
    }

    /**
     * Sets the size of the window.
     *
     * @param size the new size of the window
     */
    @Override
    public void setSize(@NotNull Vector2i size) {
        try {
            final Method method = org.jsfml.window.Window.class.getDeclaredMethod("nativeSetSize", int.class, int.class);
            method.setAccessible(true);
            method.invoke(this, size.x, size.y);
            lastSetSize = size;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @deprecated Use {@code setFramerateLimit(Window.Framerate.VSYNC)} instead.
     */
    @Contract("_ -> fail")
    @Deprecated
    @Override
    public void setVerticalSyncEnabled(boolean enable) {
        throw new UnsupportedOperationException("Use setFramerateLimit(Window.Framerate.VSYNC) instead");
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
     * @param framerateLimit the maximum frame rate in frames per second, {@link Window.Framerate#UNLIMITED}
     *                       to disable the limit, or {@link Window.Framerate#VSYNC} for vertical synchronization
     */
    @Override
    public void setFramerateLimit(int framerateLimit) {
        this.framerateLimit = framerateLimit;
        if (framerateLimit != Framerate.VSYNC) {
            super.setVerticalSyncEnabled(false);
            super.setFramerateLimit(framerateLimit);
        } else {
            super.setVerticalSyncEnabled(true);
            super.setFramerateLimit(Framerate.UNLIMITED);
        }
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
     * Sets the ability for the user to resize this window. The default value is {@code false}.
     *
     * @param resizable true to enable looping, false to disable
     * @implSpec This only affects <b>the user's</b> ability to resize this window.
     * Even if this is set to {@code false}, {@link Window#setSize} can still be used.
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
     * Sets the specified resize listener to receive resize events from this window and removes the previously added ones.
     *
     * @param listener the resize event listener
     * @deprecated Please use {@link Window#addResizeListener}
     */
    @Deprecated
    public void setResizeListener(@NotNull ResizeListener listener) {
        resizeListeners.clear();
        resizeListeners.add(listener);
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
     * Removes all previously added resize listeners of this window.
     */
    public void clearResizeListeners() {
        resizeListeners.clear();
    }

    /**
     * Sets the specified focus listener to receive focus events from this window and removes the previously added ones.
     *
     * @param listener the focus event listener
     * @deprecated Please use {@link Window#addFocusListener}
     */
    @Deprecated
    public void setFocusListener(@NotNull FocusListener listener) {
        focusListeners.clear();
        focusListeners.add(listener);
    }

    /**
     * Adds the specified focus listener to receive focus events from this window.
     *
     * @param listener the resize event listener
     */
    public void addFocusListener(@NotNull FocusListener listener) {
        focusListeners.add(listener);
    }

    /**
     * Removes all previously added focus listeners of this window.
     */
    public void clearFocusListeners() {
        focusListeners.clear();
    }

    /**
     * Sets the specified text listener to receive text input events from this window and removes the previously added ones.
     *
     * @param listener the text input event listener
     * @deprecated Please use {@link Window#addTextListener}
     */
    @Deprecated
    public void setTextListener(@NotNull TextListener listener) {
        textListeners.clear();
        textListeners.add(listener);
    }

    /**
     * Adds the specified text listener to receive text input events from this window.
     *
     * @param listener the resize event listener
     */
    public void addTextListener(@NotNull TextListener listener) {
        textListeners.add(listener);
    }

    /**
     * Removes all previously added text listeners of this window.
     */
    public void clearTextListeners() {
        textListeners.clear();
    }

    /**
     * Sets the specified key listener to receive key events from this window and removes the previously added ones.
     *
     * @param listener the key event listener
     * @deprecated Please use {@link Window#addKeyListener}
     */
    @Deprecated
    public void setKeyListener(@NotNull KeyListener listener) {
        keyListeners.clear();
        keyListeners.add(listener);
    }

    /**
     * Adds the specified key listener to receive key input events from this window.
     *
     * @param listener the resize event listener
     */
    public void addKeyListener(@NotNull KeyListener listener) {
        keyListeners.add(listener);
    }

    /**
     * Removes all previously added key listeners of this window.
     */
    public void clearKeyListeners() {
        keyListeners.clear();
    }

    /**
     * Sets the specified mouse wheel listener to receive mouse wheel
     * events from this window and removes the previously added ones.
     *
     * @param listener the mouse wheel event listener
     * @deprecated Please use {@link Window#addMouseWheelListener}
     */
    @Deprecated
    public void setMouseWheelListener(@NotNull MouseWheelListener listener) {
        mouseWheelListeners.clear();
        mouseWheelListeners.add(listener);
    }

    /**
     * Adds the specified mouse wheel listener to receive mouse wheel input events from this window.
     *
     * @param listener the resize event listener
     */
    public void addMouseWheelListener(@NotNull MouseWheelListener listener) {
        mouseWheelListeners.add(listener);
    }

    /**
     * Removes all previously added mouse wheel listeners of this window.
     */
    public void clearMouseWheelListeners() {
        mouseWheelListeners.clear();
    }

    /**
     * Sets the specified mouse button listener to receive mouse button
     * events from this window and removes the previously added ones.
     *
     * @param listener the mouse button event listener
     * @deprecated Please use {@link Window#addMouseButtonListener}
     */
    @Deprecated
    public void setMouseButtonListener(@NotNull MouseButtonListener listener) {
        mouseButtonListeners.clear();
        mouseButtonListeners.add(listener);
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
     * Removes all previously added mouse button listeners of this window.
     */
    public void clearMouseButtonListeners() {
        mouseButtonListeners.clear();
    }

    /**
     * Sets the specified mouse listener to receive mouse events from this window and removes the previously added ones.
     *
     * @param listener the mouse event listener
     * @deprecated Please use {@link Window#addMouseListener}
     */
    @Deprecated
    public void setMouseListener(@NotNull MouseListener listener) {
        mouseListeners.clear();
        mouseListeners.add(listener);
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
     * Removes all previously added mouse listeners of this window.
     */
    public void clearMouseListeners() {
        mouseListeners.clear();
    }

    /**
     * Sets the specified joystick button listener to receive joystick
     * button events from this window and removes the previously added ones.
     *
     * @param listener the joystick button event listener
     * @deprecated Please use {@link Window#addJoystickButtonListener}
     */
    @Deprecated
    public void setJoystickButtonListener(@NotNull JoystickButtonListener listener) {
        joystickButtonListeners.clear();
        joystickButtonListeners.add(listener);
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
     * Removes all previously added joystick button listeners of this window.
     */
    public void clearJoystickButtonListeners() {
        joystickButtonListeners.clear();
    }

    /**
     * Sets the specified joystick listener to receive joystick movement
     * events from this window and removes the previously added ones.
     *
     * @param listener the joystick movement event listener
     * @deprecated Please use {@link Window#addJoystickListener}
     */
    @Deprecated
    public void setJoystickListener(@NotNull JoystickListener listener) {
        joystickListeners.clear();
        joystickListeners.add(listener);
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
     * Removes all previously added joystick movement listeners of this window.
     */
    public void clearJoystickListeners() {
        joystickListeners.clear();
    }

    /**
     * Sets the specified joystick connection listener to receive joystick connection
     * and disconnection events from this window and removes the previously added ones.
     *
     * @param listener the joystick connection event listener
     */
    @Deprecated
    public void setJoystickConnectionListener(@NotNull JoystickConnectionListener listener) {
        joystickConnectionListeners.clear();
        joystickConnectionListeners.add(listener);
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
     * Removes all previously added joystick connection listeners of this window.
     */
    public void clearJoystickConnectionListener() {
        joystickConnectionListeners.clear();
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
     */
    public void setScene(@NotNull Scene scene) {
        this.scene = scene;
        scene.fullInit();
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
     */
    public void setHUD(@NotNull HUD hud) {
        this.hud = hud;
        hud.fullInit();
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
                hud.draw(this);
                display();
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
                                                 v -> v.setSize(size), v -> v.setCenter(Vector2f.mul(size, 0.5f))));
                        setPosition(position);
                        scene.refreshBackgroundTexture();
                        hud.refreshBackgroundTexture();
                        resizeListeners.forEach(action -> action.resized(new ResizeEvent(Vec2.i(size))));
                    } else setSize(lastSetSize);
                }
                case LOST_FOCUS -> focusListeners.forEach(FocusListener::focusLost);
                case GAINED_FOCUS -> focusListeners.forEach(FocusListener::focusGained);
                case TEXT_ENTERED -> textListeners.forEach(l -> l.textEntered((TextEvent) ev));
                case KEY_PRESSED -> keyListeners.forEach(l -> l.keyPressed((KeyEvent) ev));
                case KEY_RELEASED -> keyListeners.forEach(l -> l.keyReleased((KeyEvent) ev));
                case MOUSE_WHEEL_MOVED -> mouseWheelListeners.forEach(l -> l.mouseWheelMoved((MouseWheelEvent) ev));
                case MOUSE_BUTTON_PRESSED -> {
                    mouseButtonListeners.forEach(l -> l.mouseButtonPressed((MouseButtonEvent) ev));
                    for (int i = scene.size() - 1; i >= 0; i--) {
                        final MouseActionListener listener = Utils.cast(scene.get(i), MouseActionListener.class);
                        if (listener != null && listener.isCursorInside(Mouse.getPosition(this))) {
                            listener.mouseButtonPressed((MouseButtonEvent) ev);
                            break;
                        }
                    }
                }
                case MOUSE_BUTTON_RELEASED -> {
                    for (int i = scene.size() - 1; i >= 0; i--) {
                        final MouseActionListener listener = Utils.cast(scene.get(i), MouseActionListener.class);
                        if (listener != null && listener.isCursorInside(Mouse.getPosition(this))) {
                            listener.mouseButtonReleased((MouseButtonEvent) ev);
                            break;
                        }
                    }
                }
                case MOUSE_MOVED -> mouseListeners.forEach(l -> l.mouseMoved((MouseEvent) ev));
                case MOUSE_ENTERED -> mouseListeners.forEach(l -> l.mouseEntered((MouseEvent) ev));
                case MOUSE_LEFT -> mouseListeners.forEach(l -> l.mouseLeft((MouseEvent) ev));
                case JOYSTICK_BUTTON_PRESSED -> joystickButtonListeners.forEach(
                        l -> l.joystickButtonPressed((JoystickButtonEvent) ev));
                case JOYSTICK_BUTTON_RELEASED -> joystickButtonListeners.forEach(
                        l -> l.joystickButtonReleased((JoystickButtonEvent) ev));
                case JOYSTICK_MOVED -> joystickListeners.forEach(l -> l.joystickMoved((JoystickMoveEvent) ev));
                case JOYSTICK_CONNECETED -> joystickConnectionListeners.forEach(
                        l -> l.joystickConnected((JoystickEvent) ev));
                case JOYSTICK_DISCONNECTED -> joystickConnectionListeners.forEach(
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
