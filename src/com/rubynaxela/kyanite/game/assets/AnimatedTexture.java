package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.util.Utils;
import org.jetbrains.annotations.NotNull;
import org.jsfml.graphics.Shape;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;

import java.util.HashMap;
import java.util.Map;

public class AnimatedTexture implements Asset {

    public static final Map<Object, AnimatedTexture> animatedObjects = new HashMap<>();

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
     * Updates this texture for the specified object. This method is
     * run by the scene loop and does not need to be invoked manualy.
     */
    public void update(@NotNull Object object, @NotNull Time elapsedTime) {
        final int frame = (int) (elapsedTime.asSeconds() / frameDuration) % frames.length;
        if (object instanceof final Shape shape) frames[frame].apply(shape);
        if (object instanceof final Sprite sprite) frames[frame].apply(sprite);
    }
}
