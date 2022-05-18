package com.rubynaxela.kyanite.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * {@code Map}-like interface that provides methods for retrieving the values as values of specific Java types.
 */
public interface Dictionary extends Map<String, Object> {


    boolean getBoolean(@NotNull String key);

    void setBoolean(@NotNull String key, boolean value);

    Dictionary getDictionary(@NotNull String key);

    void setDictionary(@NotNull String key, @Nullable Dictionary value);

    double getDouble(@NotNull String key);

    void setDouble(@NotNull String key, double value);

    float getFloat(@NotNull String key);

    void setFloat(@NotNull String key, float value);

    int getInt(@NotNull String key);

    void setInt(@NotNull String key, int value);

    List<Object> getList(@NotNull String key);

    void setList(@NotNull String key, @Nullable List<Object> value);

    long getLong(@NotNull String key);

    void setLong(@NotNull String key, long value);

    String getString(@NotNull String key);

    void setString(@NotNull String key, @Nullable String value);

    boolean containsKey(@NotNull String key);

    Object remove(@NotNull String key);

    <T> T convertTo(@NotNull Class<T> type);

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
