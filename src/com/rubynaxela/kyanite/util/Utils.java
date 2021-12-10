package com.rubynaxela.kyanite.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsfml.graphics.Color;

import java.util.Arrays;
import java.util.function.Consumer;

/**
 * Unclassified utility funcions.
 */
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
     * @param <T>         the object class
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
     * Performs a C++ {@code dynamic_cast}-style cast of an object to the
     * class or interface represented by the specified {@code Class} object.
     *
     * @param <T>    the target class
     * @param object the object to be cast
     * @param type   the target class object
     * @return the object after casting, or null if the object is null or cannot be cast to the specified class
     */
    @Nullable
    public static <T> T cast(@NotNull Object object, @NotNull Class<T> type) {
        if (type.isInstance(object)) return type.cast(object);
        else return null;
    }

    /**
     * @param color a color
     * @return a color 50% darker than the specified color
     * @deprecated This method has been moved to the {@link Colors} class and will be removed from this class in the future.
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Color darker(@NotNull Color color) {
        return new Color(color.r / 2, color.g / 2, color.b / 2);
    }
}
