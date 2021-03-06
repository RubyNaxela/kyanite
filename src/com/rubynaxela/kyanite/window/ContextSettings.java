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

package com.rubynaxela.kyanite.window;

import java.io.Serial;
import java.io.Serializable;

/**
 * Holds the settings for an OpenGL context attached to a window. Unless you need anti-aliasing for primitives or require
 * your window to hold an OpenGL context for a specific OpenGL version (e.g. for custom OpenGL rendering), you will most
 * likely not need this. All settings provided by this class are handled as <i>hints</i> to the created OpenGL context. If a
 * feature is not supported by the hardware, the respective setting will be ignored and a supported default is used instead.
 */
public final class ContextSettings implements Serializable {

    @Serial
    private static final long serialVersionUID = -3658200233541780345L;

    /**
     * The amount of depth buffer bits.
     */
    public final int depthBits;
    /**
     * The amount of stencil buffer bits.
     */
    public final int stencilBits;
    /**
     * The level of anti-aliasing.
     */
    public final int antialiasingLevel;
    /**
     * The desired major OpenGL version number.
     */
    public final int majorVersion;
    /**
     * The desired minor OpenGL version number.
     */
    public final int minorVersion;

    /**
     * Constructs new context settings with default values (OpenGL 2.0, no depth or stencil bits, no anti-aliasing).
     */
    public ContextSettings() {
        this(0, 0, 0, 2, 0);
    }

    /**
     * Constructs new context settings with the specified level of anti-aliasing and OpenGL 2.0, no depth or stencil bits.
     *
     * @param antialiasingLevel the level of anti-aliasing
     */
    public ContextSettings(int antialiasingLevel) {
        this(0, 0, antialiasingLevel, 2, 0);
    }

    /**
     * Constructs new context settings with the specified OpenGL version, no depth or stencil bits and no anti-aliasing.
     *
     * @param majorVersion the desired major OpenGL version number
     * @param minorVersion the desired minor OpenGL version number
     */
    public ContextSettings(int majorVersion, int minorVersion) {
        this(0, 0, 0, majorVersion, minorVersion);
    }

    /**
     * Constructs new context settings with the specified OpenGL version and anti-aliasing level and no depth or stencil bits.
     *
     * @param antialiasingLevel the level of anti-aliasing
     * @param majorVersion      the desired major OpenGL version number
     * @param minorVersion      the desired minor OpenGL version number
     */
    public ContextSettings(int antialiasingLevel, int majorVersion, int minorVersion) {
        this(0, 0, antialiasingLevel, majorVersion, minorVersion);
    }

    /**
     * Constructs new context settings.
     *
     * @param depthBits         the amount of depth buffer bits
     * @param stencilBits       the amount of stencil buffer bits
     * @param antialiasingLevel the level of anti-aliasing
     * @param majorVersion      the desired major OpenGL version number
     * @param minorVersion      the desired minor OpenGL version number
     */
    public ContextSettings(int depthBits, int stencilBits, int antialiasingLevel, int majorVersion, int minorVersion) {
        this.depthBits = depthBits;
        this.stencilBits = stencilBits;
        this.antialiasingLevel = antialiasingLevel;
        this.majorVersion = majorVersion;
        this.minorVersion = minorVersion;
    }

    @Override
    public int hashCode() {
        int result = depthBits;
        result = 31 * result + stencilBits;
        result = 31 * result + antialiasingLevel;
        result = 31 * result + majorVersion;
        result = 31 * result + minorVersion;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof final ContextSettings c)
            return c.antialiasingLevel == antialiasingLevel && c.depthBits == depthBits &&
                   c.majorVersion == majorVersion && c.minorVersion == minorVersion && c.stencilBits == stencilBits;
        else return false;
    }

    @Override
    public String toString() {
        return "ContextSettings{depthBits=" + depthBits + ", stencilBits=" + stencilBits + ", antialiasingLevel=" +
               antialiasingLevel + ", majorVersion=" + majorVersion + ", minorVersion=" + minorVersion + '}';
    }
}
