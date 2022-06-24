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
 * A collection of collision detection algorithms used by the {@link Shape#intersects} method.
 */
public enum CollisionAlgorithm {

    /**
     * Axis-aligned bounding box method - checks the collision of axis aligned rectangles bounding the shapes.
     * Time complexity of this algorithm is <i>O(1)</i>. It is the fastest but the least accurate method,
     * unless the tested shapes are identical with (or acceptably close to) their bounding rectangles.
     */
    AABB,
    /**
     * Intersecting edges method - iterates over every pair <i>(e1,e2)</i>, where <i>e1</i> is an edge of the first shape
     * and <i>e2</i> is an edge of the second shape, such that both <i>e1</i> and <i>e2</i> have at least one point contained
     * within the {@link #AABB} intersection rectangle. Time worst case complexity of this algorithm is <i>O(n*m)</i>, where
     * <i>n</i> and <i>m</i> are the numbers of edges of the shapes. However, the actual number of edge intersection checks
     * is usually much lower. Since this algorithm performs an AABB check first (whose time complexity is <i>O(1)</i>),
     * if the shapes' axis aligned bounding rectangles do not intersect, the time complexity of this operation will
     * also be <i>O(1)</i>. This algorithm performs best for arbitrarily rotated shapes with relatively few edges.
     */
    EDGES
}
