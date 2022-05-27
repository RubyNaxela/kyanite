package com.rubynaxela.kyanite.game.gui;

import com.rubynaxela.kyanite.game.entities.GlobalRect;
import com.rubynaxela.kyanite.game.entities.MouseActionListener;
import com.rubynaxela.kyanite.util.MathUtils;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import org.jsfml.system.Vector2i;

public class CircleButton extends CircleShape implements MouseActionListener {

    /**
     * Constructs an empty circle button with a zero radius, approximated using 30 points.
     */
    public CircleButton() {
    }

    /**
     * Constructs an empty circle button with the specified radius, approximated using 30 points.
     *
     * @param radius the circle's radius.
     */
    public CircleButton(float radius) {
        super(radius);
    }

    /**
     * Constructs an empty circle button with the specified parameters.
     *
     * @param radius the circle's radius.
     * @param points the amount of points to approximate the circle with.
     * @see CircleShape#setPointCount(int)
     */
    public CircleButton(float radius, int points) {
        super(radius, points);
    }

    /**
     * Detects whether or not the mouse cursor is within this button's radius.
     *
     * @param cursorPosition the mouse cursor position
     * @return whether the mouse cursor is inside this button's radius
     */
    @Override
    public boolean isCursorInside(@NotNull Vector2i cursorPosition) {
        return MathUtils.isInsideCircle(Vec2.f(cursorPosition), GlobalRect.from(getGlobalBounds()).getCenter(), getRadius());
    }
}
