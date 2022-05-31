package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.Const;
import com.rubynaxela.kyanite.math.Vector2i;

/**
 * Interface for read-only textures. It provides methods to can gain information from a texture,
 * but none to modify it in any way. Note that this interface is expected to be implemented by
 * a {@link Texture}. It is not recommended to be implemented outside of the Kyanite API.
 *
 * @see Const
 */
public interface ConstTexture extends Const {

    /**
     * Gets the dimensions of the texture.
     *
     * @return the dimensions of the texture
     */
    Vector2i getSize();

    /**
     * Copies this texture to an editable {@link Image}.
     *
     * @return the image that contains a coyp of the texure's contents
     */
    Image copyToImage();

    /**
     * Checks whether the smooth filter is enabled.
     *
     * @return {@code true} if enabled, {@code false} if disabled
     */
    boolean isSmooth();

    /**
     * Checks whether texture repeating is enabled.
     *
     * @return {@code true} if enabled, {@code false} if disabled
     */
    boolean isRepeated();
}
