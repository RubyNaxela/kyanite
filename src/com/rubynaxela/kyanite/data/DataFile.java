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

import java.io.*;
import java.net.URI;
import java.util.Scanner;

/**
 * Representation of a physical file at the specified path. Provides methods for reading and writing plain text.
 *
 * @see File
 */
public class DataFile extends File {

    /**
     * Creates a new {@code DataFile} from the file specified by string pathname.
     *
     * @param pathname the pathname string
     * @throws IOException if the specified path does not exist or does exist but is a directory
     * @see File(String)
     */
    public DataFile(@NotNull String pathname) {
        super(pathname);
        validate();
    }

    /**
     * Creates a new {@code DataFile} from the file specified by abstract pathname.
     *
     * @param file the abstract pathname
     * @throws IOException if the specified path does not exist or does exist but is a directory
     * @see File(String)
     */
    public DataFile(@NotNull File file) {
        super(file.getPath());
        validate();
    }

    /**
     * Creates a new {@code DataFile} from the file specified by parent and child pathnames.
     *
     * @param parent the parent pathname string
     * @param child  the child pathname string
     * @throws IOException if the specified path does not exist or does exist but is a directory
     * @see File(String, String)
     */
    public DataFile(String parent, @NotNull String child) {
        super(parent, child);
        validate();
    }

    /**
     * Creates a new {@code DataFile} from the file specified by parent abstract pathname and child string pathname.
     *
     * @param parent the parent abstract pathname
     * @param child  the child pathname string
     * @throws IOException if the specified path does not exist or does exist but is a directory
     * @see File(File, String)
     */
    public DataFile(File parent, @NotNull String child) {
        super(parent, child);
        validate();
    }

    /**
     * Creates a new {@code DataFile} from the file specified by URI.
     *
     * @param uri the pathname string
     * @throws IOException if the specified path does not exist or does exist but is a directory
     * @see File(URI)
     */
    public DataFile(@NotNull URI uri) {
        super(uri);
        validate();
    }

    /**
     * Returns a new {@link Scanner} that produces values scanned from this file. Bytes from
     * the file are converted characters using the underlying platform's default charset.
     *
     * @return a {@link Scanner} for this file
     */
    @NotNull
    @Contract(pure = true)
    public Scanner scanner() {
        try {
            return new Scanner(this);
        } catch (FileNotFoundException e) {
            throw new IOException("File " + this + " not found. It was available during initialization of this DataFile " +
                                  "instance thus it has to have been removed or changed during the runtime.", e);
        }
    }

    /**
     * Creates a BufferedWriter given this to write, using the platform's
     * {@linkplain java.nio.charset.Charset#defaultCharset() default charset}
     * and a default-sized output buffer. Returned reader is not autocloseable.
     *
     * @return a BufferedWriter given this file to write
     */
    @NotNull
    @Contract(pure = true)
    public BufferedWriter writer(@NotNull WriteMode mode) {
        try {
            return new BufferedWriter(new FileWriter(this, mode == WriteMode.APPEND));
        } catch (java.io.IOException e) {
            throw new IOException("File " + this + " not found. It was available during initialization of this DataFile " +
                                  "instance thus it has to have been removed or changed during the runtime.", e);
        }
    }

    /**
     * Reads and returns contents of this file.
     *
     * @return the contents of this file
     */
    @NotNull
    public String read() {
        final StringBuilder builder = new StringBuilder();
        final Scanner scanner = scanner();
        while (scanner.hasNextLine()) builder.append(scanner.nextLine()).append("\n");
        return builder.substring(0, builder.length() - 1);
    }

    /**
     * Overwrites the contents of this file with the specified string. If the argument is {@code null}, the file is cleared.
     *
     * @param contents the new contents of this file
     * @throws IOException if the file exists but is a directory rather than a regular file, does
     *                     not exist but cannot be created, or cannot be opened for any other reason
     */
    public void write(@Nullable String contents) {
        try {
            final Writer writer = writer(WriteMode.OVERWRITE);
            writer.write(contents != null ? contents : "");
            writer.close();
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Appends the specified string at the end of this file. If the argument is {@code null}, the four
     * characters {@code "null"} are appended. Warning: use of this method is not optimal for multiple
     * append operations as a new writer is created on every call. For such cases, it is recommended
     * to create a writer using the {@link #writer} method and performing the append operation with it.
     *
     * @param string the new contents of this file
     * @throws IOException if the file exists but is a directory rather than a regular file, does
     *                     not exist but cannot be created, or cannot be opened for any other reason
     */
    public void append(@Nullable String string) {
        if (string == null || string.isEmpty()) return;
        try {
            final Writer writer = writer(WriteMode.APPEND);
            writer.write(string);
            writer.close();
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    private void validate() {
        if (!this.exists()) throw new IOException("File " + this + " not found");
        if (this.isDirectory()) throw new IOException("Path " + this + " leads to a directory rather than a file");
    }

    public enum WriteMode {
        OVERWRITE, APPEND
    }
}
