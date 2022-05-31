package com.rubynaxela.kyanite.core;

/**
 * Holds index definitions for the {@link SFMLNativeObject#exPtr} ("Extra Pointers") array. These are pointers
 * dynamically cast to abstract supertypes of the same SFML object. Holding these is necessary, because all the
 * C++ type information gets lost in the process of storing a pointer in a Java long field. When casting them back
 * toSFML object pointers, the correct virtual table offset has to be used for calls to methods of abstract types.
 */
@Intercom
public final class ExPtr {

    //Total amount of exPtr fields.
    @Intercom
    public static final int NUM = 3;

    //Pointer to sf::Drawable.
    @Intercom
    public static final int DRAWABLE = 0;

    //Pointer to sf::Transformable.
    @Intercom
    public static final int TRANSFORMABLE = 1;

    //Pointer to sf::Shape.
    @Intercom
    public static final int SHAPE = 2;

    //Pointer to sf::RenderTarget.
    @Intercom
    public static final int RENDER_TARGET = 0;

    //Pointer to sf::Window.
    @Intercom
    public static final int WINDOW = 1;

    //Pointer to sf::SoundSource.
    @Intercom
    public static final int SOUND_SOURCE = 0;

    //Pointer to sf::SoundStream.
    @Intercom
    public static final int SOUND_STREAM = 1;

    //Pointer to sf::SoundRecorder.
    @Intercom
    public static final int SOUND_RECORDER = 0;
}
