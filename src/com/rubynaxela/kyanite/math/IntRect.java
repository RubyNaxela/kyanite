/*
 This class uses direct C++ to Java translations from SFML source code, hence the following notice:

 ////////////////////////////////////////////////////////////
 //
 // SFML - Simple and Fast Multimedia Library
 // Copyright (C) 2007-2009 Laurent Gomila (laurent.gom@gmail.com)
 //
 // This software is provided 'as-is', without any express or implied warranty.
 // In no event will the authors be held liable for any damages arising from the use of this software.
 //
 // Permission is granted to anyone to use this software for any purpose,
 // including commercial applications, and to alter it and redistribute it freely,
 // subject to the following restrictions:
 //
 // 1. The origin of this software must not be misrepresented;
 //    you must not claim that you wrote the original software.
 //    If you use this software in a product, an acknowledgment
 //    in the product documentation would be appreciated but is not required.
 //
 // 2. Altered source versions must be plainly marked as such,
 //    and must not be misrepresented as being the original software.
 //
 // 3. This notice may not be removed or altered from any source distribution.
 //
 ////////////////////////////////////////////////////////////
*/

package com.rubynaxela.kyanite.math;

import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents an axis-aligned rectangle using integer coordinates.
 */
public final class IntRect implements Serializable {

    /**
     * An empty rectangle with no dimensions.
     */
    public static final IntRect EMPTY = new IntRect(0, 0, 0, 0);

    @Serial
    private static final long serialVersionUID = -4430448425788537785L;

    /**
     * The X coordinate of the rectangle's left edge.
     */
    public final int left;
    /**
     * The Y coordinate of the rectangle's top edge.
     */
    public final int top;
    /**
     * The width of the rectangle.
     */
    public final int width;
    /**
     * The height of the rectangle.
     */
    public final int height;

    /**
     * Constructs a new rectangle with the specified parameters.
     *
     * @param left   the X coordinate of the rectangle's left edge
     * @param top    the Y coordinate of the rectangle's top edge
     * @param width  the rectangle's width
     * @param height the rectangle's height
     */
    public IntRect(int left, int top, int width, int height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
    }

    /**
     * Constructs a new rectangle with the specified parameters.
     *
     * @param position the position of the rectangle's top left corner
     * @param size     the rectangle's dimension
     */
    public IntRect(@NotNull Vector2i position, @NotNull Vector2i size) {
        this.left = position.x;
        this.top = position.y;
        this.width = size.x;
        this.height = size.y;
    }

    /**
     * Constructs a new rectangle by converting a floating point rectangle. The fractions of the components will be removed.
     *
     * @param rect the rectangle to convert.
     */
    public IntRect(@NotNull FloatRect rect) {
        this((int) rect.left, (int) rect.top, (int) rect.width, (int) rect.height);
    }

    /**
     * Tests whether a point is inside the rectangle's boundaries, including its edges.
     *
     * @param x the X coordinate of the tested point.
     * @param y the Y coordinate of the tested point.
     * @return {@code true} if the point lies within the rectangle's boundaries,
     * {@code false} otherwise.
     */
    public boolean contains(int x, int y) {
        final int minX = Math.min(left, left + width),
                maxX = Math.max(left, left + width),
                minY = Math.min(top, top + height),
                maxY = Math.max(top, top + height);
        return (x >= minX) && (x < maxX) && (y >= minY) && (y < maxY);
    }

    /**
     * Tests whether a point is inside the rectangle's boundaries, including its edges.
     *
     * @param point the point to be tested
     * @return {@code true} if the point lies within the rectangle's boundaries, {@code false} otherwise
     */
    public boolean contains(@NotNull Vector2i point) {
        return contains(point.x, point.y);
    }

    /**
     * Tests whether this rectangle intersects with another rectangle and calculates the rectangle of intersection.
     *
     * @param rect the rectangle to test against
     * @return the intersection rectangle, or {@code null} if the rectangles do not intersect
     */
    public IntRect intersection(@NotNull IntRect rect) {
        final int interLeft = Math.max(Math.min(left, left + width), Math.min(rect.left, rect.left + rect.width)),
                interTop = Math.max(Math.min(top, top + height), Math.min(rect.top, rect.top + rect.height)),
                interRight = Math.min(Math.max(left, left + width), Math.max(rect.left, rect.left + rect.width)),
                interBottom = Math.min(Math.max(top, top + height), Math.max(rect.top, rect.top + rect.height));
        if ((interLeft < interRight) && (interTop < interBottom))
            return new IntRect(interLeft, interTop, interRight - interLeft, interBottom - interTop);
        else return null;
    }

    @Override
    public int hashCode() {
        int result = left;
        result = 31 * result + top;
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public boolean equals(Object oother) {
        if (oother instanceof final IntRect r)
            return (left == r.left && top == r.top && width == r.width && height == r.height);
        else return false;
    }

    @Override
    public String toString() {
        return "IntRect{left=" + left + ", top=" + top + ", width=" + width + ", height=" + height + '}';
    }
}
