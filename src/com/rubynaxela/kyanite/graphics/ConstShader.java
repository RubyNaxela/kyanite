package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.Const;

/**
 * Interface for read-only textures. It provides methods to can gain information from a shader,
 * but none to modify it in any way. Note that this interface is expected to be implemented by
 * a {@link Shader}. It is not recommended to be implemented outside of the Kyanite API.
 *
 * @see Const
 */
public interface ConstShader extends Const {

}
