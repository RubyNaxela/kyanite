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

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.math.MathUtils;
import org.jetbrains.annotations.Contract;
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
    public final short r;
    /**
     * The green component of the color, ranging between 0 and 255.
     */
    public final short g;
    /**
     * The blue component of the color, ranging between 0 and 255.
     */
    public final short b;
    /**
     * The alpha component of the color, ranging between 0 (transparent) and 255 (fully opaque).
     */
    public final short a;

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

    private static short clamp(int x) {
        return MathUtils.clamp(x, 0, 255).shortValue();
    }

    /**
     * Creates a new color by copying this color and resetting the alpha value.
     *
     * @param alpha the alpha value of the new color, ranging between 0 (fully transparent) and 255 (fully opaque)
     */
    @Contract(pure = true, value = "_ -> new")
    public Color withAlpha(int alpha) {
        return new Color(r, g, b, alpha);
    }

    /**
     * Creates a new color by copying this color and resetting the alpha value.
     *
     * @param opacity the color's opacity value ranging between {@code 0.0f} (fully transparent) and {@code 1.0f} (fully opaque)
     */
    @Contract(pure = true, value = "_ -> new")
    public Color withOpacity(float opacity) {
        return new Color(r, g, b, (int) (opacity * 255));
    }

    /**
     * Creates a color darker than this color. Each component {@code c} of the resulting
     * color (except alpha, which remains the same) is decreased by {@code factor * c}.
     *
     * @param factor darkening factor ranging between {@code 0.0f} (color unchanged) and {@code 1.0f} (color completely black)
     * @return the resulting darkened color
     */
    @Contract(pure = true, value = "_ -> new")
    public Color darker(float factor) {
        final float f = 1 - factor;
        return new Color((int) (r * f), (int) (g * f), (int) (b * f), a);
    }

    /**
     * Returns a color brighter than this color. Each component {@code c} of the resulting
     * color (except alpha, which remains the same) is increased by {@code factor * (255 - c)}.
     *
     * @param factor lightening factor ranging between {@code 0.0f} (color unchanged) and {@code 1.0f} (color completely white)
     * @return the resulting lightened color
     */
    @Contract(pure = true, value = "_ -> new")
    public Color brighter(float factor) {
        return new Color((int) (r + factor * (255 - r)), (int) (g + factor * (255 - g)), (int) (b + factor * (255 - b)), a);
    }

    @Override
    public int hashCode() {
        return IntercomHelper.encodeColor(this);
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
