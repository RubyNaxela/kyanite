package com.rubynaxela.kyanite.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.IntRect;
import com.rubynaxela.kyanite.core.system.Vector2i;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * An {@link Iterable} of integer 2D coordinates. Useful for iterating over images or coordinate-based scenes. Example usage:
 * <pre>Vec2iIterable.rectangle(1, 1, 16, 3).forEach((x, y) -> world.spawn(new Knight(), x, y));</pre>
 * The behavior of the above code will be the same as the following:<pre>
 * for (int i = 1; i &lt; 17; i++) {
 *     for (int j = 1; j &lt; 4; j++) {
 *         world.spawn(new Knight(), i, j);
 *     }
 * }</pre>
 * Note that for a non-positive rows or columns number, calling any
 * terminal operations on this object or its iterator will have no effect.
 */
public class Vec2iIterable implements Iterable<Vector2i> {

    private final int xOffset, yOffset, rows, columns;
    private ArrayList<Vector2i> points;

    private Vec2iIterable(int xOffset, int yOffset, int rows, int columns) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.rows = rows;
        this.columns = columns;
    }

    private Vec2iIterable(int rows, int columns) {
        this(0, 0, rows, columns);
    }

    /**
     * Creates a new square {@code Vec2iIterable} with the specified
     * size. The iteration range is {@code [0;size-1]} on both axes.
     *
     * @param size the number of rows and columns in this {@code Vec2iIterable}
     * @return a square {@code Vec2iIterable} with the specified size
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vec2iIterable square(int size) {
        return new Vec2iIterable(size, size);
    }

    /**
     * Creates a new square {@code Vec2iIterable} with the specified offset and
     * size. The iteration range is {@code [offset;size+offset-1]} on both axes.
     *
     * @param offset the shift of the points on both axes
     * @param size   the number of rows and columns in this {@code Vec2iIterable}
     * @return a square {@code Vec2iIterable} with the specified size and offset
     */
    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vec2iIterable square(int offset, int size) {
        return new Vec2iIterable(offset, offset, size, size);
    }

    /**
     * Creates a new rectangular {@code Vec2iIterable} with the specified size. The iteration
     * range is {@code [0;rows-1]} on the X axis and {@code [0;columns-1]} on the Y axis.
     *
     * @param rows    the number of rows in this {@code Vec2iIterable}
     * @param columns the number of columns in this {@code Vec2iIterable}
     * @return a rectangle {@code Vec2iIterable} with the specified size
     */
    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vec2iIterable rectangle(int rows, int columns) {
        return new Vec2iIterable(rows, columns);
    }

    /**
     * Creates a new rectangular {@code Vec2iIterable} with the specified offset and size. The iteration range
     * is {@code [xOffset;rows+xOffset-1]} on the X axis and {@code [yOffset;columns+yOffset-1]} on the Y axis.
     *
     * @param xOffset the shift of the points on the X axis
     * @param yOffset the shift of the points on the Y axis
     * @param rows    the number of rows in this {@code Vec2iIterable}
     * @param columns the number of columns in this {@code Vec2iIterable}
     * @return a rectangle {@code Vec2iIterable} with the specified size and offset
     */
    @NotNull
    @Contract(pure = true, value = "_, _, _, _ -> new")
    public static Vec2iIterable rectangle(int xOffset, int yOffset, int rows, int columns) {
        return new Vec2iIterable(xOffset, yOffset, rows, columns);
    }

    /**
     * Creates a new rectangular {@code Vec2iIterable} with the specified size. The iteration
     * range is {@code [0;size.x-1]} on the X axis and {@code [0;size.y-1]} on the Y axis.
     *
     * @param size the size of this {@code Vec2iIterable}
     * @return a rectangle {@code Vec2iIterable} with the specified size
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vec2iIterable rectangle(@NotNull Vector2i size) {
        return new Vec2iIterable(size.x, size.y);
    }

    /**
     * Creates a new rectangular {@code Vec2iIterable} with the specified size. The iteration range is
     * {@code [offset.x;size.x+offset.x-1]} on the X axis and {@code [offset.y;size.y+offset.y-1]} on the Y axis.
     *
     * @param offset the shift of the points on both axes
     * @param size   the size of this {@code Vec2iIterable}
     * @return a rectangle {@code Vec2iIterable} with the specified size and offset
     */
    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    public static Vec2iIterable rectangle(@NotNull Vector2i offset, @NotNull Vector2i size) {
        return new Vec2iIterable(offset.x, offset.y, size.x, size.y);
    }

    /**
     * Creates a new rectangular {@code Vec2iIterable} for the specified {@code IntRect}. The iteration range is
     * {@code [rect.left;rect.width+rect.left-1]} on the X axis and {@code [rect.top;rect.height+rect.top-1]} on the Y axis.
     *
     * @param rect the {@code IntRect} to iterate over
     * @return a rectangle {@code Vec2iIterable} over the specified rectangle
     */
    @NotNull
    @Contract(pure = true, value = "_ -> new")
    public static Vec2iIterable rectangle(@NotNull IntRect rect) {
        return new Vec2iIterable(rect.left, rect.top, rect.width, rect.height);
    }

    /**
     * Returns a closed range version of the {@code Vec2iIterable} on which this method is
     * called. For instance, if the iteration range is {@code [0;9]} on both axes, this method
     * will return a {@code Vec2iIterable} with iteration range of {@code [0;10]} on both axes.
     *
     * @return a closed range version of this {@code Vec2iIterable}
     */
    @NotNull
    @Contract(pure = true, value = "-> new")
    public Vec2iIterable closed() {
        return new Vec2iIterable(xOffset, yOffset, rows + 1, columns + 1);
    }

    /**
     * Returns an iterator over the coordinates in this {@code Vec2iIterable} left-to-right, then top-to-bottom.
     *
     * <p>The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @return an iterator over the coordinates in this {@code Vec2iIterable} left-to-right, then top-to-bottom
     */
    @NotNull
    @Override
    public Iterator<Vector2i> iterator() {
        createList();
        return points.iterator();
    }

    /**
     * Performs the specified action for every point in this {@code Vec2iIterable}.
     *
     * @param action the action to be performed for each point
     */
    @Override
    public void forEach(@NotNull Consumer<? super Vector2i> action) {
        for (int x = xOffset; x < rows + xOffset; x++)
            for (int y = yOffset; y < columns + yOffset; y++)
                action.accept(Vec2.i(x, y));
    }

    /**
     * Creates a <em><a href="Spliterator.html#binding">late-binding</a></em> and <em>fail-fast</em>
     * {@link Spliterator} over the elements in this {@code Vec2iIterable}. The {@code Spliterator}
     * reports {@link Spliterator#SIZED}, {@link Spliterator#SUBSIZED}, and {@link Spliterator#ORDERED}.
     *
     * @return a {@code Spliterator} over the coordinates in this {@code Vec2iIterable}
     */
    @Override
    public Spliterator<Vector2i> spliterator() {
        createList();
        return points.spliterator();
    }

    /**
     * Performs the specified action for every point in this {@code Vec2iIterable}.
     *
     * @param action the action to be performed for each point
     */
    public void forEach(@NotNull BiConsumer<Integer, Integer> action) {
        for (int x = xOffset; x < rows + xOffset; x++)
            for (int y = yOffset; y < columns + yOffset; y++)
                action.accept(x, y);
    }

    /**
     * @return a sequential {@link Stream} from this iterable
     */
    public Stream<Vector2i> stream() {
        return points.stream();
    }

    private void createList() {
        if (points == null) {
            points = new ArrayList<>();
            for (int x = xOffset; x < rows + xOffset; x++)
                for (int y = yOffset; y < columns + yOffset; y++)
                    points.add(Vec2.i(x, y));
        }
    }
}
