package com.rubynaxela.kyanite.window;

import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.game.assets.Icon;
import com.rubynaxela.kyanite.game.entities.MouseActionListener;
import com.rubynaxela.kyanite.util.Utils;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.event.*;
import org.intellij.lang.annotations.MagicConstant;
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
import java.util.Objects;

/**
 * Provides a window that can serve as a target for 2D drawing. The window is already
 * initialized with an empty scene to which {@link Drawable} objects can be added.
 */
public class Window extends RenderWindow {

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
    private CloseListener closeListener = this::close;
    private ResizeListener resizeListener = e -> {
    };
    private FocusListener focusListener = new FocusListener() {
        @Override
        public void focusGained() {
        }

        @Override
        public void focusLost() {
        }
    };
    private TextListener textListener = e -> {
    };
    private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    };
    private MouseWheelListener mouseWheelListener = e -> {
    };
    private MouseButtonListener mouseButtonListener = new MouseButtonListener() {
        @Override
        public void mouseButtonPressed(MouseButtonEvent e) {
        }

        @Override
        public void mouseButtonReleased(MouseButtonEvent e) {
        }
    };
    private MouseListener mouseListener = new MouseListener() {
        @Override
        public void mouseMoved(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseLeft(MouseEvent e) {
        }
    };
    private JoystickButtonListener joystickButtonListener = new JoystickButtonListener() {
        @Override
        public void joystickButtonPressed(JoystickButtonEvent e) {
        }

        @Override
        public void joystickButtonReleased(JoystickButtonEvent e) {
        }
    };
    private JoystickListener joystickListener = e -> {
    };
    private JoystickConnectionListener joystickConnectionListener = new JoystickConnectionListener() {
        @Override
        public void joystickConnected(JoystickEvent e) {
        }

        @Override
        public void joystickDisconnected(JoystickEvent e) {
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
     * @apiNote This only affects <b>the user's</b> ability to resize this window.
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
     * Adds the specified close listener to receive the close button press event from this window.
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
    public void setResizeListener(@NotNull ResizeListener listener) {
        resizeListener = listener;
    }

    /**
     * Adds the specified focus listener to receive focus events from this window.
     *
     * @param listener the focus event listener
     */
    public void setFocusListener(@NotNull FocusListener listener) {
        focusListener = listener;
    }

    /**
     * Adds the specified text listener to receive text input events from this window.
     *
     * @param listener the text input event listener
     */
    public void setTextListener(@NotNull TextListener listener) {
        this.textListener = listener;
    }

    /**
     * Adds the specified key listener to receive key events from this window.
     *
     * @param listener the key event listener
     */
    public void setKeyListener(@NotNull KeyListener listener) {
        this.keyListener = listener;
    }

    /**
     * Adds the specified mouse wheel listener to receive mouse wheel events from this window.
     *
     * @param listener the mouse wheel event listener
     */
    public void setMouseWheelListener(@NotNull MouseWheelListener listener) {
        this.mouseWheelListener = listener;
    }

    /**
     * Adds the specified mouse button listener to receive mouse button events from this window.
     *
     * @param listener the mouse button event listener
     */
    public void setMouseButtonListener(@NotNull MouseButtonListener listener) {
        this.mouseButtonListener = listener;
    }

    /**
     * Adds the specified mouse listener to receive mouse events from this window.
     *
     * @param listener the mouse event listener
     */
    public void setMouseListener(@NotNull MouseListener listener) {
        this.mouseListener = listener;
    }

    /**
     * Adds the specified joystick button listener to receive joystick button events from this window.
     *
     * @param listener the joystick button event listener
     */
    public void setJoystickButtonListener(@NotNull JoystickButtonListener listener) {
        this.joystickButtonListener = listener;
    }

    /**
     * Adds the specified joystick listener to receive joystick movement events from this window.
     *
     * @param listener the joystick movement event listener
     */
    public void setJoystickListener(@NotNull JoystickListener listener) {
        this.joystickListener = listener;
    }

    /**
     * Adds the specified joystick connection listener to receive joystick connection and disconnection events from this window.
     *
     * @param listener the joystick connection event listener
     */
    public void setJoystickConnectionListener(@NotNull JoystickConnectionListener listener) {
        this.joystickConnectionListener = listener;
    }

    /**
     * @return reference to the current render scene of this window.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Changes the current scene of this window and initializes it (calls its {@code init()} method).
     *
     * @param scene the new scene to be displayed on this window
     */
    public void setScene(@NotNull Scene scene) {
        this.scene = scene;
        scene.fullInit(this);
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
                display();
            }
        } else throw new IllegalStateException("The window loop is already running");
    }

    private void handleEvents() {
        // TODO Add position change event
        for (Event event = pollEvent(); event != null; event = pollEvent()) {
            switch (event.type) {
                case CLOSED -> closeListener.closed();
                case RESIZED -> {
                    if (resizable) {
                        final Vector2f size = Vec2.f(getSize());
                        final Vector2i position = getPosition();
                        setView(Utils.lambdaInit((View) getView(),
                                                 v -> v.setSize(size), v -> v.setCenter(Vector2f.mul(size, 0.5f))));
                        setPosition(position);
                        resizeListener.resized(new ResizeEvent(Vec2.i(size)));
                    } else setSize(lastSetSize);
                }
                case LOST_FOCUS -> focusListener.focusLost();
                case GAINED_FOCUS -> focusListener.focusGained();
                case TEXT_ENTERED -> textListener.textEntered((TextEvent) event);
                case KEY_PRESSED -> keyListener.keyPressed((KeyEvent) event);
                case KEY_RELEASED -> keyListener.keyReleased((KeyEvent) event);
                case MOUSE_WHEEL_MOVED -> mouseWheelListener.mouseWheelMoved((MouseWheelEvent) event);
                case MOUSE_BUTTON_PRESSED -> {
                    mouseButtonListener.mouseButtonPressed((MouseButtonEvent) event);
                    for (int i = scene.size(); i >= 0; i--) {
                        final MouseActionListener listener = Utils.cast(scene.get(i), MouseActionListener.class);
                        if (listener != null && listener.isCursorInside(Mouse.getPosition(this))) {
                            listener.mouseButtonPressed((MouseButtonEvent) event);
                            break;
                        }
                    }
                }
                case MOUSE_BUTTON_RELEASED -> {
                    for (int i = scene.size(); i >= 0; i--) {
                        final MouseActionListener listener = Utils.cast(scene.get(i), MouseActionListener.class);
                        if (listener != null && listener.isCursorInside(Mouse.getPosition(this))) {
                            listener.mouseButtonReleased((MouseButtonEvent) event);
                            break;
                        }
                    }
                }
                case MOUSE_MOVED -> mouseListener.mouseMoved((MouseEvent) event);
                case MOUSE_ENTERED -> mouseListener.mouseEntered((MouseEvent) event);
                case MOUSE_LEFT -> mouseListener.mouseLeft((MouseEvent) event);
                case JOYSTICK_BUTTON_PRESSED -> joystickButtonListener.joystickButtonPressed((JoystickButtonEvent) event);
                case JOYSTICK_BUTTON_RELEASED -> joystickButtonListener.joystickButtonReleased((JoystickButtonEvent) event);
                case JOYSTICK_MOVED -> joystickListener.joystickMoved((JoystickMoveEvent) event);
                case JOYSTICK_CONNECETED -> joystickConnectionListener.joystickConnected((JoystickEvent) event);
                case JOYSTICK_DISCONNECTED -> joystickConnectionListener.joystickDisconnected((JoystickEvent) event);
            }
        }
    }

    public static class Framerate {

        public static final int UNLIMITED = 0, VSYNC = -1;
    }
}
