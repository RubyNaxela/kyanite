package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.game.assets.Asset;
import com.rubynaxela.kyanite.util.Time;
import com.rubynaxela.kyanite.util.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * An animated texture that can be created from an array of {@link Texture}s with a fixed frame duration. Once applied on
 * a {@link Shape} or a {@link Sprite}, it is automatically updated using the current scene's clock as the time reference.
 */
public class AnimatedTexture implements Asset {

    /**
     * Stores bindings between {@code Drawable} objects and {@code AnimatedTexture}s. Since native JSFML
     * classes are not modified, this is the only way to assign an {@code AnimatedTexture} to an object.
     * This map does not need to be accessed and should not be modified from outside of the Kyanite framework.
     */
    public static final Map<Drawable, AnimatedTexture> animatedObjects = new HashMap<>();

    private final Texture[] frames;
    private final float frameDuration;

    /**
     * Creates an animated texture from the specified frames. Every frame will last {@code frameDuration} seconds.
     *
     * @param frames        the frames of this animated texture
     * @param frameDuration duration of a single frame (in seconds)
     */
    public AnimatedTexture(@NotNull Texture[] frames, float frameDuration) {
        this.frames = frames;
        this.frameDuration = frameDuration;
    }

    /**
     * Creates an animated texture from the specified frames. Every frame will last
     * {@code frameDuration} seconds. The 2D array will be flattened into a 1D array.
     *
     * @param frames        the frames of this animated texture
     * @param frameDuration duration of a single frame (in seconds)
     */
    public AnimatedTexture(@NotNull Texture[][] frames, float frameDuration) {
        this(Utils.flatten2DArray(Texture.class, frames), frameDuration);
    }

    /**
     * Applies this texture on the {@link Shape}.
     *
     * @param shape the {@link Shape} to apply this texture on
     */
    public void apply(@NotNull Shape shape) {
        animatedObjects.put(shape, this);
    }

    /**
     * Applies this texture on the {@link Sprite}.
     *
     * @param sprite the {@link Sprite} to apply this texture on
     */
    public void apply(@NotNull Sprite sprite) {
        animatedObjects.put(sprite, this);
    }

    /**
     * Removes this texture from the {@link Shape}.
     *
     * @param shape the {@link Shape} to remove this texture from
     */
    public void remove(@NotNull Shape shape) {
        animatedObjects.remove(shape);
    }

    /**
     * Removes this texture from the {@link Sprite}.
     *
     * @param sprite the {@link Sprite} to remove this texture from
     */
    public void remove(@NotNull Sprite sprite) {
        animatedObjects.remove(sprite);
    }

    /**
     * Updates this texture for the specified object. This method is
     * run by the scene loop and does not need to be invoked manualy.
     *
     * @param object      the object for which to update the texture
     * @param elapsedTime the time since the game started
     */
    public void update(@NotNull Object object, @NotNull Time elapsedTime) {
        final int frame = (int) (elapsedTime.asSeconds() / frameDuration) % frames.length;
        if (object instanceof final Shape shape) frames[frame].apply(shape);
        if (object instanceof final Sprite sprite) frames[frame].apply(sprite);
    }
}
