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

import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.io.File;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Representation of a physical JSON-formatted file at the specified path.
 * Provides methods for reading and writing the values as values of specific Java types.
 */
public class JSONDataFile extends DataFile implements Dictionary {

    private final JSONDictonary data;

    /**
     * Creates a new {@code DataFile} from the file specified by string pathname.
     *
     * @param pathname the pathname string
     * @throws IOException if the specified file does not exist
     * @see File#File(String)
     */
    public JSONDataFile(@NotNull String pathname) {
        super(pathname);
        data = new JSONDictonary(new JSONObject(read()).toMap());
    }

    /**
     * Creates a new {@code DataFile} from the file specified by abstract pathname.
     *
     * @param file the abstract pathname
     * @throws IOException if the specified file does not exist
     * @see File#File(String)
     */
    public JSONDataFile(@NotNull File file) {
        super(file);
        data = new JSONDictonary(new JSONObject(read()).toMap());
    }

    /**
     * Creates a new {@code DataFile} from the file specified by parent and child pathnames.
     *
     * @param parent the parent pathname string
     * @param child  the child pathname string
     * @throws IOException if the specified file does not exist
     * @see File#File(String, String)
     */
    public JSONDataFile(String parent, @NotNull String child) {
        super(parent, child);
        data = new JSONDictonary(new JSONObject(read()).toMap());
    }

    /**
     * Creates a new {@code DataFile} from the file specified by parent abstract pathname and child string pathname.
     *
     * @param parent the parent abstract pathname
     * @param child  the child pathname string
     * @throws IOException if the specified file does not exist
     * @see File#File(File, String)
     */
    public JSONDataFile(File parent, @NotNull String child) {
        super(parent, child);
        data = new JSONDictonary(new JSONObject(read()).toMap());
    }

    /**
     * Creates a new {@code DataFile} from the file specified by URI.
     *
     * @param uri the pathname string
     * @throws IOException if the specified file does not exist
     * @see File#File(URI)
     */
    public JSONDataFile(@NotNull URI uri) {
        super(uri);
        data = new JSONDictonary(new JSONObject(read()).toMap());
    }

    private void updateFile() {
        write(data.toString());
    }

    @Override
    public boolean getBoolean(@NotNull String key) {
        return data.getBoolean(key);
    }

    @Override
    public void setBoolean(@NotNull String key, boolean value) {
        data.setBoolean(key, value);
        updateFile();
    }

    @Override
    public Dictionary getDictionary(@NotNull String key) {
        return data.getDictionary(key);
    }

    @Override
    public void setDictionary(@NotNull String key, @Nullable Dictionary value) {
        data.setDictionary(key, value);
        updateFile();
    }

    @Override
    public double getDouble(@NotNull String key) {
        return data.getDouble(key);
    }

    @Override
    public void setDouble(@NotNull String key, double value) {
        data.setDouble(key, value);
        updateFile();
    }

    @Override
    public float getFloat(@NotNull String key) {
        return data.getFloat(key);
    }

    @Override
    public void setFloat(@NotNull String key, float value) {
        data.setFloat(key, value);
        updateFile();
    }

    @Override
    public int getInt(@NotNull String key) {
        return data.getInt(key);
    }

    @Override
    public void setInt(@NotNull String key, int value) {
        data.setInt(key, value);
        updateFile();
    }

    @Override
    public List<Object> getList(@NotNull String key) {
        return data.getList(key);
    }

    @Override
    public void setList(@NotNull String key, @Nullable List<Object> value) {
        data.setList(key, value);
        updateFile();
    }

    @Override
    public long getLong(@NotNull String key) {
        return data.getLong(key);
    }

    @Override
    public void setLong(@NotNull String key, long value) {
        data.setLong(key, value);
        updateFile();
    }

    @Override
    public String getString(@NotNull String key) {
        return data.getString(key);
    }

    @Override
    public void setString(@NotNull String key, @Nullable String value) {
        data.setString(key, value);
        updateFile();
    }

    @Override
    public boolean containsKey(@NotNull String key) {
        return data.containsKey(key);
    }

    @Override
    public Object remove(@NotNull String key) {
        updateFile();
        return data.remove(key);
    }

    @Override
    public <T> T convertTo(@NotNull Class<T> type) {
        return data.convertTo(type);
    }

    @Override
    @Contract(mutates = "this")
    public <T> void updateFrom(@NotNull T object) {
        data.updateFrom(object);
        updateFile();
    }

    @Override
    @NotNull
    public Map<String, Object> toMap() {
        return data.toMap();
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public void clear() {
        data.clear();
        updateFile();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @NotNull
    @Override
    public Collection<Object> values() {
        return data.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, Object>> entrySet() {
        return data.entrySet();
    }
}
