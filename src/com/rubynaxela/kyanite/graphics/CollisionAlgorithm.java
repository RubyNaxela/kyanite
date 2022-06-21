/*
 * Copyright (c) 2022 Alex Pawelski
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

/**
 * A collecion of collision detection algorithms used by the {@link Shape#intersects} method.
 */
public enum CollisionAlgorithm {

    /**
     * Axis-aligned bounding box method - checks the collision of axis aligned rectangles bounding
     * the shapes. The fastest but the least accurate method, unless the tested shapes are
     * identical with their bounding rectangles. Time complexity of this algorithm is <i>O(1)</i>.
     */
    AABB,
    EDGES
}
