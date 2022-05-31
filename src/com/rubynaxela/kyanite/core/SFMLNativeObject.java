package com.rubynaxela.kyanite.core;

/**
 * Abstract base class for classes bound to SFML C++ objects. This class serves as a communication interface
 * between native SFML objects and their Java representations by linking the Java object to the object's
 * native pointer. There should be no reason whatsoever to use this class outside of Kyanite itself.
 */
@SuppressWarnings("deprecation")
@Intercom
public abstract class SFMLNativeObject extends org.jsfml.internal.SFMLNativeObject {

    /**
     * If this is {@code true}, the underlying object is merely "wrapped" and not "managed" by Kyanite. If an object
     * is managed by Kyanite, it will be deleted using the {@code delete} operator when the Java object is finalized.
     * Wrapped objects are expected to be cleaned up by SFML and will simply be abandoned upon finalization.
     */
    private boolean wrapped;

    /**
     * Constructs a Kyanite native object by invoking the {@link #nativeCreate()}
     * method and retrieving a pointer to the SFML object in the JVM heap.
     */
    protected SFMLNativeObject() {
        SFMLNative.loadNativeLibraries();
        ptr = nativeCreate();
        if (ptr == 0) throw new NullPointerException("nativeCreate() yielded a NULL pointer: " + this);
        nativeSetExPtr();
        wrapped = false;
    }

    /**
     * Wraps an Kyanite native object around an already existing native SFML object.
     *
     * @param wrap the pointer to the native SFML object in the JNI heap
     * @deprecated Use of this constructor may cause undefined behaviour and is not supported.
     */
    @Deprecated
    protected SFMLNativeObject(long wrap) {
        SFMLNative.loadNativeLibraries();
        if (wrap == 0) throw new NullPointerException("Tried to wrap around a NULL pointer: " + this);
        ptr = wrap;
        nativeSetExPtr();
        wrapped = true;
    }

    public final void setJavaManaged(boolean javaManaged) {
        wrapped = !javaManaged;
    }

    /**
     * Creates a new native SFML object of the represented SFML class in the JVM memory heap.
     *
     * @return the pointer to the newly created native SFML object
     * @deprecated Use of this method may cause undefined behaviour and is not supported.
     */
    @Override
    @Deprecated
    protected abstract long nativeCreate();

    /**
     * This method is expected to fill the extra pointers array, if any. It is called
     * after the object has been created or wrapped around an already existing pointer.
     *
     * @deprecated Use of this method may cause undefined behaviour and is not supported.
     */
    @Override
    @Deprecated
    protected abstract void nativeSetExPtr();

    /**
     * Deletes the underlying SFML object.
     *
     * @deprecated Use of this method may cause undefined behaviour and is not supported.
     */
    @Override
    @Deprecated
    protected abstract void nativeDelete();

    @Override
    @SuppressWarnings("deprecation")
    protected void finalize() throws Throwable {
        if (!wrapped && ptr != 0) nativeDelete();
        ptr = 0;
        for (int i = 0; i < ExPtr.NUM; i++) exPtr.put(i, 0);
        super.finalize();
    }
}
