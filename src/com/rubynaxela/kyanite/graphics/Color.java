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

import com.rubynaxela.kyanite.math.MathUtils;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents RGBA colors.
 */
public final class Color implements Serializable {

    @Serial
    private static final long serialVersionUID = -161207563051572152L;

    /**
     * The red component of the color, ranging between 0 and 255.
     */
    public final int r;
    /**
     * The green component of the color, ranging between 0 and 255.
     */
    public final int g;
    /**
     * The blue component of the color, ranging between 0 and 255.
     */
    public final int b;
    /**
     * The alpha component of the color, ranging between 0 (transparent) and 255 (fully opaque).
     */
    public final int a;

    /**
     * Constructs a new color with the specified color components and an alpha value of 255 (fully opaque)
     *
     * @param r the color's red component, ranging between 0 and 255
     * @param g the color's green component, ranging between 0 and 255
     * @param b the color's blue component, ranging between 0 and 255
     */
    public Color(int r, int g, int b) {
        this(r, g, b, 255);
    }

    /**
     * Constructs a new color with the specified color and alpha components.
     *
     * @param r the color's red component, ranging between 0 and 255
     * @param g the color's green component, ranging between 0 and 255
     * @param b the color's blue component, ranging between 0 and 255
     * @param a the color's alpha component, ranging between 0 (transparent) and 255 (fully opaque)
     */
    public Color(int r, int g, int b, int a) {
        this.r = clamp(r);
        this.g = clamp(g);
        this.b = clamp(b);
        this.a = clamp(a);
    }

    /**
     * Constructs a new color by copying another color and resetting the alpha value.
     *
     * @param color the color to copy
     * @param alpha the alpha value of the new color, ranging between 0 (transparent) and 255 (fully opaque)
     */
    public Color(@NotNull Color color, int alpha) {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
        this.a = clamp(alpha);
    }

    /**
     * Modulates two colors by performing a component-wise addition.
     *
     * @param a the first color
     * @param b the second color
     * @return the modulated color
     */
    public static Color add(@NotNull Color a, @NotNull Color b) {
        return new Color(a.r + b.r, a.g + b.g, a.b + b.b, a.a + b.a);
    }

    /**
     * Modulates two colors by performing a component-wise multiplication.
     *
     * @param a the first color
     * @param b the second color
     * @return the modulated color
     */
    public static Color multiply(@NotNull Color a, @NotNull Color b) {
        return new Color((a.r * b.r) / 255, (a.g * b.g) / 255, (a.b * b.b) / 255, (a.a * b.a) / 255);
    }

    /**
     * Modulates a color by multiplying its components with a factor.
     *
     * @param color the color
     * @param f     the factor
     * @return the modulated color
     */
    public static Color multiply(@NotNull Color color, float f) {
        return new Color((int) (color.r * f), (int) (color.g * f), (int) (color.b * f), (int) (color.a * f));
    }

    private static int clamp(int x) {
        return MathUtils.clamp(x, 0, 255);
    }

    @Override
    public int hashCode() {
        int result = r;
        result = 31 * result + g;
        result = 31 * result + b;
        result = 31 * result + a;
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof final Color c && c.r == r && c.g == g && c.b == b && c.a == a);
    }

    @Override
    public String toString() {
        return "Color{r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + '}';
    }
}
