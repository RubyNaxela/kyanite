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

import com.rubynaxela.kyanite.game.assets.Asset;
import com.rubynaxela.kyanite.util.Utils;
import org.jetbrains.annotations.NotNull;

/**
 * An animated texture that can be created from an array of {@link Texture}s with a fixed frame duration. Once applied on
 * a {@link Shape} or a {@link Sprite}, it is automatically updated using the current scene's clock as the time reference.
 */
public class AnimatedTexture implements ConstAnimatedTexture, Asset {

    private final ConstTexture[] frames;
    private final float frameDuration;

    /**
     * Creates an animated texture from the specified frames. Every frame will last {@code frameDuration} seconds.
     *
     * @param frames        the frames of this animated texture
     * @param frameDuration duration of a single frame (in seconds)
     */
    public AnimatedTexture(@NotNull ConstTexture[] frames, float frameDuration) {
        this.frames = frames;
        this.frameDuration = frameDuration;
    }

    /**
     * Creates an animated texture from the specified frames. Every frame will last
     * {@code frameDuration} seconds. The 2D array will be flattened into a 1D array.
     *
     * @param frames        the frames of this animated texture
     * @param frameDuration duration of a single frame (in seconds)
     */
    public AnimatedTexture(@NotNull ConstTexture[][] frames, float frameDuration) {
        this(Utils.flatten2DArray(ConstTexture.class, frames), frameDuration);
    }

    /**
     * Gets the frame of this animated texture at the specified index.
     *
     * @param index index of a frame
     * @return the frame of this animated texture at the specified index
     */
    @Override
    public ConstTexture getFrame(int index) {
        return frames[index];
    }

    /**
     * Returns the number of frames of this animated texture.
     *
     * @return the number of frames of this animated texture
     */
    @Override
    public int getFramesCount() {
        return frames.length;
    }

    /**
     * Gets the duration of a single frame (in seconds) set for this animated texture.
     *
     * @return the duration of a single frame
     */
    @Override
    public float getFrameDuration() {
        return frameDuration;
    }
}
