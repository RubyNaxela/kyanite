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

import com.rubynaxela.kyanite.core.Const;
import com.rubynaxela.kyanite.math.Vector2i;

/**
 * Interface for read-only textures. It provides methods to can gain information from a texture,
 * but none to modify it in any way. Note that this interface is expected to be implemented by
 * a {@link Texture}. It is not recommended to be implemented outside of the Kyanite API.
 *
 * @see Const
 */
public interface ConstTexture extends Const {

    /**
     * Gets the dimensions of the texture.
     *
     * @return the dimensions of the texture
     */
    Vector2i getSize();

    /**
     * Copies this texture to an editable {@link Image}.
     *
     * @return the image that contains a coyp of the texure's contents
     */
    Image copyToImage();

    /**
     * Checks whether the smooth filter is enabled.
     *
     * @return {@code true} if enabled, {@code false} if disabled
     */
    boolean isSmooth();

    /**
     * Checks whether texture repeating is enabled.
     *
     * @return {@code true} if enabled, {@code false} if disabled
     */
    boolean isTileable();
}
