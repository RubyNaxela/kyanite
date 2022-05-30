package com.rubynaxela.kyanite.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.IntRect;
import com.rubynaxela.kyanite.core.system.Time;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
     * Converts an {@link IntRect} to a {@link FloatRect}
     *
     * @param rect an {@link IntRect} object
     * @return a {@link FloatRect} object with the same values as the parameter
     */
    @NotNull
    public static FloatRect toFloatRect(@NotNull IntRect rect) {
        return new FloatRect(rect.left, rect.top, rect.width, rect.height);
    }

    /**
     * Converts a {@link FloatRect} to an {@link IntRect}, rounding the values down to the nearest integer
     *
     * @param rect a {@link FloatRect} object
     * @return an {@link IntRect} object with the same values as the parameter, rounded down
     */
    @NotNull
    public static IntRect toIntRect(@NotNull FloatRect rect) {
        return new IntRect((int) rect.left, (int) rect.top, (int) rect.width, (int) rect.height);
    }

    /**
     * Calls the {@code Time(long)} constructor of the {@link Time} class.
     *
     * @param microseconds the duration in microseconds
     * @return a {@link Time} object with the specified duration
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Time timeOf(long microseconds) {
        try {
            final Constructor<Time> constructor = Time.class.getDeclaredConstructor(long.class);
            constructor.setAccessible(true);
            return constructor.newInstance(microseconds);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return Time.ZERO;
    }

    /**
     * Flattens a 2D array into a 1D array. For instance, {@code [[1, 2], [3, 4], [5, 6]]} becomes {@code [1, 2, 3, 4, 5, 6]}.
     *
     * @param array2D a 2D array
     * @param type    the array elements type
     * @param <T>     the array elements type
     * @return an array of all values from the specified 2D array
     */
    @SuppressWarnings("unchecked")
    @Contract(pure = true)
    public static <T> T[] flatten2DArray(@NotNull Class<T> type, @NotNull T[][] array2D) {
        final int size = array2D.length * array2D[0].length;
        List<T> list = new ArrayList<>(array2D.length * array2D[0].length);
        for (T[] array : array2D) Collections.addAll(list, array);
        return list.toArray((T[]) Array.newInstance(type, size));
    }
}
