package com.rubynaxela.kyanite.window;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.core.SFMLError;
import com.rubynaxela.kyanite.core.SFMLNative;
import com.rubynaxela.kyanite.math.Vector2i;
import com.rubynaxela.kyanite.window.event.*;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.graphics.Image;
import org.jsfml.window.Window;

import java.nio.IntBuffer;
import java.util.Iterator;

/**
 * A basic window that provides an OpenGL context. This class implements the
 * {@link WindowStyle} interface for quick access to the constants provided by it.
 */
@SuppressWarnings("deprecation")
public class BasicWindow extends Window {

    /**
     * The current window icon image. A reference to it must be maintained
     * in order to assure that the image will not be garbage-collected.
     */
    private Image icon = null;

    /**
     * Constructs a new window without actually creating it (making it visible).
     *
     * @see BasicWindow#create(VideoMode, String, int, ContextSettings)
     */
    public BasicWindow() {
        SFMLNative.ensureDisplay();
    }

    /**
     * Constructs a new window within the specified parent window.
     *
     * @param ptr the window handle of the parent window
     * @deprecated Use of this method may cause undefined behaviour and is not supported.
     */
    @Deprecated
    protected BasicWindow(long ptr) {
        super(ptr);
    }

    /**
     * Constructs a new window and creates it with the specified settings.
     *
     * @param mode     the window's video mode
     * @param title    the window title
     * @param style    the window style
     * @param settings the settings for the OpenGL context
     * @see #create(VideoMode, String, int, ContextSettings)
     */
    public BasicWindow(@NotNull VideoMode mode, @NotNull String title,
                       @MagicConstant(flagsFromClass = WindowStyle.class) int style, @NotNull ContextSettings settings) {
        create(mode, title, style, settings);
    }

    /**
     * Constructs a new window and creates it with the specified settings and default context settings.
     *
     * @param mode  the window's video mode
     * @param title the window title
     * @param style the window style
     */
    public BasicWindow(@NotNull VideoMode mode, @NotNull String title,
                       @MagicConstant(flagsFromClass = WindowStyle.class) int style) {
        create(mode, title, style, new ContextSettings());
    }

    /**
     * Constructs a new window and creates it with the specified settings and default context settings and window style.
     *
     * @param mode  the window's video mode
     * @param title the window title
     */
    public BasicWindow(@NotNull VideoMode mode, @NotNull String title) {
        create(mode, title, WindowStyle.DEFAULT, new ContextSettings());
    }

    private static Event decodeEvent(@NotNull IntBuffer eventBuffer) {
        final Event event;
        final int typeId = eventBuffer.get(0);
        if (typeId >= 0) {
            final Event.Type type = Event.Type.values()[typeId];
            switch (type) {
                case CLOSED, GAINED_FOCUS, LOST_FOCUS -> event = new Event(typeId);
                case RESIZED -> event = new SizeEvent(typeId, eventBuffer.get(1), eventBuffer.get(2));
                case TEXT_ENTERED -> event = new TextEvent(typeId, eventBuffer.get(1));
                case KEY_PRESSED, KEY_RELEASED -> {
                    final int keyCode = eventBuffer.get(1);
                    final int flags = eventBuffer.get(2);
                    event = new KeyEvent(typeId, keyCode, (flags & 0x01) != 0, (flags & 0x02) != 0,
                                         (flags & 0x04) != 0, (flags & 0x08) != 0);
                }
                case MOUSE_WHEEL_MOVED -> event = new MouseWheelEvent(typeId, eventBuffer.get(1), eventBuffer.get(2),
                                                                      eventBuffer.get(3));
                case MOUSE_BUTTON_PRESSED, MOUSE_BUTTON_RELEASED -> event = new MouseButtonEvent(typeId, eventBuffer.get(1),
                                                                                                 eventBuffer.get(2),
                                                                                                 eventBuffer.get(3));
                case MOUSE_MOVED, MOUSE_LEFT, MOUSE_ENTERED -> event = new MouseEvent(typeId, eventBuffer.get(1),
                                                                                      eventBuffer.get(2));
                case JOYSTICK_BUTTON_PRESSED, JOYSTICK_BUTTON_RELEASED -> event = new JoystickButtonEvent(typeId,
                                                                                                          eventBuffer.get(1),
                                                                                                          eventBuffer.get(2));
                case JOYSTICK_MOVED -> event = new JoystickMoveEvent(typeId, eventBuffer.get(1), eventBuffer.get(2),
                                                                     Float.intBitsToFloat(eventBuffer.get(3)));
                case JOYSTICK_CONNECETED, JOYSTICK_DISCONNECTED -> event = new JoystickEvent(typeId, eventBuffer.get(1));
                default -> event = null;
            }
        } else {
            event = null;
        }

        return event;
    }

    /**
     * Checks whether the current native thread is eligibile for creating a window. This will
     * always be the case on Windows or Linux, but on Mac OS X, it will check whether the JVM was
     * started in the main thread using the {@code -XstartOnFirstThread} command line parameter.
     *
     * @return {@code true} if the current native thread may create a window, {@code false} otherwise
     */
    public static boolean isLegalWindowThread() {
        return Window.isLegalWindowThread();
    }

    /**
     * Creates and opens a window or re-creates it if it was already opened.
     *
     * @param mode     the video mode that determines the window's size (must be a
     *                 valid video mode in case {@link WindowStyle#FULLSCREEN} is set)
     * @param title    the window title
     * @param style    the style of the window
     * @param settings the context settings for the created OpenGL context.
     * @see VideoMode#isValid()
     * @see WindowStyle
     * @see ContextSettings
     */
    public void create(@NotNull VideoMode mode, @NotNull String title,
                       @MagicConstant(flagsFromClass = WindowStyle.class) int style, @NotNull ContextSettings settings) {
        if (!isLegalWindowThread()) {
            throw new SFMLError("This thread is not allowed to create a window on this system. " +
                                "If you are running on Mac OS X, you MUST run your " +
                                "application with the -XstartOnFirstThread command line argument!");
        }

        if ((style & FULLSCREEN) != 0 && !mode.isValid())
            throw new IllegalArgumentException("Invalid video mode for a fullscreen window.");

        final IntBuffer params = IntercomHelper.getBuffer().asIntBuffer();
        params.put(0, mode.width);
        params.put(1, mode.height);
        params.put(2, mode.bitsPerPixel);
        params.put(3, style);
        params.put(4, settings.depthBits);
        params.put(5, settings.stencilBits);
        params.put(6, settings.antialiasingLevel);
        params.put(7, settings.majorVersion);
        params.put(8, settings.minorVersion);
        nativeCreateWindow(params, title);
    }

    /**
     * Creates and opens a window or re-creates it if it was already opened.
     * The default context settings will be used for the OpenGL context.
     *
     * @param mode  the video mode that determines the window's size (must be a
     *              valid video mode in case {@link WindowStyle#FULLSCREEN} is set)
     * @param title the window title
     * @param style the style of the window
     * @see VideoMode#isValid()
     * @see WindowStyle
     */
    public final void create(@NotNull VideoMode mode, @NotNull String title,
                             @MagicConstant(flagsFromClass = WindowStyle.class) int style) {
        create(mode, title, style, new ContextSettings());
    }

    /**
     * Creates and opens a window or re-creates it if it was already opened. The {@link WindowStyle#DEFAULT}
     * window style will be applied and default context settings will be used for the OpenGL context.
     *
     * @param mode  the video mode that determines the window's size
     * @param title the window title
     */
    public final void create(@NotNull VideoMode mode, @NotNull String title) {
        create(mode, title, DEFAULT, new ContextSettings());
    }

    /**
     * Closes the window and destroys all attached resources,
     * including the OpenGL context provided by it.
     */
    @Override
    public void close() {
        super.close();
    }

    /**
     * Checks if the window has been created and is opened. Note that the <i>open</i> state is not directly affected
     * by the user clicking the window's <i>close</i> button, if available. In order to accomplish that, listen
     * to an event of type {@link Event.Type#CLOSED} and use the {@link #close()} method.
     *
     * @return {@code true} if the window has been created and is currently open
     */
    @Override
    public boolean isOpen() {
        return super.isOpen();
    }

    /**
     * Enables or disables vertical synchronization (VSync). Activating vertical synchronization will limit the number of
     * frames displayed to the refresh rate of the monitor. This can avoid some visual artifacts, and limit the framerate
     * to a good value (but not constant across different computers). This should not be used in combination with {@link
     * #setFramerateLimit}, as these two will conflict with one another. By default, vertical synchronization is disabled.
     *
     * @param enable {@code true} to enable vertical synchronization, {@code false} to disable
     */
    @Override
    public void setVerticalSyncEnabled(boolean enable) {
        super.setVerticalSyncEnabled(enable);
    }

    /**
     * Determines whether the mouse cursor, if moved over the window, is visible or not
     *
     * @param show {@code true} to make the cursor visible, {@code false} to hide it
     */
    @Override
    public void setMouseCursorVisible(boolean show) {
        super.setMouseCursorVisible(show);
    }

    /**
     * Shows or hides the window
     *
     * @param show {@code true} to show the window, {@code false} to hide it
     */
    @Override
    public void setVisible(boolean show) {
        super.setVisible(show);
    }

    /**
     * Determines whether automatic key repeat is enabled. If enabled, multiple key press events will
     * be fired when a key stays pressed (much like in a text field). Key repeat is enabled by default.
     *
     * @param enable {@code true} to enable, {@code false} to disabled
     */
    @Override
    public void setKeyRepeatEnabled(boolean enable) {
        super.setKeyRepeatEnabled(enable);
    }

    /**
     * Flushes the OpenGL pixel buffer to the screen. This should be called every frame
     * after everything has been drawn in order to make the changes visible in the window.
     */
    @Override
    public void display() {
        super.display();
    }

    /**
     * Sets the maximum frame rate in frames per second. If a limit is set, the {@link #display()}
     * method will blockfor a short delay time after flushing the buffer in order to possibly maintain
     * a constant frame rate. This should not be used in combination with {@link #setVerticalSyncEnabled}}
     * as these two will conflict with one another. By default, there is no frame rate limit.
     *
     * @param limit the maximum frame rate in frames per second, or {@code 0} to disable the frame rate limit
     */
    @Override
    public void setFramerateLimit(int limit) {
        super.setFramerateLimit(limit);
    }

    /**
     * Gets the absolute position of the window's top left corner on the screen.
     *
     * @return the absolute position of the window's top left corner on the screen
     */
    public Vector2i getPosition() {
        return IntercomHelper.decodeVector2i(nativeGetPosition());
    }

    /**
     * Sets the absolute position of the window's top left corner on the screen.
     *
     * @param position the new absolute position of the window's top left corner on the screen
     */
    public void setPosition(@NotNull Vector2i position) {
        nativeSetPosition(position.x, position.y);
    }

    /**
     * Gets the size of the window.
     *
     * @return the size of the window
     */
    public Vector2i getSize() {
        return IntercomHelper.decodeVector2i(nativeGetSize());
    }

    /**
     * Sets the size of the window.
     *
     * @param size the new size of the window
     */
    public void setSize(Vector2i size) {
        nativeSetSize(size.x, size.y);
    }

    /**
     * Retrieves the context settings for the window's OpenGL context.
     *
     * @return the context settings for the window's OpenGL context
     */
    public ContextSettings getSettings() {
        final IntBuffer settings = IntercomHelper.getBuffer().asIntBuffer();
        nativeGetSettings(settings);
        return new ContextSettings(settings.get(0), settings.get(1), settings.get(2), settings.get(3), settings.get(4));
    }

    /**
     * Pops the event on top of the event stack, if any, and returns it. This method needs to be called
     * regularly in order to process pending events. If this is not done, the window will be unresponsive
     *
     * @return the event currently on top of the event stack, or {@code null} if there is none.
     * @see #waitEvent()
     */
    public Event pollEvent() {
        final IntBuffer buffer = IntercomHelper.getBuffer().asIntBuffer();
        nativePollEvent(buffer);
        return decodeEvent(buffer);
    }

    /**
     * Returns an {@link Iterable} that consecutively calls {@link #pollEvent()} and can be used to process all pending events.
     *
     * @return an {@code Iterable} over all pending events
     * @see #pollEvent()
     */
    public Iterable<Event> pollEvents() {
        return () -> new Iterator<>() {

            private Event nextEvent = pollEvent();

            @Override
            public boolean hasNext() {
                return (nextEvent != null);
            }

            @Override
            public Event next() {
                Event currentEvent = nextEvent;
                nextEvent = pollEvent();
                return currentEvent;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    /**
     * Pops the event on top of the event stack and returns it, or, if there is none, waits until an event
     * occurs and then returns it. This method will block the program flow until an event is returned.
     *
     * @return the event currently on top of the event stack, or the next event that will occur
     * @see #pollEvent()
     */
    public Event waitEvent() {
        final IntBuffer buffer = IntercomHelper.getBuffer().asIntBuffer();
        nativeWaitEvent(buffer);
        return decodeEvent(buffer);
    }

    /**
     * Sets the window's title
     *
     * @param title the window's new title
     */
    public void setTitle(@NotNull String title) {
        nativeSetTitle(title);
    }

    /**
     * Sets the icon of the window.
     *
     * @param icon the icon image.
     */
    public void setIcon(@NotNull Image icon) {
        this.icon = icon;
        nativeSetIcon(icon);
    }

    /**
     * Activates or deactivates the window as the current OpenGL rendering target. If a window gets
     * activated, all other windows operating in the same thread will automatically be deactivated.
     *
     * @param active {@code true} to activate, {@code false} to deactivate
     * @throws ContextActivationException in case window activation fails
     */
    public void setActive(boolean active) throws ContextActivationException {
        if (!nativeSetActive(active))
            throw new ContextActivationException("Failed to " + (active ? "" : "de") + "activate the window's context.");
    }

    /**
     * Activates the window as the current OpenGL rendering target. If a window gets activated,
     * all other windows operating in the same thread will automatically be deactivated.
     *
     * @throws ContextActivationException in case window activation fails.
     */
    public final void setActive() throws ContextActivationException {
        setActive(true);
    }

    /**
     * Sets the joystick threshold. Joystick axis movements with a magnitude smaller than
     * this treshold will not fire a joystick event. The default joystick treshold is 0.1.
     *
     * @param threshold the joystick threshold, ranging between 0 and 100
     * @see JoystickMoveEvent
     */
    public void setJoystickThreshold(float threshold) {
        super.setJoystickTreshold(threshold);
    }
}
