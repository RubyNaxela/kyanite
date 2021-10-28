package com.rubynaxela.kyanite.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * {@code Map}-like class designed to hold JSON data. Provides methods for retrieving
 * the {@code Object} values as values of specific Java types. Does not support modifications.
 */
public class Dictionary {

    protected final Map<String, Object> data;

    /**
     * Constructs a {@code MapObject} with the specified data set.
     *
     * @param data the data set to be stored by this {@code MapObject}
     */
    @JsonCreator
    public Dictionary(@NotNull Map<String, Object> data) {
        this.data = data;
    }

    /**
     * @param key a JSON key
     * @return the {@code boolean} value associated with the key
     * @throws JSONParseException if the value is not a boolean or the
     *                            {@code "true"} or {@code "false"} string.
     */
    public boolean getBoolean(@NotNull String key) throws JSONParseException {
        try {
            return (Boolean) data.get(key);
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) a boolean");
        }
    }

    /**
     * @param key a JSON key
     * @return the {@code Dictionary} value associated with the key
     */
    @SuppressWarnings("unchecked")
    public Dictionary getDictionary(@NotNull String key) {
        try {
            return new Dictionary((Map<String, Object>) data.get(key));
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not a dictionary object");
        }
    }

    /**
     * @param key a JSON key
     * @return the {@code double} value associated with the key
     * @throws JSONParseException if the value is not a double and cannot be converted
     *                            to one by {@link Double#parseDouble(String)}
     */
    public double getDouble(@NotNull String key) throws JSONParseException {
        try {
            return ((BigDecimal) data.get(key)).doubleValue();
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) a double");
        }
    }

    /**
     * @param key a JSON key
     * @return the {@code float} value associated with the key
     * @throws JSONParseException if the value is not a float and cannot be converted
     *                            to one by {@link Float#parseFloat(String)}
     */
    public float getFloat(@NotNull String key) throws JSONParseException {
        try {
            return ((BigDecimal) data.get(key)).floatValue();
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) a float");
        }
    }

    /**
     * @param key a JSON key
     * @return the {@code int} value associated with the key
     * @throws JSONParseException if the value is not an integer and cannot be converted
     *                            to one by {@link Integer#parseInt(String)}
     */
    public int getInt(@NotNull String key) throws JSONParseException {
        try {
            return ((BigInteger) data.get(key)).intValue();
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) an int");
        }
    }

    /**
     * @param key a JSON key
     * @return the {@code List} associated with the key
     * @throws JSONParseException if the value is not a list
     */
    @SuppressWarnings("unchecked")
    public List<Object> getList(@NotNull String key) {
        try {
            final List<Object> list = (List<Object>) data.get(key);
            final List<Object> result = new ArrayList<>();
            list.forEach(value -> {
                if (value instanceof HashMap) result.add(new Dictionary((HashMap<String, Object>) value));
                else result.add(value);
            });
            return result;
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not a list");
        }
    }

    /**
     * @param key a JSON key
     * @return the {@code long} value associated with the key
     * @throws JSONParseException if the value is not a long and cannot be converted
     *                            to one by {@link Long#parseLong(String)}
     */
    public long getLong(@NotNull String key) throws JSONParseException {
        try {
            return ((BigInteger) data.get(key)).longValue();
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) a long");
        }
    }

    /**
     * @param key a JSON key
     * @return the {@code String} value associated with the key
     * @throws JSONParseException if the value is not a string
     */
    public String getString(@NotNull String key) throws JSONParseException {
        try {
            return (String) data.get(key);
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not a string");
        }
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * @param key a JSON key
     * @return whether the key exists in this data asset
     */
    public boolean containsKey(@NotNull String key) {
        return data.containsKey(key);
    }

    /**
     * @param value a value to test for
     * @return whether one or more of this map's keys map to the specified value
     */
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @NotNull
    public Set<String> keySet() {
        return data.keySet();
    }

    @NotNull
    public Collection<Object> values() {
        return data.values();
    }

    @NotNull
    public Set<Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
    }

    @Override
    public String toString() {
        return new JSONObject(data).toString(2);
    }

    private static class JSONParseException extends JSONException {

        public JSONParseException(@NotNull String message) {
            super(message);
        }
    }
}
