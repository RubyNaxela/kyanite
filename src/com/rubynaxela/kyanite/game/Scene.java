package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.system.Clock;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.system.Time;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ConcurrentModificationException;

/**
 * Provides a scene that can be given a custom behavior and displayed on a {@link com.rubynaxela.kyanite.window.Window}.
 */
public abstract class Scene extends RenderLayer {

    private final Clock clock = context.getClock();
    private Time previousFrameTime, currentFrameTime;
    private boolean suspended = false;

    /**
     * Creates a "null" scene with empty {@link Scene#init} and {@link Scene#loop} methods.
     */
    public static Scene empty() {
        return new Scene() {
            @Override
            protected void init() {
            }

            @Override
            protected void loop() {
            }
        };
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
            currentFrameTime = previousFrameTime = clock.getTime();
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
        if (!suspended) {
            runScheduledActions();
            loop();
            if (background != null) window.draw(background);
            try {
                forEach(object -> {
                    if (object instanceof AnimatedEntity) ((AnimatedEntity) object).animate(getDeltaTime(), clock.getTime());
                    window.draw(object);
                });
            } catch (ConcurrentModificationException e) {
                throw new ConcurrentModificationException("Scene contents cannot be modified during a loop iteration." +
                                                          " In order to add or remove an object, use the" +
                                                          " scheduleToAdd(), scheduleToRemove() or schedule() method");
            }
            previousFrameTime = currentFrameTime;
            currentFrameTime = clock.getTime();
        }
    }

    /**
     * Gets the time that has passed between the previous and the current frame. Calling
     * this method during the first frame of the scene will return time estimated by the
     * window framerate limit as there was no previous frame time to compute the difference.
     *
     * @return time between the current and the previous frame
     */
    public Time getDeltaTime() {
        if (previousFrameTime.equals(currentFrameTime)) return estimatedTime();
        return Time.sub(currentFrameTime, previousFrameTime);
    }

    /**
     * @return whether the execution of this scene's loop is suspended
     */
    public boolean isSuspended() {
        return suspended;
    }

    /**
     * Suspends the execution of this scene's loop. Note that if a {@code HUD}'s
     * animated behvior relies on this scene's loop, it will be suspended as well.
     */
    public void suspend() {
        if (suspended) throw new IllegalStateException("This scene is already suspended");
        suspended = true;
    }

    /**
     * Resumes the execution of this scene's loop. The {@link #getDeltaTime} method called
     * right after this method will return time estimated by the window framerate limit.
     */
    public void resume() {
        if (!suspended) throw new IllegalStateException("This scene is not suspended");
        suspended = false;
        currentFrameTime = previousFrameTime = clock.getTime();
    }

    private Time estimatedTime() {
        try {
            final Constructor<Time> constructor = Time.class.getDeclaredConstructor(long.class);
            return constructor.newInstance((long) (1000000.0f / GameContext.getInstance().getWindow().getFramerateLimit()));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return Time.ZERO;
    }
}
