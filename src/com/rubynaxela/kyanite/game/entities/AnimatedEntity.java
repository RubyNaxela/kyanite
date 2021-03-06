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

import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.util.Time;

/**
 * This interface represents an entity with an animation. The animation method is called every frame.
 */
public interface AnimatedEntity {

    /**
     * This method is called every window frame. It is designed to be used for defining the entity animated behavior.
     *
     * @param deltaTime   the time that has passed between the previous and the current frame
     * @param elapsedTime the time that has passed since you the game was started
     */
    void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime);
}
