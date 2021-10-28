package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.system.IOException;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Image;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Icon implements Asset {

    private final org.jsfml.graphics.Image icon;

    /**
     * Creates a new icon from the source specified by the path.
     *
     * @param path path to the data source
     */
    public Icon(@NotNull Path path) {
        icon = new Image();
        try {
            icon.loadFromFile(path);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Creates a new icon from the source specified by the path.
     *
     * @param path path to the data source
     */
    public Icon(@NotNull String path) {
        this(Paths.get(path));
    }

    /**
     * Creates a new icon from the source image file.
     *
     * @param file the data source file
     */
    public Icon(@NotNull File file) {
        this(file.toPath());
    }

    /**
     * Creates a new icon from the input stream.
     *
     * @param stream the data source input stream
     */
    public Icon(@NotNull InputStream stream) {
        icon = new Image();
        try {
            icon.loadFromStream(stream);
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
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
