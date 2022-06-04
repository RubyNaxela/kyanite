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

package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

/**
 * Defines a drawable set of one or multiple 2D primitives.
 */
public class VertexArray extends BasicTransformable implements List<Vertex>, Serializable, Drawable, BoundsObject {

    @Serial
    private static final long serialVersionUID = 4656221909265000727L;

    private final List<Vertex> vertices;
    private PrimitiveType primitiveType;
    private Color fillColor = Colors.WHITE;

    /**
     * Constructs a new empty vertex array using the {@link PrimitiveType#POINTS} type.
     */
    public VertexArray() {
        this(PrimitiveType.POINTS);
    }

    /**
     * Constructs a new empty vertex array.
     *
     * @param primitiveType the type of primitives drawn by this vertex array
     */
    public VertexArray(@NotNull PrimitiveType primitiveType) {
        vertices = new ArrayList<>(4);
        this.primitiveType = primitiveType;
    }

    /**
     * Constructs a vertex array containing the specified vertices.
     *
     * @param vertices      the vertices to be contained in this vertex array
     * @param primitiveType the type of primitives drawn by this vertex array
     */
    public VertexArray(@NotNull List<Vertex> vertices, @NotNull PrimitiveType primitiveType) {
        this.vertices = new ArrayList<>(vertices.size());
        this.vertices.addAll(vertices);
        this.primitiveType = primitiveType;
    }

    private VertexArray(@NotNull PrimitiveType primitiveType, @NotNull List<Vertex> vertices) {
        this.vertices = vertices;
        this.primitiveType = primitiveType;
    }

    /**
     * Gets the type of primitives drawn by this vertex array.
     *
     * @return the type of primitives drawn by this vertex array
     */
    public PrimitiveType getPrimitiveType() {
        return primitiveType;
    }

    /**
     * Sets the type of primitives drawn by this vertex array.
     *
     * @param primitiveType the type of primitives drawn by this vertex array
     */
    public void setPrimitiveType(PrimitiveType primitiveType) {
        this.primitiveType = primitiveType;
    }

    /**
     * Computes the axis-aligned bounding box of this vertex array, <i>not</i> taking its transformation into account.
     *
     * @return the axis-aligned bounding box of this vertex array
     */
    @Override
    public FloatRect getLocalBounds() {
        if (!vertices.isEmpty()) {
            Vector2f v = vertices.get(0).position;
            float left = v.x, top = v.y, right = v.x, bottom = v.y;
            for (int i = 1; i < vertices.size(); i++) {
                v = vertices.get(i).position;
                if (v.x < left) left = v.x;
                else if (v.x > right) right = v.x;
                if (v.y < top) top = v.y;
                else if (v.y > bottom) bottom = v.y;
            }
            return new FloatRect(left, top, right - left, bottom - top);
        } else return FloatRect.EMPTY;
    }

    /**
     * Computes the axis-aligned bounding box of this vertex array, taking its transformation into account.
     *
     * @return the axis-aligned bounding box of this vertex array
     */
    @Override
    public FloatRect getGlobalBounds() {
        return getTransform().transformRect(getLocalBounds());
    }

    /**
     * Gets the vertex array's current fill color. This method does not take color changes made manually to the
     * individual vertices into account, but rather only returns the color set by the {@link #setFillColor} method.
     *
     * @return the vertex array's current fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill color of the vertex array by applying the color to every vertex. The default color is {@link Colors#WHITE}.
     *
     * @param color the new fill color of the shape, or {@link Colors#TRANSPARENT}
     *              to indicate that the shape should not be filled
     */
    public void setFillColor(@NotNull Color color) {
        fillColor = color;
        vertices.forEach(v -> v.color = color);
    }

    /**
     * Draws this vertex array to a render target.
     *
     * @param target the target to draw this vertex array on
     * @param states the current render states
     */
    @Override
    public void draw(@NotNull RenderTarget target, @NotNull RenderStates states) {
        final Transform transform = Transform.combine(states.transform, getTransform());
        final RenderStates renderStates = new RenderStates(states.blendMode, transform, states.texture, states.shader);
        if (!vertices.isEmpty()) target.draw(vertices.toArray(new Vertex[0]), primitiveType, renderStates);
    }

    /**
     * Returns the number of vertices in this {@code VertexArray}.
     *
     * @return the number of vertices in this {@code VertexArray}
     */
    @Override
    public int size() {
        return vertices.size();
    }

    /**
     * Returns {@code true} if this {@code VertexArray} contains no vertices.
     *
     * @return {@code true} if this {@code VertexArray} contains no vertices
     */
    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    /**
     * Returns {@code true} if this {@code VertexArray} contains the specified vertex. More formally, returns {@code true} if
     * and only if this {@code VertexArray} contains at least one vertex {@code v} such that {@code Objects.equals(vertex, v)}.
     *
     * @param vertex vertex whose presence in this {@code VertexArray} is to be tested
     * @return {@code true} if this {@code VertexArray} contains the specified vertex, {@code false} otherwise
     */
    @Override
    public boolean contains(Object vertex) {
        return vertices.contains(vertex);
    }

    @NotNull
    @Override
    public Iterator<Vertex> iterator() {
        return vertices.iterator();
    }

    /**
     * Returns an array containing all of the elements in this {@code VertexArray} in proper sequence (from
     * first to last vertex). The returned array will be "safe" in that no references to it are maintained by
     * this {@code VertexArray}. (In other words, this method must allocate a new array). The caller is thus free
     * to modify the returned array. This method acts as bridge between array-based and collection-based APIs.
     *
     * @return an array containing all of the elements in this list in proper sequence
     */
    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        return vertices.toArray();
    }

    /**
     * Returns an array containing all of the elements in this {@code VertexArray} in proper sequence (from first to
     * last vertex); the runtime type of the returned array is that of the specified array. If the {@code VertexArray}
     * fits in the specified array, it is returned therein. Otherwise, a new array is  allocated with the runtime type
     * of the specified array and the size of this {@code VertexArray}. If the {@code VertexArray} fits in the specified
     * array with room to spare (i.e. the array has more elements than the {@code VertexArray}), the vertex in the
     * array immediately following the end of the collection is set to {@code null}. (This is useful in determining
     * the length of the list <i>only</i> if the caller knows that the list does not contain any null elements.)
     *
     * @param a the array into which the vertices of the {@code VertexArray} are to be stored, if it is
     *          big enough; otherwise, a new array of the same runtime type is allocated for this purpose
     * @return an array containing the vertices of the {@code VertexArray}
     * @throws ArrayStoreException  if the runtime type of the specified array is not a supertype of {@link Vertex}
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("SuspiciousToArrayCall")
    @NotNull
    @Override
    public <T> T @NotNull [] toArray(@NotNull T @NotNull [] a) {
        return vertices.toArray(a);
    }

    /**
     * Appends the specified element to the end of this {@code VertexArray}.
     *
     * @param vertex vertex to be appended to this {@code VertexArray}
     * @return {@code true} (as specified by {@link Collection#add})
     */
    @Override
    public boolean add(Vertex vertex) {
        return vertices.add(vertex);
    }

    /**
     * Removes the first occurrence of the specified vertex from this {@code VertexArray}, if it is
     * present. If the {@code VertexArray} does not contain the element, it is unchanged. More formally,
     * removes the vertex with the lowest index {@code i} such that {@code Objects.equals(vertex, get(i))}
     * (if such an element exists). Returns {@code true} if this {@code VertexArray} contained the
     * specified element (or equivalently, if this {@code VertexArray} changed as a result of the call).
     *
     * @param vertex vertex to be removed from this {@code VertexArray}, if present
     * @return {@code true} if this list contained the specified element
     */
    @Override
    public boolean remove(Object vertex) {
        return vertices.remove(vertex);
    }

    /**
     * Returns {@code true} if this {@code VertexArray} contains all of the vertices of the specified collection.
     *
     * @param c collection to be checked for containment in this list
     * @return {@code true} if this {@code VertexArray} contains all of the vertices of the specified collection
     * @throws ClassCastException   if the types of one or more elements in the specified
     *                              collection are incompatible with {@link Vertex}
     * @throws NullPointerException if the specified collection is {@code null}
     * @see #contains(Object)
     */
    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        return new HashSet<>(vertices).containsAll(c);
    }

    /**
     * Appends all of the vertices in the specified collection to the end of this {@code VertexArray}, in the order that
     * they are returned by the specified collection's {@code Iterator}. The behavior of this operation is undefined if the
     * specified collection is modified while the operation is in progress. (This implies that the behavior of this call
     * is undefined if the specified collection is this {@code VertexArray}, and this {@code VertexArray} is nonempty.)
     *
     * @param c collection containing vertices to be added to this {@code VertexArray}
     * @return {@code true} if this {@code VertexArray} changed as a result of the call, {@code false} otherwise
     * @throws NullPointerException if the specified collection is {@code null}
     */
    @Override
    public boolean addAll(@NotNull Collection<? extends Vertex> c) {
        return vertices.addAll(c);
    }

    /**
     * Inserts all of the vertices in the specified collection into this {@code VertexArray}, starting
     * at the specified position. Shifts the vertex currently at that position (if any) and any subsequent
     * vertex to the right (increases their indices). The new vertices will appear in the
     * {@code VertexArray} in the order that they are returned by the specified collection's {@code Iterator}.
     *
     * @param index index at which to insert the first vertex from the specified collection
     * @param c     collection containing vertices to be added to this {@code VertexArray}
     * @return {@code true} if this {@code VertexArray} changed as a result of the call, {@code false} otherwise
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException      if the specified collection is {@code null}
     */
    @Override
    public boolean addAll(int index, @NotNull Collection<? extends Vertex> c) {
        return vertices.addAll(index, c);
    }

    /**
     * Removes from this {@code VertexArray} all of its vertices that are contained in the specified collection.
     *
     * @param c collection containing vertices to be removed from this {@code VertexArray}
     * @return {@code true} if this {@code VertexArray} changed as a result of the call, {@code false} otherwise
     * @throws ClassCastException   if the class of the specified collection is incompatible with {@link Vertex}
     * @throws NullPointerException if this {@code VertexArray} contains a {@code null} element and the specified
     *                              collection does not permit null elements or if the specified collection is {@code null}
     * @see Collection#contains(Object)
     */
    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return vertices.removeAll(c);
    }

    /**
     * Retains only the vertives in this {@code VertexArray} that are contained in the specified collection. In other
     * words, removes from this {@code VertexArray} all of the vertices that are not contained in the specified collection.
     *
     * @param c collection containing vertices to be retained in this {@code VertexArray}
     * @return {@code true} if this {@code VertexArray} changed as a result of the call, {@code false} otherwise
     * @throws ClassCastException   if the class of the specified collection is incompatible with {@link Vertex}
     * @throws NullPointerException if this {@code VertexArray} contains a {@code null} element and the specified
     *                              collection does not permit null elements or if the specified collection is {@code null}
     * @see Collection#contains(Object)
     */
    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return vertices.retainAll(c);
    }

    /**
     * Removes all of the vertices from this {@code VertexArray}. The {@code VertexArray} will be empty after this call returns.
     */
    @Override
    public void clear() {
        vertices.clear();
    }

    /**
     * Returns the vertex at the specified position in this {@code VertexArray}.
     *
     * @param index index of the vertex to return
     * @return the vertex at the specified position in this {@code VertexArray}
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Vertex get(int index) {
        return vertices.get(index);
    }

    /**
     * Replaces the vertex at the specified position in this {@code VertexArray} with the specified element.
     *
     * @param index   index of the vertex to replace
     * @param element vertex to be stored at the specified position
     * @return the vertex previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Vertex set(int index, Vertex element) {
        return vertices.set(index, element);
    }

    /**
     * Inserts the specified vertex at the specified position in this {@code VertexArray}. Shifts the vertex
     * currently at that position (if any) and any subsequent vertices to the right (adds one to their indices).
     *
     * @param index  index at which the specified vertex is to be inserted
     * @param vertex vertex to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, Vertex vertex) {
        vertices.add(index, vertex);
    }

    /**
     * Removes the vertex at the specified position in this {@code VertexArray}.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     *
     * @param index the index of the vertex to be removed
     * @return the vertex that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public Vertex remove(int index) {
        return vertices.remove(index);
    }

    /**
     * Returns the index of the first occurrence of the specified vertex in this {@code VertexArray},
     * or -1 if this {@code VertexArray} does not contain the vertex. More formally, returns the lowest
     * index {@code i} such that {@code Objects.equals(vertex, get(i))}, or -1 if there is no such index.
     */
    @Override
    public int indexOf(Object vertex) {
        return vertices.indexOf(vertex);
    }

    /**
     * Returns the index of the last occurrence of the specified vertex in this {@code VertexArray},
     * or -1 if this {@code VertexArray} does not contain the vertex. More formally, returns the highest
     * index {@code i} such that {@code Objects.equals(vertex, get(i))}, or -1 if there is no such index.
     */
    @Override
    public int lastIndexOf(Object vertex) {
        return vertices.lastIndexOf(vertex);
    }

    /**
     * Returns a list iterator over the vertices in this {@code VertexArray} (in proper
     * sequence). The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @see #listIterator(int)
     */
    @NotNull
    @Override
    public ListIterator<Vertex> listIterator() {
        return vertices.listIterator();
    }

    /**
     * Returns a list iterator over the elements in this {@code VertexArray} (in proper sequence),
     * starting at the specified position in the {@code VertexArray}. The specified index indicates
     * the first element that would be returned by an initial call to {@link ListIterator#next next}.
     * An initial call to {@link ListIterator#previous previous} would return the element with the
     * specified index minus one. The returned list iterator is <a href="#fail-fast"><i>fail-fast</i></a>.
     *
     * @param index the starting position of the iterator
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @NotNull
    @Override
    public ListIterator<Vertex> listIterator(int index) {
        return vertices.listIterator(index);
    }

    /**
     * Returns a view of the portion of this {@code VertexArray} between the specified {@code fromIndex},
     * inclusive, and {@code toIndex}, exclusive. (If {@code fromIndex} and {@code toIndex} are equal, the
     * returned {@code VertexArray} is empty.) The returned {@code VertexArray} is backed by this {@code VertexArray},
     * so non-structural changes in the returned {@code VertexArray} are reflected in this {@code VertexArray},
     * and vice-versa. The returned {@code VertexArray} supports all of the optional list operations. This method
     * eliminates the need for explicit range operations (of the sort that commonly exist for arrays). Any
     * operation that expects a list can be used as a range operation by passing a subList view instead of a whole
     * {@code VertexArray}. For example, the following idiom removes a range of elements from a {@code VertexArray}:
     * <pre>
     *      array.subList(from, to).clear();
     * </pre>
     * Similar idioms may be constructed for {@link #indexOf(Object)} and {@link #lastIndexOf(Object)}, and
     * all of the algorithms in the {@link Collections} class can be applied to a subList. The semantics of
     * the {@code VertexArray} returned by this method become undefined if the backing {@code VertexArray}
     * (i.e. this {@code VertexArray}) is <i>structurally modified</i> in any way other than via the
     * returned {@code VertexArray}. (Structural modifications are those that change the size of this list,
     * or otherwise perturb it in such a fashion that iterations in progress may yield incorrect results.)
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws IllegalArgumentException  {@inheritDoc}
     */
    @NotNull
    @Override
    public VertexArray subList(int fromIndex, int toIndex) {
        return new VertexArray(primitiveType, vertices.subList(fromIndex, toIndex));
    }
}
