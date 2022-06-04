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

import com.rubynaxela.kyanite.core.Intercom;
import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.core.SFMLNative;

import java.io.Serial;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a video mode (width, height and bits per pixel). It defines the width, height and pixel
 * depth of a window and must be supported by the monitor hardware in order to be used in fullscreen.
 *
 * @see BasicWindow#create(VideoMode, String, int)
 */
@SuppressWarnings("deprecation")
public final class VideoMode extends org.jsfml.window.VideoMode implements Serializable {

    @Serial
    private static final long serialVersionUID = 8608938390916786270L;

    private static VideoMode desktopMode = null;
    private static Set<VideoMode> fullscreenModes = null;

    static {
        SFMLNative.loadNativeLibraries();
    }

    /**
     * The width of the video mode, in pixels.
     */
    public final int width;
    /**
     * The height of the video mode, in pixels.
     */
    public final int height;
    /**
     * The pixel depth, in bits per pixel, of the video mode.
     */
    public final int bitsPerPixel;

    /**
     * Constructs a new video mode.
     *
     * @param width        the width, in pixels
     * @param height       the height, in pixels
     * @param bitsPerPixel the pixel depth, in bits per pixel
     */
    @Intercom
    public VideoMode(int width, int height, int bitsPerPixel) {
        this.width = width;
        this.height = height;
        this.bitsPerPixel = bitsPerPixel;
    }

    /**
     * Constructs a new video mode with a pixel depth of 32 bits per pixel.
     *
     * @param width  the width, in pixels
     * @param height the height, in pixels
     */
    public VideoMode(int width, int height) {
        this(width, height, 32);
    }

    /**
     * Retrieves the desktop's current video mode / screen resolution.
     *
     * @return the desktop's current video mode
     */
    public static VideoMode getDesktopMode() {
        if (desktopMode == null) {
            final IntBuffer buffer = IntercomHelper.getBuffer().asIntBuffer();
            nativeGetDesktopMode(buffer);
            desktopMode = new VideoMode(buffer.get(0), buffer.get(1), buffer.get(2));
        }
        return desktopMode;
    }

    private static Set<VideoMode> getFullscreenModeSet() {
        if (fullscreenModes == null) {
            final int num = nativeGetFullscreenModeCount();
            final IntBuffer buffer = ByteBuffer.allocateDirect(12 * num).order(ByteOrder.nativeOrder()).asIntBuffer();
            nativeGetFullscreenModes(buffer);
            fullscreenModes = new HashSet<>(num);
            for (int i = 0; i < num; i++)
                fullscreenModes.add(new VideoMode(buffer.get(3 * i), buffer.get(3 * i + 1), buffer.get(3 * i + 2)));
        }
        return fullscreenModes;
    }

    /**
     * Retrieves the list of supported fullscreen video modes.
     *
     * @return the list of supported fullscreen video modes
     */
    public static VideoMode[] getFullscreenModes() {
        return getFullscreenModeSet().toArray(new VideoMode[0]);
    }

    /**
     * Checks whether this display mode is a valid fullscreen mode, i.e. whether the current monitor supports it for fullscreen.
     *
     * @return {@code true} if this video mode is a valid fullscreen mode, {@code false} otherwise
     */
    public boolean isValid() {
        return getFullscreenModeSet().contains(this);
    }

    @Override
    public int hashCode() {
        int result = width;
        result = 31 * result + height;
        result = 31 * result + bitsPerPixel;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof final VideoMode v) return (v.width == width && v.height == height && v.bitsPerPixel == bitsPerPixel);
        else return false;
    }

    @Override
    public String toString() {
        return "VideoMode{width=" + width + ", height=" + height + ", bitsPerPixel=" + bitsPerPixel + '}';
    }
}
