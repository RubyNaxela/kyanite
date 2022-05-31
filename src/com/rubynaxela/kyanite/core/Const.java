package com.rubynaxela.kyanite.core;

/**
 * Interface for read-only objects. This interface is Kyanite's way to map C++ const references to Java.
 * Subinterfaces will be specific to a certain Kyanite type and provide methods to read from them only.
 * The subinterfaces may be used freely outside of the Kyanite API itself, however, it is not recommended to
 * implement them, because in certain cases, they are expected to be implemented by a certain Kyanite class.
 */
public interface Const {

}
