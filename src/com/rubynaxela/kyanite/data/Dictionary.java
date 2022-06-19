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

package com.rubynaxela.kyanite.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * {@code Map}-like interface that provides methods for retrieving the values as values of specific Java types.
 */
public interface Dictionary extends Map<String, Object> {

    /**
     * Returns a {@code boolean} value from this dictionary, to which the specified key is mapped. If this dictionary
     * contains no mapping for the key, or the mapped value is not a {@code boolean} value, an exception will be thrown.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    boolean getBoolean(@NotNull String key);

    /**
     * Associates the specified {@code boolean} value with the specified key in this dictionary.
     * If the dictionary previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified ke
     */
    void setBoolean(@NotNull String key, boolean value);

    /**
     * Returns a {@code Dictionary} value from this dictionary, to which the specified key is mapped. If this dictionary
     * contains no mapping for the key, or the mapped value is not a {@code Dictionary} value, an exception will be thrown.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    Dictionary getDictionary(@NotNull String key);

    /**
     * Associates the specified {@code Dictionary} value with the specified key in this dictionary.
     * If the dictionary previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified ke
     */
    void setDictionary(@NotNull String key, @Nullable Dictionary value);

    /**
     * Returns a {@code double} value from this dictionary, to which the specified key is mapped. If this dictionary
     * contains no mapping for the key, or the mapped value is not a {@code double} value, an exception will be thrown.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    double getDouble(@NotNull String key);

    /**
     * Associates the specified {@code double} value with the specified key in this dictionary.
     * If the dictionary previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified ke
     */
    void setDouble(@NotNull String key, double value);

    /**
     * Returns a {@code float} value from this dictionary, to which the specified key is mapped. If this dictionary
     * contains no mapping for the key, or the mapped value is not a {@code float} value, an exception will be thrown.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    float getFloat(@NotNull String key);

    /**
     * Associates the specified {@code float} value with the specified key in this dictionary.
     * If the dictionary previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified ke
     */
    void setFloat(@NotNull String key, float value);

    /**
     * Returns a {@code int} value from this dictionary, to which the specified key is mapped. If this dictionary
     * contains no mapping for the key, or the mapped value is not a {@code int} value, an exception will be thrown.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    int getInt(@NotNull String key);

    /**
     * Associates the specified {@code int} value with the specified key in this dictionary.
     * If the dictionary previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified ke
     */
    void setInt(@NotNull String key, int value);

    /**
     * Returns a {@code List} value from this dictionary, to which the specified key is mapped. If this dictionary
     * contains no mapping for the key, or the mapped value is not a {@code List} value, an exception will be thrown.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    List<Object> getList(@NotNull String key);

    /**
     * Associates the specified {@code List} value with the specified key in this dictionary.
     * If the dictionary previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified ke
     */
    void setList(@NotNull String key, @Nullable List<Object> value);

    /**
     * Returns a {@code long} value from this dictionary, to which the specified key is mapped. If this dictionary
     * contains no mapping for the key, or the mapped value is not a {@code long} value, an exception will be thrown.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    long getLong(@NotNull String key);

    /**
     * Associates the specified {@code long} value with the specified key in this dictionary.
     * If the dictionary previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified ke
     */
    void setLong(@NotNull String key, long value);

    /**
     * Returns a {@code String} value from this dictionary, to which the specified key is mapped. If this dictionary
     * contains no mapping for the key, or the mapped value is not a {@code String} value, an exception will be thrown.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped
     */
    String getString(@NotNull String key);

    /**
     * Associates the specified {@code String} value with the specified key in this dictionary.
     * If the dictionary previously contained a mapping for the key, the old value is replaced.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified ke
     */
    void setString(@NotNull String key, @Nullable String value);

    /**
     * Returns true if this dictionary contains a mapping for the specified key.
     *
     * @param key the key whose presence in this dictionary is to be tested
     * @return {@code true} if this dictionary contains a mapping for the specified key, {@code false} otherwise
     */
    boolean containsKey(@NotNull String key);

    /**
     * Removes the mapping for the specified key from this dictionary if present.
     *
     * @param key key whose mapping is to be removed from the dictionary
     * @return the previous value associated with key
     */
    Object remove(@NotNull String key);

    /**
     * Creates an object of the specified class and binds the data from this dictionary
     * to it. The target class must have the same structure as the data in the dictionary.
     *
     * @param type the destination class
     * @return an object of the specified class
     */
    <T> T convertTo(@NotNull Class<T> type);

    /**
     * Updates the contents of this dictionary to match the specified object.
     *
     * @param object a data object
     * @param <T>    the data object type
     */
    <T> void updateFrom(@NotNull T object);

    @NotNull
    Map<String, Object> toMap();

    @Override
    default boolean containsKey(@NotNull Object key) {
        return containsKey(key.toString());
    }

    /**
     * @param key unused
     * @deprecated this method should not be overridden; the dictionary
     * concept is not designed to support typeless setting of values
     */
    @Override
    @Deprecated
    @Contract("_ -> fail")
    default Object get(Object key) {
        throw new UnsupportedOperationException("The dictionary does not support typeless getting of values.");
    }

    /**
     * @param key   unused
     * @param value unused
     * @deprecated this method should not be overridden; the dictionary
     * concept is not designed to support typeless setting of values
     */
    @Override
    @Deprecated
    @Contract("_, _ -> fail")
    default Object put(String key, Object value) {
        throw new UnsupportedOperationException("The dictionary does not support typeless setting of values.");
    }

    @Override
    default Object remove(Object key) {
        return remove(key.toString());
    }

    /**
     * @param map unused
     * @deprecated this method should not be overridden; the dictionary
     * concept is not designed to support typeless setting of values
     */
    @Override
    @Deprecated
    @Contract("_ -> fail")
    default void putAll(@NotNull Map<? extends String, ?> map) {
        throw new UnsupportedOperationException("The dictionary does not support typeless setting of values.");
    }
}
