/*
 * Copyright (c) 2021-2022 Alex Pawelski
 *
 * Licensed under the Silicon License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://rubynaxela.github.io/Silicon-License/plain_text.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.rubynaxela.kyanite.graphics;

import com.rubynaxela.kyanite.core.IntercomHelper;
import com.rubynaxela.kyanite.core.SFMLErrorCapture;
import com.rubynaxela.kyanite.core.SFMLNative;
import com.rubynaxela.kyanite.core.StreamUtil;
import com.rubynaxela.kyanite.math.Vector2f;
import com.rubynaxela.kyanite.math.Vector3f;
import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Represents a GLSL shader program, consisting of a vertex and a fragment shader.
 */
@SuppressWarnings("deprecation")
public class Shader extends org.jsfml.graphics.Shader implements ConstShader {

    /**
     * Special value denoting that the texture of the object being drawn
     * should be used, which cannot be known before it is actually being drawn.
     *
     * @see Shader#setParameter(String, Shader.CurrentTextureType)
     */
    public static final CurrentTextureType CURRENT_TEXTURE = new CurrentTextureType();

    static {
        SFMLNative.loadNativeLibraries();
    }

    /**
     * Constructs a new shader.
     */
    public Shader() {
    }

    /**
     * Activates a shader for rendering. This is required only if you wish to use Kyanite shaders in custom OpenGL code.
     *
     * @param shader the shader to activate, or {@code null} to indicate that no shader is to be used
     */
    public static void bind(ConstShader shader) {
        org.jsfml.graphics.Shader.bind(shader);
    }

    /**
     * Checks if shaders are available on this system.
     *
     * @return {@code true} if shaders are available, {@code false} otherwise
     */
    public static boolean isAvailable() {
        return org.jsfml.graphics.Shader.isAvailable();
    }

    private void throwException(@Nullable String msg) throws IOException, ShaderSourceException {
        if (msg != null && (msg.startsWith("Failed to compile") || msg.startsWith("Failed to link")))
            throw new ShaderSourceException(msg);
        else throw new IOException(msg);
    }

    /**
     * Attempts to load a shader from GLSL source code.
     *
     * @param source     the GLSL source code
     * @param shaderType the shader type
     * @throws IOException           in case an I/O error occurs
     * @throws ShaderSourceException in case the shader could not be compiled or linked
     */
    public void loadFromSource(@NotNull String source, @NotNull Type shaderType) throws IOException, ShaderSourceException {
        SFMLErrorCapture.start();
        final boolean result = nativeLoadFromSource1(source, shaderType.ordinal());
        final String msg = SFMLErrorCapture.finish();
        if (!result) throwException(msg);
    }

    /**
     * Attempts to load a shader from GLSL source code.
     *
     * @param vertexShaderSource   the vertex shader's GLSL source code
     * @param fragmentShaderSource the fragment shader's GLSL source code
     * @throws IOException           in case an I/O error occurs
     * @throws ShaderSourceException in case the shader could not be compiled or linked
     */
    public void loadFromSource(@NotNull String vertexShaderSource, @NotNull String fragmentShaderSource)
    throws IOException, ShaderSourceException {
        SFMLErrorCapture.start();
        final boolean result = nativeLoadFromSource2(vertexShaderSource, fragmentShaderSource);
        final String msg = SFMLErrorCapture.finish();
        if (!result) throwException(msg);
    }

    /**
     * Fully loads all available bytes from an {@link InputStream} and attempts to load the shader from it.
     *
     * @param in         the input stream to read from
     * @param shaderType the shader type
     * @throws IOException           in case an I/O error occurs
     * @throws ShaderSourceException in case the shader could not be compiled or linked
     */
    public void loadFromStream(InputStream in, Type shaderType) throws IOException, ShaderSourceException {
        loadFromSource(new String(StreamUtil.readStream(in)), Objects.requireNonNull(shaderType));
    }

    /**
     * Fully loads all available bytes from an {@link InputStream} and attempts to load the shader from it
     *
     * @param vertexShaderIn   the input stream to read the vertex shader from
     * @param fragmentShaderIn the input stream to read the fragment shader from
     * @throws IOException           in case an I/O error occurs
     * @throws ShaderSourceException in case the shader could not be compiled or linked
     */
    public void loadFromStream(@NotNull InputStream vertexShaderIn, @NotNull InputStream fragmentShaderIn)
    throws IOException, ShaderSourceException {
        loadFromSource(new String(StreamUtil.readStream(vertexShaderIn)), new String(StreamUtil.readStream(fragmentShaderIn)));
    }

    /**
     * Attempts to load the shader from a file.
     *
     * @param path       the path to the file to load
     * @param shaderType the shader type
     * @throws IOException           in case an I/O error occurs
     * @throws ShaderSourceException in case the shader could not be compiled or linked
     */
    public void loadFromFile(@NotNull Path path, @NotNull Type shaderType) throws IOException, ShaderSourceException {
        loadFromSource(new String(StreamUtil.readFile(path)), shaderType);
    }

    /**
     * Attempts to load the shader from files.
     *
     * @param vertexShaderFile   the path to the file to read the vertex shader from
     * @param fragmentShaderFile the path to the file to read the fragment shader from
     * @throws IOException           in case an I/O error occurs
     * @throws ShaderSourceException in case the shader could not be compiled or linked
     */
    public void loadFromFile(@NotNull Path vertexShaderFile, @NotNull Path fragmentShaderFile)
    throws IOException, ShaderSourceException {
        loadFromSource(new String(StreamUtil.readFile(vertexShaderFile)), new String(StreamUtil.readFile(fragmentShaderFile)));
    }

    /**
     * Sets a float parameter ({@code float}) value in the shader.
     *
     * @param name  the parameter's name
     * @param value the parameter's value
     */
    public void setParameter(@NotNull String name, float value) {
        nativeSetParameterFloat(name, value);
    }

    /**
     * Sets a 2-component-float ({@code vec2}) parameter value in the shader.
     *
     * @param name the parameter's name
     * @param x    the parameter's value
     * @param y    the parameter's value
     */
    public void setParameter(@NotNull String name, float x, float y) {
        nativeSetParameterVec2(name, x, y);
    }

    /**
     * Sets a 2-component-float ({@code vec2}) parameter value in the shader.
     *
     * @param name the parameter's name
     * @param v    the parameter's value
     */
    public void setParameter(@NotNull String name, Vector2f v) {
        setParameter(name, v.x, v.y);
    }

    /**
     * Sets a 3-component-float ({@code vec3}) parameter value in the shader.
     *
     * @param name the parameter's name
     * @param x    the parameter's value
     * @param y    the parameter's value
     * @param z    the parameter's value
     */
    public void setParameter(@NotNull String name, float x, float y, float z) {
        nativeSetParameterVec3(name, x, y, z);
    }

    /**
     * Sets a 3-component-float ({@code vec3}) parameter value in the shader.
     *
     * @param name the parameter's name
     * @param v    the parameter's value
     */
    public void setParameter(@NotNull String name, Vector3f v) {
        setParameter(name, v.x, v.y, v.z);
    }

    /**
     * Sets a 4-component-float ({@code vec4}) parameter value in the shader.
     *
     * @param name the parameter's name
     * @param x    the parameter's value
     * @param y    the parameter's value
     * @param z    the parameter's value
     * @param w    the parameter's value
     */
    public void setParameter(@NotNull String name, float x, float y, float z, float w) {
        nativeSetParameterVec4(name, x, y, z, w);
    }

    /**
     * Sets a color (vec4) parameter value in the shader.
     *
     * @param name  the parameter's name
     * @param color the parameter's value
     */
    public void setParameter(@NotNull String name, Color color) {
        setParameter(name, (float) color.r / 255.0f, (float) color.g / 255.0f,
                     (float) color.b / 255.0f, (float) color.a / 255.0f);
    }

    /**
     * Sets a matrix (mat4) parameter value in the shader.
     *
     * @param name      the parameter's name
     * @param transform the parameter's value
     */
    public void setParameter(@NotNull String name, @NotNull Transform transform) {
        nativeSetParameterMat4(name, IntercomHelper.encodeTransform(transform));
    }

    /**
     * Sets a texture (sampler2D) parameter value in the shader.
     *
     * @param name    the parameter's name.
     * @param texture the parameter's value.
     */
    public void setParameter(@NotNull String name, @NotNull ConstTexture texture) {
        nativeSetParameterSampler2d(name, (Texture) texture);
    }

    /**
     * Sets a texture (sampler2D) parameter value in the shader to the texture of the object being
     * drawn in the moment the shader is applied. Since that texture cannot be known before the object
     * is actually being drawn, this overload can be used to tell the shader to use it when applied.
     *
     * @param name the parameter's name.
     * @param t    should be {@link Shader#CURRENT_TEXTURE}.
     */
    public void setParameter(@NotNull String name, CurrentTextureType t) {
        nativeSetParameterCurrentTexture(name);
    }

    /**
     * Enumeration of shader types.
     */
    public enum Type {

        /**
         * Vertex shaders.
         */
        VERTEX,
        /**
         * Fragment shaders.
         */
        FRAGMENT
    }

    /**
     * Special type denoting that the texture of the object being drawn
     * should be used, which cannot be known before it is actually being drawn.
     *
     * @see Shader#setParameter(String, Shader.CurrentTextureType)
     */
    public static final class CurrentTextureType {

        private CurrentTextureType() {
        }
    }
}
