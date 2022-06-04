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

import com.rubynaxela.kyanite.math.FloatRect;

/**
 * Represents objects that have determinable local and global bounds.
 */
public interface BoundsObject {

    /**
     * Gets the object's local bounding rectangle, <i>not</i> taking its transformation into account.
     *
     * @return the object's local bounding rectangle
     * @see Shape#getLocalBounds()
     * @see Sprite#getLocalBounds()
     * @see Text#getLocalBounds()
     * @see VertexArray#getLocalBounds()
     */
    FloatRect getLocalBounds();

    /**
     * Gets the object's global bounding rectangle in the scene, taking its transformation into account.
     *
     * @return the object's local bounding rectangle
     * @see Shape#getGlobalBounds()
     * @see Sprite#getGlobalBounds()
     * @see Text#getGlobalBounds()
     * @see VertexArray#getGlobalBounds()
     */
    FloatRect getGlobalBounds();
}
