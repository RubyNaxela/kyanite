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

package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.game.entities.CompoundEntity;
import com.rubynaxela.kyanite.game.entities.MovingEntity;
import com.rubynaxela.kyanite.graphics.Drawable;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.physics.GravityAffected;
import com.rubynaxela.kyanite.system.Clock;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ConcurrentModificationException;

/**
 * Provides a scene that can be given a custom behavior and displayed on a {@link Window}.
 */
public abstract non-sealed class Scene extends RenderLayer {

    private final Clock clock = (Clock) context.getClock();
    private Time previousFrameTime, currentFrameTime;
    private boolean suspended = false;
    private float maxLagFactor = 5f, maxSceneDuration = 5f / 60;

    /**
     * Creates a "null" scene with empty {@link Scene#init} and {@link Scene#loop} methods.
     *
     * @return an empty scene
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
     * Calls the {@link #loop} method, then every entity is animated (if it implements {@link AnimatedEntity}
     * or {@link MovingEntity}) and drawn on the game window. This method is automatically
     * executed every frame by the window it belongs to and should not be invoked manually.
     *
     * @param window the window that the scene has to be displayed on
     */
    public final void fullLoop(@NotNull Window window) {
        if (!suspended) {
            runScheduledActions();
            if (getDeltaTime().asSeconds() <= maxSceneDuration) {
                loop();
                if (background != null) window.draw(background);
                try {
                    forEach(object -> {
                        final Time et = getElapsedTime();
                        final float dt = getDeltaTime().asSeconds();
                        if (object instanceof final GravityAffected entity)
                            entity.setVelocity(Vec2.add(entity.getVelocity(), Vec2.f(0, entity.getGravity() * dt)));
                        if (object instanceof final AnimatedEntity entity) entity.animate(getDeltaTime(), et);
                        updateAnimatedTexture(object, et);
                        if (object instanceof final CompoundEntity entity)
                            for (final Drawable component : entity.getComponents()) updateAnimatedTexture(component, et);
                        if (object instanceof final MovingEntity entity) entity.move(Vec2.multiply(entity.getVelocity(), dt));
                    });
                } catch (ConcurrentModificationException e) {
                    throw new ConcurrentModificationException("Scene contents cannot be modified during a loop iteration." +
                                                              " In order to add or remove an object, use the" +
                                                              " scheduleToAdd(), scheduleToRemove() or schedule() method");
                }
            }
        }
        forEach(window::draw);
        previousFrameTime = currentFrameTime;
        currentFrameTime = clock.getTime();
    }

    /**
     * Gets the time that has passed between the previous and the current frame. Calling
     * this method during the first frame of the scene will return time estimated by the
     * window framerate limit as there was no previous frame time to compute the difference.
     *
     * @return time between the current and the previous frame
     */
    public Time getDeltaTime() {
        if (previousFrameTime == null || previousFrameTime.equals(currentFrameTime)) return estimatedTime();
        return Time.sub(currentFrameTime, previousFrameTime);
    }

    /**
     * Gets the elapsed time since the game was started.
     *
     * @return the elapsed time since the game was started
     */
    public Time getElapsedTime() {
        return clock.getTime();
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

    /**
     * @return current max lag factor for this scene
     */
    public float getMaxLagFactor() {
        return maxLagFactor;
    }

    /**
     * Sets the max lag factor for this scene. This determines how many times the time of a frame can be longer than
     * it would result from the set limit of frames per second. The default value is 5. If a frame lasted longer than
     * {@code maxLagFactor / getContext().getWindow().getFramerateLimit()}, the scene iteration for the next frame
     * is not performed. This is to prevent {@code deltaTime} value passed to animation methods from being too high.
     *
     * @param maxLagFactor new max lag factor for this scene
     */
    public void setMaxLagFactor(float maxLagFactor) {
        this.maxLagFactor = maxLagFactor;
        if (getContext().getWindow() != null) {
            final int framerate = getContext().getWindow().getFramerateLimit();
            this.maxSceneDuration = framerate > 0 ? maxLagFactor / framerate : Float.MAX_VALUE;
        }
    }

    private Time estimatedTime() {
        final int framerate = getContext().getWindow().getFramerateLimit();
        return Time.s(1f / (framerate > 0 ? framerate : 60));
    }
}
