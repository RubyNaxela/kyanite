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
}
