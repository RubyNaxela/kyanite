package com.rubynaxela.kyanite.game.entities;

import org.jetbrains.annotations.NotNull;
import com.rubynaxela.kyanite.util.Time;

/**
 * This interface represents an entity with an animation. The animation method is called every frame.
 */
public interface AnimatedEntity {

    /**
     * This method is called every window frame. It is designed to be used for defining the entity animated behavior.
     *
     * @param deltaTime   the time that has passed between the previous and the current frame
     * @param elapsedTime the time that has passed since you the game was started
     */
    void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime);
}
