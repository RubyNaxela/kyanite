package com.rubynaxela.kyanite.core;

/**
 * Provides inherently unsafe operations on native SFML objects. These need to be public in order to
 * maintain Kyanite's package structure, but should by no means used outside of the Kyanite framework.
 */
public final class UnsafeOperations {

    private UnsafeOperations() {
    }

    /**
     * Flags an SFML object as Java managed or unmanaged. Java managed objects will be destroyed using the
     * {@code SFMLNativeObject#nativeDelete()} method when this object gets finalized. This is used for
     * Kyanite to differentiate between explicitly self-constructed SFML objects (using {@code new}) and
     * SFML objects that are managed by other SFML objects, but require a Java representation. Wrong use of
     * this method will make the application prone to crashes and memory leaks, so handle with extreme care.
     *
     * @param object  the SFML object wrapper
     * @param managed whether or not this object is managed by Kyanite
     */
    public static void manageSFMLObject(SFMLNativeObject object, boolean managed) {
        object.setJavaManaged(managed);
    }
}
