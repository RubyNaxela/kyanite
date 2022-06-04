package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

import java.io.Serial;
import java.io.Serializable;

/**
 * Defines a shape point with position, color and texture coordinates information.
 */
public final class Vertex implements Serializable {

    @Serial
    private static final long serialVersionUID = -5749297453247575018L;

    /**
     * The vertex position.
     */
    public Vector2f position;
    /**
     * The vertex color.
     */
    public Color color;
    /**
     * The vertex texture coordinates. The unit space this information is measured in depends on the way the respective
     * texture will be bound. By the default, the {@link Texture.CoordinateType#NORMALIZED} coordinate type is used.
     */
    public Vector2f texCoords;

    /**
     * Constructs a new vertex at the specified position with white color.
     *
     * @param position the vertex' position
     */
    public Vertex(@NotNull Vector2f position) {
        this(position, Colors.WHITE, Vector2f.ZERO);
    }

    /**
     * Constructs a new vertex with the specified position and color.
     *
     * @param position the vertex' position
     * @param color    the vertex' color
     */
    public Vertex(@NotNull Vector2f position, @NotNull Color color) {
        this(position, color, Vector2f.ZERO);
    }

    /**
     * Constructs a new vertex with the specified position and texture coordinates, with white color.
     *
     * @param position  the vertex' position
     * @param texCoords the vertex' texture coordinates
     */
    public Vertex(@NotNull Vector2f position, @NotNull Vector2f texCoords) {
        this(position, Colors.WHITE, texCoords);
    }

    /**
     * Constructs a new vertex with the specified parameters.
     *
     * @param position  the vertex' position
     * @param color     the vertex' color
     * @param texCoords the vertex' texture coordinates
     */
    public Vertex(@NotNull Vector2f position, @NotNull Color color, @NotNull Vector2f texCoords) {
        this.position = position;
        this.color = color;
        this.texCoords = texCoords;
    }
}
