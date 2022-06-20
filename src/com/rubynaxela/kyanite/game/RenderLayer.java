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

package com.rubynaxela.kyanite.game;

import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.math.Vec2;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.window.Window;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * This class is the base of the {@link Scene} and {@link HUD} classes. Provides a layer of {@link Drawable}s that can
 * be displayed on a {@link Window}. Supports some features of a {@link List} such as adding, removing and random access.
 */
public abstract sealed class RenderLayer implements Iterable<Drawable> permits HUD, Scene {

    /**
     * The game context.
     */
    protected final GameContext context = GameContext.getInstance();
    /**
     * The list of objects on this render layer.
     */
    protected final List<Drawable> drawables = new DrawablesList();
    private final Queue<Consumer<? super RenderLayer>> scheduledActions = new LinkedList<>();
    /**
     * The background color of this render layer.
     */
    protected Color backgroundColor = Colors.BLACK;
    /**
     * The background of this render layer. If the background image is set, this overrides the background color.
     */
    protected RectangleShape background;
    /**
     * Indicates whether this render layer was initialized and is ready to be drawn on the window.
     */
    protected boolean ready = false;
    private Texture backgroundTexture;
    private OrderingPolicy orderingPolicy = OrderingPolicy.LAST_ON_TOP;

    RenderLayer() {
    }

    static void updateAnimatedTexture(@NotNull Drawable object, @NotNull Time elapsedTime) {
        if (object instanceof final SceneObject sceneObject && sceneObject.getAnimatedTexture() != null)
            sceneObject.updateAnimatedTexture(elapsedTime);
    }

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
            background.setTexture(backgroundTexture);
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
     * Returns the object at the specified position in this render layer.
     *
     * @param index the index of the object to return
     * @return the object at the specified position in this render layer
     */
    public Drawable get(int index) {
        return drawables.get(index);
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
     * Returns {@code true} if this render layer contains all the elements in the specified collection.
     *
     * @param collection a collection whose objects' presence in this render layer is to be tested
     * @return {@code true} if this list contains all the elements in the specified collection
     */
    public boolean containsAll(@NotNull Collection<Drawable> collection) {
        return new HashSet<>(drawables).containsAll(collection);
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
     * Adds all the specified elements to this render layer.
     *
     * @param objects the objects to be added to this render layer
     * @return {@code true} if this render layer changed as a result of the call
     */
    public final boolean add(@NotNull Drawable... objects) {
        return add(Arrays.asList(objects));
    }

    /**
     * Adds all the elements in the specified collection to this render layer.
     *
     * @param collection a collection containing objects to be added to this render layer
     * @return {@code true} if this render layer changed as a result of the call
     */
    public boolean add(@NotNull Collection<? extends Drawable> collection) {
        return drawables.addAll(collection);
    }

    /**
     * Schedules an object to add to this render layer. All scheduled
     * objects will be added before the nearest loop/draw iteration.
     *
     * @param object an object to add to this scene
     */
    public void scheduleToAdd(@NotNull Drawable object) {
        scheduledActions.add(layer -> layer.add(object));
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
     * Schedules an object to remove from this render layer. All scheduled
     * objects will be removed before the nearest loop/draw iteration.
     *
     * @param object an object to add to this scene
     */
    public void scheduleToRemove(@NotNull Drawable object) {
        scheduledActions.add(layer -> layer.remove(object));
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
     * Removes all the objects of this render layer that satisfy the given predicate. Errors or
     * runtime exceptions thrown during iteration or by the predicate are relayed to the caller.
     *
     * @param filter a predicate which returns {@code true} for objects to be removed
     * @return {@code true} if any objects were removed
     */
    public boolean removeIf(@NotNull Predicate<? super Drawable> filter) {
        return drawables.removeIf(filter);
    }

    /**
     * Removes all the objects from this render layer.
     */
    public void clear() {
        drawables.clear();
    }

    /**
     * Schedules a consumer action to be executed with this render layer as the parameter.
     * All scheduled actions will be executed before the nearest loop/draw iteration.
     *
     * @param action an action to be scheduled
     */
    public void schedule(@NotNull Consumer<? super RenderLayer> action) {
        scheduledActions.add(action);
    }

    /**
     * Moves the specified object to the end of the render layer collection so that
     * it is drawn last on the window and therefore above all other objects.
     *
     * @param object the object to be moved to the z-axis top of the render layer
     */
    public void bringToTop(@NotNull Drawable object) {
        if (orderingPolicy == OrderingPolicy.LAST_ON_TOP)
            if (drawables.contains(object)) {
                drawables.remove(object);
                drawables.add(object);
            } else throw new NoSuchElementException("This render layer does not contain the specified object");
        else throw new UnsupportedOperationException("Using the bringToTop method is not permitted with the LAYERS policy");
    }

    /**
     * Moves the specified object to the front of the render layer collection so that it is
     * drawn first on the window and therefore below all other objects.
     *
     * @param object the object to be moved to the z-axis bottom of the render layer
     */
    public void bringToBottom(@NotNull Drawable object) {
        if (orderingPolicy == OrderingPolicy.LAST_ON_TOP)
            if (drawables.contains(object)) {
                remove(object);
                drawables.add(0, object);
            } else throw new NoSuchElementException("This render layer does not contain the specified object");
        else throw new UnsupportedOperationException("Using the bringToBottom method is not permitted with the LAYERS policy");
    }

    /**
     * Creates an immutable list containing the elements of this render layer, in the same order as they are inside
     * the render layer. Query operations on the returned list "read through" to the specified list, and attempts to
     * modify the returned list, whether direct or via its iterator, result in an {@code UnsupportedOperationException}.
     *
     * @return an immutable list containing the elements of this render layer
     */
    public List<Drawable> asList() {
        return Collections.unmodifiableList(drawables);
    }

    /**
     * This method is called automatically before every loop/draw iteration of a render
     * layer. Calling it manually might cause a {@link ConcurrentModificationException}.
     */
    protected void runScheduledActions() {
        scheduledActions.forEach(change -> change.accept(this));
        scheduledActions.clear();
    }

    /**
     * Returns the currently set policy of ordering the elements on this render layer.
     *
     * @return currently set ordering policy
     * @see OrderingPolicy#LAST_ON_TOP
     * @see OrderingPolicy#LAYERS
     */
    public OrderingPolicy getOrderingPolicy() {
        return orderingPolicy;
    }

    /**
     * Sets the policy that should be followed when ordering the objects on the render layer and updates
     * the order. Since the original order of insertion is not stored, changing this setting from
     * {@link OrderingPolicy#LAYERS} to {@link OrderingPolicy#LAST_ON_TOP} does not change the order of objects.
     *
     * @param policy {@link OrderingPolicy#LAST_ON_TOP} or {@link OrderingPolicy#LAYERS}
     */
    public void setOrderingPolicy(@NotNull OrderingPolicy policy) {
        orderingPolicy = policy;
        updateOrder();
    }

    /**
     * Updates the order of objects on this render layer if the ordering policy is set to {@link OrderingPolicy#LAYERS}.
     */
    public void updateOrder() {
        if (orderingPolicy == OrderingPolicy.LAYERS) drawables.sort(Comparator.comparing(Drawable::getLayer));
    }

    /**
     * Policies by which the objects are ordered when added to the render layer.
     */
    public enum OrderingPolicy {

        /**
         * A simple policy with which the items on the render layer are in the order they were added. The order may be
         * changed using the {@link RenderLayer#bringToTop} and {@link RenderLayer#bringToBottom} methods. Objects with
         * a higher layer index will be placed further in the list, making them drawn over objects with lower indices.
         */
        LAST_ON_TOP,
        /**
         * A policy that follows an order determined by the layer setting (the {@link Drawable#getLayer} method). Objects with
         * a higher layer index will be placed further in the list, making them drawn over objects with lower indices. This
         * policy involves sorting the objects as new ones are added, which might cause performance issues if the render layer
         * contains many objects or is updated frequently. With this policy, manual shifting of the objects is not permitted.
         */
        LAYERS
    }

    private class DrawablesList extends LinkedList<Drawable> {

        @Override
        public boolean add(Drawable drawable) {
            super.add(drawable);
            if (orderingPolicy == OrderingPolicy.LAYERS) sort(Comparator.comparing(Drawable::getLayer));
            return true;
        }

        @Override
        public boolean addAll(Collection<? extends Drawable> c) {
            if (c.isEmpty()) return false;
            super.addAll(c);
            if (orderingPolicy == OrderingPolicy.LAYERS) sort(Comparator.comparing(Drawable::getLayer));
            return true;
        }
    }
}
