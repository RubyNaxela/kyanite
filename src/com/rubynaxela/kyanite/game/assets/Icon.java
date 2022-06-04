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

package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.system.IOException;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.graphics.Image;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * A wrapper class for JSFML {@link Image} objects, representing an icon asset.
 * An icon can be set as a {@link Window} icon. The source must be the path or an {@link InputStream}
 * to an image file. Supported formats are: BMP, DDS, JPEG, PNG, TGA and PSD.
 */
public class Icon implements Asset {

    private final Image icon;

    /**
     * Creates a new icon from the source specified by the path.
     *
     * @param path path to the data source
     * @throws IOException in case an I/O error occurs
     */
    public Icon(@NotNull Path path) {
        icon = new Image();
        icon.loadFromFile(path);
    }

    /**
     * Creates a new icon from the source specified by the path.
     *
     * @param path path to the data source
     * @throws IOException in case an I/O error occurs
     */
    public Icon(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new icon from the source image file.
     *
     * @param file the data source file
     * @throws IOException in case an I/O error occurs
     */
    public Icon(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new icon from the input stream.
     *
     * @param stream the data source input stream
     * @throws IOException in case an I/O error occurs
     */
    public Icon(@NotNull InputStream stream) {
        icon = new Image();
        icon.loadFromStream(stream);
    }

    /**
     * Applies this texture to the {@link Window}.
     *
     * @param window the {@link Window} to apply this icon to
     */
    public void apply(@NotNull Window window) {
        window.setIcon(icon);
    }
}
