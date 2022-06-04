package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.FloatRect;

/**
 * Represents objects that have determinable local and global bounds.
 */
public interface BoundsObject {

    /**
     * Gets the object's local bounding rectangle, <i>not</i> taking its transformation into account.
     *
     * @return the object's local bounding rectangle
     * @see Shape#getLocalBounds()
     * @see Sprite#getLocalBounds()
     * @see Text#getLocalBounds()
     * @see VertexArray#getLocalBounds()
     */
    FloatRect getLocalBounds();

    /**
     * Gets the object's global bounding rectangle in the scene, taking its transformation into account.
     *
     * @return the object's local bounding rectangle
     * @see Shape#getGlobalBounds()
     * @see Sprite#getGlobalBounds()
     * @see Text#getGlobalBounds()
     * @see VertexArray#getGlobalBounds()
     */
    FloatRect getGlobalBounds();
}
