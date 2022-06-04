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

/**
 * Provides window style constants. These constants can be combined using an arithmetic {@code OR} operation to define the style
 * settings for a {@code Window}. For instance, the {@code DEFAULT} style is defined as {@code TITLEBAR | RESIZE | CLOSE}.
 *
 * @see BasicWindow#create(VideoMode, String, int)
 */
public interface WindowStyle {

    /**
     * Undecorated, non-resizable window.
     */
    int NONE = 0;
    /**
     * Adds a title bar and a fixed border to the window.
     */
    int TITLEBAR = 0x01;
    /**
     * Makes the window resizable.
     */
    int RESIZE = 0x02;
    /**
     * Adds a close button to the window.
     */
    int CLOSE = 0x04;
    /**
     * Makes the window a fullscreen window.
     * If this flag is set, the other flags will be ignored.
     */
    int FULLSCREEN = 0x08;
    /**
     * The default style, a resizable and closeable window with a title bar.
     */
    int DEFAULT = TITLEBAR | RESIZE | CLOSE;
}
