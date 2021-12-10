package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RectangleShape;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 * Provides a layer of {@link Drawable}s that can be displayed on a {@link Window}. The collection index of
 * an element is also its z-index. If two elements overlap each other, the one with the higher index will
 * be displayed over the other one. This class is the base of the {@link Scene} and {@link HUD} classes.
 */
abstract class RenderLayer implements Iterable<Drawable> {

    /**
     * The game context.
     */
    protected final GameContext context = GameContext.getInstance();
    /**
     * The list of objects on this render layer.
     */
    protected final List<Drawable> drawables = new LinkedList<>();
    /**
     * The background color of this render layer.
     */
    protected Color backgroundColor = Color.BLACK;
    /**
     * The background of this render layer. If the background image is set, this overrides the background color.
     */
    protected RectangleShape background;
    /**
     * Indicates whether this render layer was initialized and is ready to be drawn on the window.
     */
    protected boolean ready = false;
    private Texture backgroundTexture;

    /**
     * This method is executed when this render layer is assigned to a window.
     */
    protected abstract void init();

    /**
     * @return the reference to the game context
     */
    public GameContext getContext() {
        return context;
    }

    /**
     * @return the background color of this render layer
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the background color of this render layer.
     *
     * @param backgroundColor the background color of this render layer
     */
    public void setBackgroundColor(@NotNull Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Sets the background texture of this render layer.
     *
     * @param backgroundTexture the background texture of this render layer
     */
    public void setBackgroundTexture(@NotNull Texture backgroundTexture) {
        this.backgroundTexture = backgroundTexture;
        refreshBackgroundTexture();
    }

    /**
     * Re-applies the render layer background image. This method is called
     * automatically when the window is resized to also resize the background image.
     */
    public void refreshBackgroundTexture() {
        if (backgroundTexture != null) {
            background = new RectangleShape(Vec2.f(GameContext.getInstance().getWindow().getSize()));
            backgroundTexture.apply(background);
        }
    }

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     * The returned iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    @Override
    @NotNull
    public Iterator<Drawable> iterator() {
        return drawables.iterator();
    }

    /**
     * Performs the given action for each object of the {@code Iterable} until all elements have been
     * processed or the action throws an exception. Actions are performed in the order of iteration,
     * if that order is specified. Exceptions thrown by the action are relayed to the caller.
     *
     * @param action the action to be performed for each object
     */
    @Override
    public void forEach(@NotNull Consumer<? super Drawable> action) {
        drawables.forEach(action);
    }

    /**
     * Creates a sequential {@code Stream} with the collection of objects of this render layer as its source.
     *
     * @return a sequential {@code Stream} over this render layer's objects
     */
    @NotNull
    public Stream<Drawable> stream() {
        return drawables.stream();
    }

    /**
     * @return the number of objects in this render layer
     */
    public int size() {
        return drawables.size();
    }

    /**
     * @return {@code true} if this render layer contains no objects
     */
    public boolean isEmpty() {
        return drawables.isEmpty();
    }

    /**
     * Returns {@code true} if this render layer contains the specified object.
     *
     * @param object the object whose presence in this render layer is to be tested
     * @return {@code true} if this list contains the specified object
     */
    public boolean contains(@NotNull Drawable object) {
        return drawables.contains(object);
    }

    /**
     * Returns {@code true} if this render layer contains all of the elements in the specified collection.
     *
     * @param collection a collection whose objects' presence in this render layer is to be tested
     * @return {@code true} if this list contains all of the elements in the specified collection
     */
    public boolean containsAll(@NotNull Collection<Drawable> collection) {
        return drawables.containsAll(collection);
    }

    /**
     * Adds the specified object to this render layer.
     *
     * @param object the object to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(@NotNull Drawable object) {
        return drawables.add(object);
    }

    /**
     * Inserts the specified object at the specified position in this render layer. Shifts the object currently
     * at that position (if any) and any subsequent objects to the right (adds one to their indices).
     *
     * @param index   the index at which the specified object is to be inserted
     * @param element object to be added
     */
    public void add(int index, @NotNull Drawable element) {
        drawables.add(index, element);
    }

    /**
     * Adds all of the specified elements to this render layer.
     *
     * @param objects the objects to be added to this render layer
     * @return {@code true} if this render layer changed as a result of the call
     */
    public final boolean add(@NotNull Drawable... objects) {
        return add(Arrays.asList(objects));
    }

    /**
     * Adds all of the elements in the specified collection to this render layer.
     *
     * @param collection a collection containing objects to be added to this render layer
     * @return {@code true} if this render layer changed as a result of the call
     */
    public boolean add(@NotNull Collection<? extends Drawable> collection) {
        return drawables.addAll(collection);
    }

    /**
     * Inserts all of the objects in the specified collection into this render layer at the specified position (optional
     * operation).
     * Shifts the object currently at that position (if any) and any subsequent objects to the right (increases their indices).
     *
     * @param index      the index at which to insert the first object from the specified collection
     * @param collection a collection containing objects to be added to this render layer
     * @return {@code true} if this render layer changed as a result of the call
     */
    public boolean add(int index, @NotNull Collection<? extends Drawable> collection) {
        return drawables.addAll(index, collection);
    }

    /**
     * Removes the first occurrence of the specified element from this render layer, if it is present.
     *
     * @param object the object to be removed from this render layer, if present
     * @return {@code true} if this render layer contained the specified object
     */
    public boolean remove(@NotNull Drawable object) {
        return drawables.remove(object);
    }

    /**
     * Removes the object at the specified position in this render layer. Shifts any
     * subsequent objects to the left (subtracts one from their indices).
     *
     * @param index the index of the object to be removed
     * @return {@code true} if the object that was removed from the list
     */
    public Drawable remove(int index) {
        return drawables.remove(index);
    }

    /**
     * Removes from this render layer all of its objects that are contained in the specified collection.
     *
     * @param collection a collection containing objects to be removed from this render layer
     * @return {@code true} if this render layer changed as a result of the call
     */
    public boolean removeAll(@NotNull Collection<Drawable> collection) {
        return drawables.removeAll(collection);
    }

    /**
     * Removes all of the objects of this render layer that satisfy the given predicate. Errors or
     * runtime exceptions thrown during iteration or by the predicate are relayed to the caller.
     *
     * @param filter a predicate which returns {@code true} for objects to be removed
     * @return {@code true} if any objects were removed
     */
    public boolean removeIf(@NotNull Predicate<? super Drawable> filter) {
        return drawables.removeIf(filter);
    }

    /**
     * Removes all of the objects from this render layer.
     */
    public void clear() {
        drawables.clear();
    }

    /**
     * Returns the object at the specified position in this render layer.
     *
     * @param index the index of the object to return
     * @return the object at the specified position in this render layer
     */
    public Drawable get(int index) {
        return drawables.get(index);
    }

    /**
     * Replaces the object at the specified position in this render layer with the specified object.
     *
     * @param index   the index of the object to replace
     * @param element the object to be stored at the specified position
     * @return the object previously at the specified position
     */
    public Drawable set(int index, @NotNull Drawable element) {
        return drawables.set(index, element);
    }

    /**
     * Replaces the first of the specified objects with the second one.
     *
     * @param previous the object to be replaced
     * @param current  the object to replace the original one with
     */
    public void replace(@NotNull Drawable previous, @NotNull Drawable current) {
        final int index = indexOf(previous);
        if (index >= 0) {
            remove(index);
            add(index, current);
        }
    }

    /**
     * Replaces each object of this render layer with the result of applying the operator to that
     * object. Errors or runtime exceptions thrown by the operator are relayed to the caller.
     *
     * @param operator the operator to apply to each object
     */
    public void replaceAll(@NotNull UnaryOperator<Drawable> operator) {
        drawables.replaceAll(operator);
    }

    /**
     * Returns the index of the first occurrence of the specified element in this render layer.
     *
     * @param object the object to search for
     * @return the index of the first occurrence of the specified object
     * in this render layer, or -1 if this list does not contain the element
     */
    public int indexOf(@NotNull Drawable object) {
        return drawables.indexOf(object);
    }

    /**
     * Returns the index of the last occurrence of the specified element in this render layer.
     *
     * @param object the object to search for
     * @return the index of the first occurrence of the specified object
     * in this render layer, or -1 if this list does not contain the element
     */
    public int lastIndexOf(@NotNull Drawable object) {
        return drawables.lastIndexOf(object);
    }

    /**
     * Moves the specified object to the end of the render layer collection so that
     * it is drawn last on the window and therefore above all other objects.
     *
     * @param object the object to be moved to the z-axis top of the render layer
     */
    public void bringToTop(@NotNull Drawable object) {
        if (contains(object)) {
            remove(object);
            add(object);
        } else throw new NullPointerException("This render layer does not contain the specified object");
    }

    /**
     * Moves the specified object to the front of the render layer collection so that it is
     * drawn first on the window and therefore below all other objects.
     *
     * @param object the object to be moved to the z-axis bottom of the render layer
     */
    public void bringToBottom(@NotNull Drawable object) {
        if (contains(object)) {
            remove(object);
            add(0, object);
        } else throw new NullPointerException("This render layer does not contain the specified object");
    }

    /**
     * Creates an immutable list containing the elements of this render layer, in the same order as they are inside the render
     * layer. Query operations on the returned list "read through" to the specified list, and attempts to modify the returned
     * list, whether direct or via its iterator, result in an {@code UnsupportedOperationException}.
     *
     * @return an immutable list containing the elements of this render layer
     */
    public List<Drawable> asList() {
        return Collections.unmodifiableList(drawables);
    }
}
