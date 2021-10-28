package com.rubynaxela.kyanite.util;

import com.rubynaxela.kyanite.game.assets.AssetsBundle;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class Utils {

    /**
     * This method is designed to shorten an object initialization to one statement. For example:
     * <pre>
     * final Window window = new Window();
     * window.maximize();
     * window.setTitle("Game");
     * </pre>becomes:<pre>
     * final Window window = Utils.lambdaInit(new Window(), Window::maximize, w -> w.setTitle("Game"));
     * </pre>
     *
     * @param object      the object to initialize
     * @param initActions actions to be performed on the specified object
     * @return the specified object after initialization
     * @apiNote The usefulness of this method is questionable when any of the initialization actions may
     * throw an exception as the exception must be handled within the lambda expression of that action.
     */
    @SafeVarargs
    public static <T> T lambdaInit(@NotNull T object, @NotNull Consumer<T>... initActions) {
        Arrays.stream(initActions).forEach(a -> a.accept(object));
        return object;
    }

    /**
     * @param color a color
     * @return a color 50% darker than the specified color
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Color darker(@NotNull Color color) {
        return new Color(color.r / 2, color.g / 2, color.b / 2);
    }

    /**
     * Finds a resource file with a given path. The path must be relative to the module source root
     * package. For instance, if the file is located at {@code project_name/src/res/sound/step1.ogg},
     * then the specified path must be {@code "res/sound/step1.ogg"}.
     *
     * @param path the path to the desired file (relative to the module source root package)
     * @return an {@link java.io.InputStream} from the specified file
     * @throws IOException if no resource with this name is found, or access to the resource
     *                     is denied by the security manager or {@code path} is {@code null}
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static InputStream resourceStream(@NotNull String path) {
        try {
            return Objects.requireNonNull(AssetsBundle.class.getResourceAsStream(!path.startsWith("/") ? "/" : "" + path));
        } catch (NullPointerException e) {
            throw new IOException(e);
        }
    }
}
