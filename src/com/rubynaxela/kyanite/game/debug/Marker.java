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

package com.rubynaxela.kyanite.game.debug;

import com.rubynaxela.kyanite.graphics.Colors;
import com.rubynaxela.kyanite.math.Vec2;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.graphics.CircleShape;
import com.rubynaxela.kyanite.math.Vector2f;

/**
 * A small red circle that can be used to mark a position while debugging the game.
 */
public class Marker extends CircleShape {

    /**
     * Constructs a new marker.
     *
     * @param position the initial position of the marker
     */
    public Marker(@NotNull Vector2f position) {
        super(4);
        setPosition(position);
        setOrigin(2, 2);
        setFillColor(Colors.RED);
    }

    /**
     * Constructs a new marker.
     *
     * @param x the initial x-coordinate of the marker
     * @param y the initial y-coordinate of the marker
     */
    public Marker(float x, float y) {
        this(Vec2.f(x, y));
    }
}
