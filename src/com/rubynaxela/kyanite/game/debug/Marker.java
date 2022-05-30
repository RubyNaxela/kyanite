package com.rubynaxela.kyanite.game.debug;

import com.rubynaxela.kyanite.util.Colors;
import com.rubynaxela.kyanite.util.Vec2;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import com.rubynaxela.kyanite.core.system.Vector2f;

/**
 * A small red circle that can be used to mark a position while debugging the game.
 */
public class Marker extends CircleShape {

    /**
     * Constructs a new marker.
     *
     * @param position the initial position of the marker
     */
    public Marker(@NotNull Vector2f position) {
        super(4);
        setPosition(position);
        setOrigin(2, 2);
        setFillColor(Colors.RED);
    }

    /**
     * Constructs a new marker.
     *
     * @param x the initial x-coordinate of the marker
     * @param y the initial y-coordinate of the marker
     */
    public Marker(float x, float y) {
        this(Vec2.f(x, y));
    }
}
