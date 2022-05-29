package com.rubynaxela.kyanite.game.entities;

import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.game.Scene;
import com.rubynaxela.kyanite.game.assets.Texture;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.CircleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

/**
 * Represents a temporary object displaying a sequence of textures during its lifetime.
 */
public class Particle extends CircleShape implements AnimatedEntity, MovingEntity {

    private final Scene scene;
    private final Texture[] frames;
    private final float lifetime;
    private Vector2f velocity;
    private float timeSpawned;
    private int stage = 0;

    /**
     * Constructs a {@code Particle} with the specified parameters.
     *
     * @param scene    the scene this particle belongs to
     * @param frames   the sequence of textures to be displayed (duration of a single frame is {@code lifetime / frames.length})
     * @param lifetime the duration of this {@code Particle}'s lifetime (in seconds)
     * @param velocity the velocity at which this {@code Particle} moves
     */
    public Particle(@NotNull Scene scene, @NotNull Texture[] frames, float lifetime, @NotNull Vector2f velocity) {
        super(64);
        this.scene = scene;
        this.frames = frames;
        this.lifetime = lifetime;
        this.velocity = velocity;
    }

    /**
     * Constructs a non-moving {@code Particle} with the specified parameters.
     *
     * @param scene    the scene this particle belongs to
     * @param frames   the sequence of textures to be displayed (duration of a single frame is {@code lifetime / frames.length})
     * @param lifetime the duration of this {@code Particle}'s lifetime
     */
    public Particle(@NotNull Scene scene, @NotNull Texture[] frames, float lifetime) {
        this(scene, frames, lifetime, Vector2f.ZERO);
    }

    private void nextFrame() {
        frames[stage++].apply(this);
    }

    @Override
    public void animate(@NotNull Time deltaTime, @NotNull Time elapsedTime) {
        if (stage == 0) {
            timeSpawned = GameContext.getInstance().getClock().getTime().asSeconds();
            nextFrame();
        } else for (int i = 0; i < frames.length; i++) {
            if (stage == frames.length && elapsedTime.asSeconds() - timeSpawned > lifetime) {
                scene.scheduleToRemove(this);
                break;
            } else if (stage == i && elapsedTime.asSeconds() - timeSpawned > (float) i / frames.length * lifetime) {
                nextFrame();
                break;
            }
        }
    }

    @Override
    public @NotNull Vector2f getVelocity() {
        return velocity;
    }

    @Override
    public void setVelocity(@NotNull Vector2f velocity) {
        this.velocity = velocity;
    }
}
