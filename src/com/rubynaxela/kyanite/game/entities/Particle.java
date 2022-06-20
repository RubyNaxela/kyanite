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

package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.graphics.CircleShape;
import com.rubynaxela.kyanite.graphics.Texture;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.util.Time;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a temporary object displaying a sequence of textures during its lifetime.
 */
public class Particle extends CircleShape implements AnimatedEntity, MovingEntity {

    private final Scene scene;
    private final Texture[] frames;
    private final float lifetime;
    private Vector2f velocity;
    private float timeSpawned;
    private int stage = 0;

    /**
     * Constructs a {@code Particle} with the specified parameters.
     *
     * @param scene    the scene this particle belongs to
     * @param frames   the sequence of textures to be displayed (duration of a single frame is {@code lifetime / frames.length})
     * @param lifetime the duration of this {@code Particle}'s lifetime (in seconds)
     * @param velocity the velocity at which this {@code Particle} moves
     */
    public Particle(@NotNull Scene scene, @NotNull Texture[] frames, float lifetime, @NotNull Vector2f velocity) {
        super(64);
        this.scene = scene;
        this.frames = frames;
        this.lifetime = lifetime;
        this.velocity = velocity;
    }

    /**
     * Constructs a non-moving {@code Particle} with the specified parameters.
     *
     * @param scene    the scene this particle belongs to
     * @param frames   the sequence of textures to be displayed (duration of a single frame is {@code lifetime / frames.length})
     * @param lifetime the duration of this {@code Particle}'s lifetime
     */
    public Particle(@NotNull Scene scene, @NotNull Texture[] frames, float lifetime) {
        this(scene, frames, lifetime, Vector2f.zero());
    }

    private void nextFrame() {
        setTexture(frames[stage++]);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        if (stage == 0) {
            timeSpawned = GameContext.getInstance().getClock().getTime().asSeconds();
            nextFrame();
        } else for (int i = 0; i < frames.length; i++) {
            if (stage == frames.length && elapsedTime.asSeconds() - timeSpawned > lifetime) {
                scene.scheduleToRemove(this);
                break;
            } else if (stage == i && elapsedTime.asSeconds() - timeSpawned > (float) i / frames.length * lifetime) {
                nextFrame();
                break;
            }
        }
    }

    @Override
    public @NotNull Vector2f getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(@NotNull Vector2f velocity) {
        this.velocity = velocity;
    }
}
