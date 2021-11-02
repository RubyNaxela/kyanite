package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.system.Clock;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.system.Time;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Provides a scene that can be given a custom behavior and displayed on a {@link com.rubynaxela.kyanite.window.Window}.
 */
public abstract class Scene extends RenderLayer {

    private final Clock clock = context.getClock();
    private Time previousFrameTime, currentFrameTime;

    /**
     * Creates an empty scene. Overriding this constructor is possible
     * but not recommended. Use the {@link #init} method instead.
     */
    public Scene() {
    }

    /**
     * Performs the full scene initialization, i.a. starts the global clock and calls
     * the {@link #init} method. This method should not be invoked manually.
     */
    @Contract(value = "-> fail")
    public final void fullInit() {
        if (!ready) {
            ready = true;
            clock.tryStart();
            previousFrameTime = clock.getTime();
            currentFrameTime = previousFrameTime;
            init();
        } else throw new IllegalStateException("This scene has been already initialized");
    }

    /**
     * This method is executed every frame by the window it belongs to.
     */
    protected abstract void loop();

    /**
     * Calls the {@link #loop} method and then every entity is animated (if it implements
     * {@link AnimatedEntity}) and drawn on the game window. This method is automatically
     * executed every frame by the window it belongs to and should not be invoked manually.
     *
     * @param window the window that the scene has to be displayed on
     */
    public final void fullLoop(@NotNull Window window) {
        loop();
        if (background != null) window.draw(background);
        forEach(object -> {
            if (object instanceof AnimatedEntity) ((AnimatedEntity) object).animate(getDeltaTime(), clock.getTime());
            window.draw(object);
        });
        previousFrameTime = currentFrameTime;
        currentFrameTime = clock.getTime();
    }

    /**
     * Gets the time that has passed between the previous and the current frame.
     *
     * @return the elapsed time since the clock was created or this function was last called.
     * @apiNote Calling this method during the first frame of the scene will return time estimated
     * by the window framerate limit as there was no previous frame time to compute the difference
     */
    public Time getDeltaTime() {
        try {
            final Constructor<Time> constructor = Time.class.getDeclaredConstructor(long.class);
            return constructor.newInstance((long) (1000000.0f / GameContext.getInstance().getWindow().getFramerateLimit()));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return Time.sub(currentFrameTime, previousFrameTime);
    }
}
