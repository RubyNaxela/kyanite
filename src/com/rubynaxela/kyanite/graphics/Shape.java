package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.IntRect;
import com.rubynaxela.kyanite.math.Vector2f;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Abstract base class for (optionally) textured shapes with (optional) outlines.
 */
@SuppressWarnings("deprecation")
public abstract class Shape extends org.jsfml.graphics.Shape implements Drawable {

    protected boolean pointsNeedUpdate = true;
    protected Vector2f[] points = null;

    private Color fillColor = Colors.WHITE;
    private Color outlineColor = Colors.WHITE;
    private float outlineThickness = 0;
    private IntRect textureRect = IntRect.EMPTY;
    private ConstTexture texture = null;
    private boolean boundsNeedUpdate = true;
    private FloatRect localBounds = null;
    private FloatRect globalBounds = null;

    /**
     * Sets the texture of the shape. The texture may be {@code null} if no texture is to be used.
     *
     * @param texture   the texture of the shape, or {@code null} to indicate that no texture is to be used
     * @param resetRect {@code true} to reset the texture rect, {@code false} otherwise
     */
    public void setTexture(ConstTexture texture, boolean resetRect) {
        this.texture = texture;
        nativeSetTexture((Texture) texture, resetRect);
    }

    /**
     * Gets the shape's current texture.
     *
     * @return the shape's current texture
     */
    public ConstTexture getTexture() {
        return texture;
    }

    /**
     * Sets the texture of the shape without affecting the texture rectangle.
     * The texture may be {@code null} if no texture is to be used.
     *
     * @param texture the texture of the shape, or {@code null} to indicate that no texture is to be used
     */
    public final void setTexture(ConstTexture texture) {
        setTexture(texture, false);
    }

    /**
     * Gets the shape's current texture portion.
     *
     * @return the shape's current texture portion
     */
    public IntRect getTextureRect() {
        return textureRect;
    }

    /**
     * Sets the portion of the texture that will be used for drawing. An empty rectangle can be
     * passed to indicate that the whole texture shall be used. The width and / or height of the
     * rectangle may be negative to indicate that the respective axis should be flipped. For example,
     * a width of {@code -32} will result in a sprite that is 32 pixels wide and flipped horizontally.
     *
     * @param rect the texture portion
     */
    public void setTextureRect(@NotNull IntRect rect) {
        nativeSetTextureRect(IntercomHelper.encodeIntRect(rect));
        this.textureRect = rect;
    }

    /**
     * Gets the shape's current fill color.
     *
     * @return the shape's current fill color
     */
    public Color getFillColor() {
        return fillColor;
    }

    /**
     * Sets the fill color of the shape.
     *
     * @param color the new fill color of the shape, or {@link Colors#TRANSPARENT}
     *              to indicate that the shape should not be filled
     */
    public void setFillColor(@NotNull Color color) {
        nativeSetFillColor(IntercomHelper.encodeColor(color));
        this.fillColor = color;
    }

    /**
     * Gets the shape's current outline color.
     *
     * @return the shape's current outline color
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * Sets the outline color of the shape.
     *
     * @param color the new outline color of the shape
     */
    public void setOutlineColor(@NotNull Color color) {
        nativeSetOutlineColor(IntercomHelper.encodeColor(color));
        this.outlineColor = color;
    }

    /**
     * Gets the shape's current outline thickness.
     *
     * @return the shape's current outline thickness
     */
    public float getOutlineThickness() {
        return outlineThickness;
    }

    /**
     * Sets the thickness of the shape's outline.
     *
     * @param thickness the thickness of the shape's outline, or 0 to indicate that no outline should be drawn
     */
    public void setOutlineThickness(float thickness) {
        nativeSetOutlineThickness(thickness);
        this.outlineThickness = thickness;
    }

    private void updatePoints() {
        if (pointsNeedUpdate) {
            final int n = nativeGetPointCount();
            final FloatBuffer buffer = ByteBuffer.allocateDirect(2 * n * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            nativeGetPoints(n, buffer);
            points = new Vector2f[n];
            for (int i = 0; i < n; i++) points[i] = new Vector2f(buffer.get(2 * i), buffer.get(2 * i + 1));
            pointsNeedUpdate = false;
        }
    }

    /**
     * Gets the amount of points that defines this shape.
     *
     * @return the amount of points that defines this shape
     */
    public int getPointCount() {
        if (pointsNeedUpdate) updatePoints();
        return points.length;
    }

    /**
     * Gets a point of the shape.
     *
     * @param i the index of the point to retrieve
     * @return the point at the given index
     */
    public Vector2f getPoint(int i) {
        if (pointsNeedUpdate) updatePoints();
        return points[i];
    }

    /**
     * Gets all the points of the shape.
     *
     * @return an array containing the points of the shape
     */
    public Vector2f[] getPoints() {
        final int n = getPointCount();
        Vector2f[] points = new Vector2f[n];
        System.arraycopy(this.points, 0, points, 0, n);
        return points;
    }

    private void updateBounds() {
        if (boundsNeedUpdate) {
            nativeGetLocalBounds(IntercomHelper.getBuffer());
            localBounds = IntercomHelper.decodeFloatRect();
            nativeGetGlobalBounds(IntercomHelper.getBuffer());
            globalBounds = IntercomHelper.decodeFloatRect();
            boundsNeedUpdate = false;
        }
    }

    /**
     * Gets the text's local bounding rectangle, <i>not</i> taking the text's transformation into account.
     *
     * @return the text's local bounding rectangle
     * @see Sprite#getGlobalBounds()
     */
    public FloatRect getLocalBounds() {
        if (boundsNeedUpdate) updateBounds();
        return localBounds;
    }

    /**
     * Gets the text's global bounding rectangle in the scene, taking the text's transformation into account.
     *
     * @return the text's global bounding rectangle
     * @see Text#getLocalBounds()
     */
    public FloatRect getGlobalBounds() {
        if (boundsNeedUpdate) updateBounds();
        return globalBounds;
    }

    @Override
    public void setPosition(@NotNull Vector2f v) {
        super.setPosition(v);
        boundsNeedUpdate = true;
    }

    @Override
    public void setRotation(float angle) {
        super.setRotation(angle);
        boundsNeedUpdate = true;
    }

    @Override
    public void setScale(@NotNull Vector2f v) {
        super.setScale(v);
        boundsNeedUpdate = true;
    }

    @Override
    public void setOrigin(@NotNull Vector2f v) {
        super.setOrigin(v);
        boundsNeedUpdate = true;
    }

    @Override
    public void draw(@NotNull RenderTarget target, @NotNull RenderStates states) {
        SFMLNativeDrawer.draw(this, target, states);
    }
}
