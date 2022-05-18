package com.rubynaxela.kyanite.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.rubynaxela.kyanite.system.JSONParseException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * {@code Map}-like class designed to hold JSON data. Provides methods
 * for reading and writing the values as values of specific Java types.
 */
public class JSONDictonary implements Dictionary {

    protected final Map<String, Object> data;

    /**
     * Constructs a {@code MapObject} with the specified data set.
     *
     * @param data the data set to be stored by this {@code MapObject}
     */
    @JsonCreator
    public JSONDictonary(@NotNull Map<String, Object> data) {
        this.data = data;
    }

    /**
     * @param key a JSON key
     * @return the {@code boolean} value associated with the key
     * @throws JSONParseException if the value is not a boolean or the
     *                            {@code "true"} or {@code "false"} string.
     */
    @Override
    public boolean getBoolean(@NotNull String key) throws JSONParseException {
        try {
            return (Boolean) data.get(key);
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) a boolean");
        }
    }

    /**
     * Associates the specified {@code boolean} value with the specified key in this dictionary. If the dictionary
     * previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key   a JSON key
     * @param value value to be associated with the specified key
     */
    @Override
    public void setBoolean(@NotNull String key, boolean value) {
        data.put(key, value);
    }

    /**
     * Returns the {@code Dictionary} associated with the specified key. The returned dictionary
     * is mutable, however any changes made to it will not affect this {@code Dictionary}.
     *
     * @param key a JSON key
     * @return the {@code Dictionary} value associated with the key
     */
    @SuppressWarnings("unchecked")
    @Override
    public Dictionary getDictionary(@NotNull String key) {
        try {
            return new JSONDictonary((Map<String, Object>) data.get(key));
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not a dictionary object");
        }
    }

    /**
     * Associates the specified {@code Dictionary} value with the specified key in this dictionary. If the
     * dictionary previously contained a mapping for the key, the old value is replaced by the specified value.
     * The value that is stored in this dictionary is not linked with the specified {@code Dictionary}, so
     * any changes made to that {@code Dictionary} after it has been added, will not affect this dictionary.
     *
     * @param key   a JSON key
     * @param value value to be associated with the specified key
     */
    @Override
    public void setDictionary(@NotNull String key, @Nullable Dictionary value) {
        data.put(key, value != null ? value.toMap() : null);
    }

    /**
     * @param key a JSON key
     * @return the {@code double} value associated with the key
     * @throws JSONParseException if the value is not a double and cannot be converted
     *                            to one by {@link Double#parseDouble(String)}
     */
    @Override
    public double getDouble(@NotNull String key) throws JSONParseException {
        try {
            final Object value = data.get(key);
            if (value instanceof final Float floatValue) return floatValue.doubleValue();
            if (value instanceof final Double doubleValue) return doubleValue;
            return ((BigDecimal) value).doubleValue();
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) a double");
        }
    }

    /**
     * Associates the specified {@code double} value with the specified key in this dictionary. If the dictionary
     * previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key   a JSON key
     * @param value value to be associated with the specified key
     */
    @Override
    public void setDouble(@NotNull String key, double value) {
        data.put(key, value);
    }

    /**
     * @param key a JSON key
     * @return the {@code float} value associated with the key
     * @throws JSONParseException if the value is not a float and cannot be converted
     *                            to one by {@link Float#parseFloat(String)}
     */
    @Override
    public float getFloat(@NotNull String key) throws JSONParseException {
        try {
            final Object value = data.get(key);
            if (value instanceof final Float floatValue) return floatValue;
            if (value instanceof final Double doubleValue) return doubleValue.floatValue();
            return ((BigDecimal) value).floatValue();
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) a float");
        }
    }

    /**
     * Associates the specified {@code float} value with the specified key in this dictionary. If the dictionary
     * previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key   a JSON key
     * @param value value to be associated with the specified key
     */
    @Override
    public void setFloat(@NotNull String key, float value) {
        data.put(key, value);
    }

    /**
     * @param key a JSON key
     * @return the {@code int} value associated with the key
     * @throws JSONParseException if the value is not an integer and cannot be converted
     *                            to one by {@link Integer#parseInt(String)}
     */
    @Override
    public int getInt(@NotNull String key) throws JSONParseException {
        try {
            final Object value = data.get(key);
            if (value instanceof final Integer intValue) return intValue;
            return ((BigInteger) value).intValue();
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) an int");
        }
    }

    /**
     * Associates the specified {@code int} value with the specified key in this dictionary. If the dictionary
     * previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key   a JSON key
     * @param value value to be associated with the specified key
     */
    @Override
    public void setInt(@NotNull String key, int value) {
        data.put(key, value);
    }

    /**
     * Returns the {@code List} associated with the specified key. The returned list
     * is mutable, however any changes made to it will not affect this {@code Dictionary}.
     *
     * @param key a JSON key
     * @return the {@code List} associated with the key
     * @throws JSONParseException if the value is not a list
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object> getList(@NotNull String key) {
        try {
            final List<Object> list = (List<Object>) data.get(key);
            final List<Object> result = new ArrayList<>();
            list.forEach(value -> {
                if (value instanceof HashMap) result.add(new JSONDictonary((HashMap<String, Object>) value));
                else result.add(value);
            });
            return result;
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not a list");
        }
    }

    /**
     * Associates the specified list with the specified key in this dictionary. If the dictionary
     * previously contained a mapping for the key, the old value is replaced by the specified list.
     * The value that is stored in this dictionary is not linked with the specified {@code List}, so
     * any changes made to that {@code List} after it has been added, will not affect this dictionary.
     *
     * @param key   a JSON key
     * @param value value to be associated with the specified key
     */
    @Override
    public void setList(@NotNull String key, @Nullable List<Object> value) {
        if (value == null) data.put(key, null);
        else if (isOfValidJSONTypes(value)) data.put(key, new ArrayList<>(value));
        else throw new IllegalArgumentException("The specified list contains values of invalid JSON types");
    }

    /**
     * @param key a JSON key
     * @return the {@code long} value associated with the key
     * @throws JSONParseException if the value is not a long and cannot be converted
     *                            to one by {@link Long#parseLong(String)}
     */
    @Override
    public long getLong(@NotNull String key) throws JSONParseException {
        try {
            return ((BigInteger) data.get(key)).longValue();
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not (and cannot be converted to) a long");
        }
    }

    /**
     * Associates the specified {@code long} value with the specified key in this dictionary. If the dictionary
     * previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key   a JSON key
     * @param value value to be associated with the specified key
     */
    @Override
    public void setLong(@NotNull String key, long value) {
        data.put(key, value);
    }

    /**
     * @param key a JSON key
     * @return the {@code String} value associated with the key
     * @throws JSONParseException if the value is not a string
     */
    @Override
    public String getString(@NotNull String key) throws JSONParseException {
        try {
            return (String) data.get(key);
        } catch (JSONException | ClassCastException e) {
            throw new JSONParseException("\"" + key + "\" is not is not a string");
        }
    }

    /**
     * Associates the specified {@code String} value with the specified key in this dictionary. If the dictionary
     * previously contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key   a JSON key
     * @param value value to be associated with the specified key
     */
    @Override
    public void setString(@NotNull String key, @Nullable String value) {
        data.put(key, value);
    }

    /**
     * @param key a JSON key
     * @return whether the key exists in this data asset
     */
    @Override
    public boolean containsKey(@NotNull String key) {
        return data.containsKey(key);
    }

    /**
     * Removes the mapping for a key from this dictionary if it is present. More formally, if this dictionary contains
     * a mapping from key {@code k} to a value such that {@code Objects.equals(key, k)}, that mapping is removed.
     *
     * @param key key whose mapping is to be removed from the dictionary
     * @return the previous value associated with {@code key}, or {@code null} if there was no mapping for {@code key}
     */
    @Override
    public Object remove(@NotNull String key) {
        return data.remove(key);
    }

    /**
     * Creates an object of the specified class and binds the data from the assigned file to it.
     * The target class must have the same structure as the data in the source JSON file.
     *
     * @param <T>  the destination class
     * @param type the destination class object
     * @return an object of the specified class
     */
    @Override
    public <T> T convertTo(@NotNull Class<T> type) {
        return new JSONParser(new JSONObject(data).toString()).as(type);
    }

    /**
     * Updates this dictionary to match the structure and data of
     * the specified object, overwriting all data it contained before.
     *
     * @param object an object to update this dictionary with
     * @param <T>    the object type
     */
    @Override
    public <T> void updateFrom(@NotNull T object) {
        try {
            final Map<String, Object> newData = new JSONObject(new ObjectMapper().writeValueAsString(object)).toMap();
            data.clear();
            data.putAll(newData);
        } catch (JsonProcessingException e) {
            throw new JSONProcessingException("Update failed: " + e.getMessage());
        }
    }

    @Override
    @NotNull
    public Map<String, Object> toMap() {
        return data;
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * @param value a value to test for
     * @return whether one or more of this map's keys map to the specified value
     */
    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    /**
     * Removes all of the mappings from this map (optional operation). The dictionary will be empty after this call returns.
     */
    @Override
    public void clear() {
        data.clear();
    }

    /**
     * Returns a {@link Set} view of the keys contained in this dictionary. The set is backed
     * by an internal map, so changes to the map are reflected in the set, and vice-versa.
     *
     * @return a set view of the keys contained in this dictionary
     */
    @Override
    @NotNull
    public Set<String> keySet() {
        return data.keySet();
    }

    /**
     * Returns a {@link Collection} view of the values contained in this dictionary. The collection is
     * backed by an internal map, so changes to the map are reflected in the collection, and vice-versa.
     *
     * @return a collection view of the values contained in this dictionary
     */
    @Override
    @NotNull
    public Collection<Object> values() {
        return data.values();
    }

    /**
     * Returns a {@link Set} view of the mappings contained in this dictionary. The set is
     * backed by an internal map, so changes to the map are reflected in the set, and vice-versa.
     *
     * @return a set view of the mappings contained in this dictionary
     */
    @Override
    @NotNull
    public Set<Map.Entry<String, Object>> entrySet() {
        return data.entrySet();
    }

    /**
     * @return JSON representation of this dictionary
     */
    @Override
    public String toString() {
        return new JSONObject(data).toString(2);
    }

    private boolean isValidJSONType(@Nullable Object value) {
        return value == null || value instanceof Boolean || value instanceof Dictionary ||
               value instanceof Double || value instanceof Float || value instanceof Integer ||
               value instanceof List || value instanceof Long || value instanceof String;
    }

    @SuppressWarnings("rawtypes")
    private boolean isOfValidJSONTypes(@NotNull List<?> list) {
        for (final Object value : list) {
            if (value instanceof final List listValue && !isOfValidJSONTypes(listValue)) return false;
            else if (!isValidJSONType(value)) return false;
        }
        return true;
    }

    protected static final class JSONParser {

        private static final ObjectReader objectReader = new ObjectMapper().reader();
        private File inputFile;
        private InputStream inputStream;
        private String json;

        public JSONParser(@NotNull File inputFile) {
            this.inputFile = inputFile;
        }

        public JSONParser(@NotNull InputStream stream) {
            this.inputStream = stream;
        }

        public JSONParser(@NotNull String json) {
            this.json = json;
        }

        public <T> T as(@NotNull Class<T> type) {
            try {
                if (inputFile != null) return objectReader.readValue(inputFile, type);
                else if (inputStream != null) return objectReader.readValue(inputStream, type);
                else return objectReader.readValue(json, type);
            } catch (Exception e) {
                throw new RuntimeException("An error occurred while binding data to a " + type + " object", e);
            }
        }
    }
}
