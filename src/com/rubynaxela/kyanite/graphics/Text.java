package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.math.FloatRect;
import com.rubynaxela.kyanite.math.Vector2f;
import org.intellij.lang.annotations.MagicConstant;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a graphical text that can be transformed and drawn to a render target. This class
 * implements the {@code TextStyle} interface for quick access to the constants provided by it.
 */
@SuppressWarnings("deprecation")
public class Text extends org.jsfml.graphics.Text {

    private ConstFont font = null;
    private String string = "";
    private Color color = Colors.WHITE;
    private int style = TextStyle.REGULAR;
    private int characterSize = 30;

    private boolean boundsNeedUpdate = true;
    private FloatRect localBounds = null;
    private FloatRect globalBounds = null;

    /**
     * Creates a new empty text.
     */
    public Text() {
    }

    /**
     * Creates a new text.
     *
     * @param string the text string
     * @param font   the font to use
     */
    public Text(@NotNull String string, @NotNull ConstFont font) {
        setFont(font);
        setString(string);
    }

    /**
     * Creates a new text.
     *
     * @param string        the text string
     * @param font          the font to use
     * @param characterSize the font size
     */
    public Text(@NotNull String string, @NotNull ConstFont font, int characterSize) {
        setCharacterSize(characterSize);
        setFont(font);
        setString(string);
    }

    /**
     * Gets the text's string.
     *
     * @return the text strng
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the string to display.
     *
     * @param string the string to display
     */
    public void setString(String string) {
        this.string = Objects.requireNonNull(string);
        nativeSetString(string);
        boundsNeedUpdate = true;
    }

    /**
     * Gets the text's current font.
     *
     * @return The text's current font (may be {@code null} if no font has been set yet)
     */
    @Nullable
    public ConstFont getFont() {
        return font;
    }

    /**
     * Sets the text's font.
     *
     * @param font the text's font
     */
    public void setFont(@NotNull ConstFont font) {
        this.font = Objects.requireNonNull(font);
        nativeSetFont((Font) font);
        boundsNeedUpdate = true;
    }

    /**
     * Gets the text's current font size.
     *
     * @return the text's current font size
     */
    public int getCharacterSize() {
        return characterSize;
    }

    /**
     * Sets the font size for this text.
     *
     * @param characterSize the font size for this text
     */
    public void setCharacterSize(int characterSize) {
        nativeSetCharacterSize(characterSize);
        this.characterSize = characterSize;
        boundsNeedUpdate = true;
    }

    /**
     * Gets the text's current font style.
     *
     * @return the text's current font style
     * @see Text#setStyle(int)
     */
    public int getStyle() {
        return style;
    }

    /**
     * Sets the font drawing style.
     *
     * @param style the font drawing style
     */
    public void setStyle(@MagicConstant(flagsFromClass = TextStyle.class) int style) {
        nativeSetStyle(style);
        this.style = style;
        boundsNeedUpdate = true;
    }

    /**
     * Gets the text's current font color.
     *
     * @return the text's current font color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the font color for this text.
     *
     * @param color the font color for this text
     */
    public void setColor(Color color) {
        nativeSetColor(IntercomHelper.encodeColor(color));
        this.color = color;
    }

    /**
     * Returns the position of a character inside the string.
     *
     * @param i the index of the character to return the position for
     * @return the position of the character at the given index
     */
    public Vector2f findCharacterPos(int i) {
        if (i < 0 || i >= string.length()) throw new StringIndexOutOfBoundsException(Integer.toString(i));
        return IntercomHelper.decodeVector2f(nativeFindCharacterPos(i));
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
