package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.game.assets.Texture;
import com.rubynaxela.kyanite.game.entities.AnimatedEntity;
import com.rubynaxela.kyanite.system.Clock;
import com.rubynaxela.kyanite.util.Vec2;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.Drawable;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.system.Time;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * Provides a scene that can be given a custom behavior and displayed on a {@link com.rubynaxela.kyanite.window.Window}.
 */
public abstract class Scene implements Iterable<Drawable> {

    private final GameContext context = GameContext.getInstance();
    private final Clock clock = context.getClock();
    private final List<Drawable> drawables = new LinkedList<>();
    private boolean ready = false;
    private Time previousFrameTime, currentFrameTime;
    private Color backgroundColor = Color.BLACK;
    private RectangleShape background;

    /**
     * This method is executed when this scene is assigned to a window.
     */
    protected abstract void init();

    /**
     * Performs the full scene initialization, i.a. starts the global clock and calls the {@link Scene#init} method.
     *
     * @param window the window that the scene has to be displayed on
     */
    public final void fullInit(@NotNull Window window) {
        if (!ready) {
            ready = true;
            clock.tryStart();
            previousFrameTime = clock.getTime();
            currentFrameTime = previousFrameTime;
            init();
        } else throw new IllegalStateException("This scene has been already initialized");
    }

    /**
     * This method is executed every frame by the window it belongs to.
     */
    protected abstract void loop();

    /**
     * Calls the {@link Scene#loop} method and then every entity is animated (if it implements {@link AnimatedEntity})
     * and drawn on the game window. This method is automatically executed every frame by the window it belongs to.
     *
     * @param window the window that the scene has to be displayed on
     */
    public final void fullLoop(@NotNull Window window) {
        loop();
        if (background != null) window.draw(background);
        forEach(object -> {
            if (object instanceof AnimatedEntity) ((AnimatedEntity) object).animate(getDeltaTime(), clock.getTime());
            window.draw(object);
        });
        previousFrameTime = currentFrameTime;
        currentFrameTime = clock.getTime();
    }

    /**
     * @return the reference to the game context
     */
    public GameContext getContext() {
        return context;
    }

    /**
     * Gets the time that has passed between the previous and the current frame.
     *
     * @return the elapsed time since the clock was created or this function was last called.
     * @apiNote Calling this method during the first frame of the scene will return time estimated
     * by the window framerate limit as there was no previous frame time to compute the difference
     */
    public Time getDeltaTime() {
        try {
            final Constructor<Time> constructor = Time.class.getDeclaredConstructor(long.class);
            return constructor.newInstance((long) (1000000.0f / GameContext.getInstance().getWindow().getFramerateLimit()));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
        }
        return Time.sub(currentFrameTime, previousFrameTime);
    }

    /**
     * @return the background color of this scene
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * Sets the background color of this scene.
     *
     * @param backgroundColor the background color of this scene
     */
    public void setBackgroundColor(@NotNull Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Sets the background texture of this scene.
     *
     * @param backgroundTexture the background texture of this scene
     * @apiNote When the window is resized, the background texture should be re-applied to fit the new size of the window.
     */
    public void setBackgroundTexture(@NotNull Texture backgroundTexture) {
        background = new RectangleShape(Vec2.f(GameContext.getInstance().getWindow().getSize()));
        backgroundTexture.apply(background);
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
     * @return the number of objects in this scene
     */
    public int size() {
        return drawables.size();
    }

    /**
     * @return {@code true} if this scene contains no objects
     */
    public boolean isEmpty() {
        return drawables.isEmpty();
    }

    /**
     * Returns {@code true} if this scene contains the specified object.
     *
     * @param object the object whose presence in this scene is to be tested
     * @return {@code true} if this list contains the specified object
     */
    public boolean contains(@NotNull Drawable object) {
        return drawables.contains(object);
    }

    /**
     * Returns {@code true} if this scene contains all of the elements in the specified collection.
     *
     * @param collection a collection whose objects' presence in this scene is to be tested
     * @return {@code true} if this list contains all of the elements in the specified collection
     */
    public boolean containsAll(@NotNull Collection<Drawable> collection) {
        return drawables.containsAll(collection);
    }

    /**
     * Adds the specified object to this scene.
     *
     * @param object the object to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(@NotNull Drawable object) {
        return drawables.add(object);
    }

    /**
     * Inserts the specified object at the specified position in this scene. Shifts the object currently
     * at that position (if any) and any subsequent objects to the right (adds one to their indices).
     *
     * @param index   the index at which the specified object is to be inserted
     * @param element object to be added
     */
    public void add(int index, @NotNull Drawable element) {
        drawables.add(index, element);
    }

    /**
     * Adds all of the specified elements to this scene.
     *
     * @param objects the objects to be added to this scene
     * @return {@code true} if this scene changed as a result of the call
     */
    public final boolean add(@NotNull Drawable... objects) {
        return add(Arrays.asList(objects));
    }

    /**
     * Adds all of the elements in the specified collection to this scene.
     *
     * @param collection a collection containing objects to be added to this scene
     * @return {@code true} if this scene changed as a result of the call
     */
    public boolean add(@NotNull Collection<? extends Drawable> collection) {
        return drawables.addAll(collection);
    }

    /**
     * Inserts all of the objects in the specified collection into this scene at the specified position (optional operation).
     * Shifts the object currently at that position (if any) and any subsequent objects to the right (increases their indices).
     *
     * @param index      the index at which to insert the first object from the specified collection
     * @param collection a collection containing objects to be added to this scene
     * @return {@code true} if this scene changed as a result of the call
     */
    public boolean add(int index, @NotNull Collection<? extends Drawable> collection) {
        return drawables.addAll(index, collection);
    }

    /**
     * Removes the first occurrence of the specified element from this scene, if it is present.
     *
     * @param object the object to be removed from this scene, if present
     * @return {@code true} if this scene contained the specified object
     */
    public boolean remove(@NotNull Drawable object) {
        return drawables.remove(object);
    }

    /**
     * Removes the object at the specified position in this scene. Shifts any
     * subsequent objects to the left (subtracts one from their indices).
     *
     * @param index the index of the object to be removed
     * @return {@code true} if the object that was removed from the list
     */
    public Drawable remove(int index) {
        return drawables.remove(index);
    }

    /**
     * Removes from this scene all of its objects that are contained in the specified collection.
     *
     * @param collection a collection containing objects to be removed from this scene
     * @return {@code true} if this scene changed as a result of the call
     */
    public boolean removeAll(@NotNull Collection<Drawable> collection) {
        return drawables.removeAll(collection);
    }

    /**
     * Removes all of the objects of this scene that satisfy the given predicate. Errors or
     * runtime exceptions thrown during iteration or by the predicate are relayed to the caller.
     *
     * @param filter a predicate which returns {@code true} for objects to be removed
     * @return {@code true} if any objects were removed
     */
    public boolean removeIf(@NotNull Predicate<? super Drawable> filter) {
        return drawables.removeIf(filter);
    }

    /**
     * Removes all of the objects from this scene.
     */
    public void clear() {
        drawables.clear();
    }

    /**
     * Returns the object at the specified position in this scene.
     *
     * @param index the index of the object to return
     * @return the object at the specified position in this scene
     */
    public Drawable get(int index) {
        return drawables.get(index);
    }

    /**
     * Replaces the object at the specified position in this scene with the specified object.
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
     * Replaces each object of this scene with the result of applying the operator to that
     * object. Errors or runtime exceptions thrown by the operator are relayed to the caller.
     *
     * @param operator the operator to apply to each object
     */
    public void replaceAll(@NotNull UnaryOperator<Drawable> operator) {
        drawables.replaceAll(operator);
    }

    /**
     * Returns the index of the first occurrence of the specified element in this scene.
     *
     * @param object the object to search for
     * @return the index of the first occurrence of the specified object
     * in this scene, or -1 if this list does not contain the element
     */
    public int indexOf(@NotNull Drawable object) {
        return drawables.indexOf(object);
    }

    /**
     * Returns the index of the last occurrence of the specified element in this scene.
     *
     * @param object the object to search for
     * @return the index of the first occurrence of the specified object
     * in this scene, or -1 if this list does not contain the element
     */
    public int lastIndexOf(@NotNull Drawable object) {
        return drawables.lastIndexOf(object);
    }

    /**
     * Moves the specified object to the end of the scene collection so that
     * it is drawn last on the window and therefore above all other objects.
     *
     * @param object the object to be moved to the z-axis top of the scene
     */
    public void bringToTop(@NotNull Drawable object) {
        if (contains(object)) {
            remove(object);
            add(object);
        } else throw new NullPointerException("This scene does not contain the specified object");
    }

    /**
     * Moves the specified object to the front of the scene collection so that it is
     * drawn first on the window and therefore below all other objects.
     *
     * @param object the object to be moved to the z-axis bottom of the scene
     */
    public void bringToBottom(@NotNull Drawable object) {
        if (contains(object)) {
            remove(object);
            add(0, object);
        } else throw new NullPointerException("This scene does not contain the specified object");
    }
}
