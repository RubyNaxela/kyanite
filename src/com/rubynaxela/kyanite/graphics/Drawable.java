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

package com.rubynaxela.kyanite.graphics;

import org.jetbrains.annotations.NotNull;

/**
 * Interface for objects that can be drawn to a render target. Implementing classes can be conveniently
 * used for the {@link RenderTarget#draw(Drawable)} method, but serve no additional purpose otherwise.
 */
@SuppressWarnings("deprecation")
public interface Drawable extends org.jsfml.graphics.Drawable {

    /**
     * Draws the object to a render target.
     *
     * @param target the target to draw this object on
     * @param states the current render states
     */
    void draw(@NotNull RenderTarget target, @NotNull RenderStates states);
}
