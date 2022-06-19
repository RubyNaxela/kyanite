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

package com.rubynaxela.kyanite.core;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Objects;

/**
 * Native library loader. This class contains the "self-containedness" functionality of Kyanite.
 *
 * @see #loadNativeLibraries() for more information
 */
@SuppressWarnings("deprecation")
public final class SFMLNative extends org.jsfml.internal.SFMLNative {

    /**
     * The substring of the {@code os.name} system property to look for to detect Windows systems.
     */
    public static final String OS_NAME_WINDOWS = "Windows";
    /**
     * The substring of the {@code os.name} system property to look for to detect Linux systems.
     */
    public static final String OS_NAME_LINUX = "Linux";
    /**
     * The substring of the {@code os.name} system property to look for to detect Mac OS X systems.
     */
    public static final String OS_NAME_MACOSX = "Mac OS X";
    private static final String MD5_EXT = ".MD5";
    private static final int MD5_LENGTH = 32;
    private static boolean loaded = false;

    private SFMLNative() {
    }

    private static String readMD5File(@NotNull InputStream in) throws IOException {
        byte[] buffer = new byte[MD5_LENGTH];
        if (in.read(buffer) != MD5_LENGTH) throw new IOException("Error reading MD5 file.");
        return new String(buffer);
    }

    private static String readMD5File(@NotNull Path path) throws IOException {
        try (final InputStream fis = Files.newInputStream(path)) {
            return readMD5File(fis);
        }
    }

    /**
     * Loads the native Kyanite libraries if it has not been done yet. This must be done before any SFML class
     * representations can be used. All affected classes will call this method when they are loaded, so this does not
     * have to be done manually. This method will scan the {@code os.name} and {@code os.arch} system properties and
     * compile a list of libraries to load if the platform is supported. The libraries will then be looked for in the
     * classpath, extracted to the user directory and loaded. The libraries will be extracted to {@code ~/.kyanite}.
     * If the files already exist, their MD5 hashes (which are provided in separate files) will be tested against the
     * ones in the classpath. If the MD5 hashes differ, the existing file in the user directory will be overridden. If
     * loading the native libraries fails, a {@link SFMLError} will be raised with a brief description of what went wrong.
     */
    public static void loadNativeLibraries() {
        if (!loaded) {
            loaded = true;

            final String osName = System.getProperty("os.name");
            final String osArch = System.getProperty("os.arch");

            String arch = null;

            final LinkedList<String> nativeLibs = new LinkedList<>();

            if (osName.contains(OS_NAME_WINDOWS)) {
                switch (osArch) {
                    case "x86" -> arch = "windows_x86";
                    case "amd64" -> arch = "windows_x64";
                }
                nativeLibs.add("libsndfile-1.dll");
                nativeLibs.add("openal32.dll");
                nativeLibs.add("sfml-system-2.dll");
                nativeLibs.add("sfml-window-2.dll");
                nativeLibs.add("sfml-audio-2.dll");
                nativeLibs.add("sfml-graphics-2.dll");
                nativeLibs.add("jsfml.dll");
            } else if (osName.contains(OS_NAME_LINUX)) {
                arch = switch (osArch) {
                    case "x86", "i386" -> "linux_x86";
                    case "amd64" -> "linux_x64";
                    default -> null;
                };
                nativeLibs.add("libsfml-system.so");
                nativeLibs.add("libsfml-window.so");
                nativeLibs.add("libsfml-graphics.so");
                nativeLibs.add("libsfml-audio.so");
                nativeLibs.add("libjsfml.so");
            } else if (osName.contains(OS_NAME_MACOSX)) {
                arch = "macosx_universal";
                nativeLibs.add("libfreetype.dylib");
                nativeLibs.add("libsndfile.dylib");
                nativeLibs.add("libsfml-system.dylib");
                nativeLibs.add("libsfml-window.dylib");
                nativeLibs.add("libsfml-graphics.dylib");
                nativeLibs.add("libsfml-audio.dylib");
                nativeLibs.add("libjsfml.jnilib");
            }

            if (arch == null) throw new SFMLError("Unsupported platform: " + osName + " " + osArch);

            final String nativeResourcePath = KyaniteStorage.KYANITE_BIN_RESOURCE_PATH + arch + "/";
            final Path nativeLibPath = KyaniteStorage.KYANITE_USER_HOME.resolve(arch);

            try {
                Files.createDirectories(nativeLibPath);
            } catch (FileAlreadyExistsException ignored) {
            } catch (IOException e) {
                throw new SFMLError("Failed to create native library directory: " + nativeLibPath, e);
            }

            for (final String lib : nativeLibs) {
                final Path libFile = nativeLibPath.resolve(lib);
                boolean md5Equal = false;
                final String md5FileName = lib + MD5_EXT;
                try (final InputStream md5InputStream =
                             SFMLNative.class.getResourceAsStream(nativeResourcePath + md5FileName)) {

                    final String md5Jar = readMD5File(Objects.requireNonNull(md5InputStream));
                    final Path md5File = nativeLibPath.resolve(md5FileName);

                    if (Files.isRegularFile(libFile) && Files.isRegularFile(md5File))
                        md5Equal = readMD5File(md5File).equals(md5Jar);

                    if (!md5Equal) {
                        try (final OutputStream out = Files.newOutputStream(md5File)) {
                            out.write(md5Jar.getBytes());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (!md5Equal) {
                    try (final InputStream in = SFMLNative.class.getResourceAsStream(nativeResourcePath + lib)) {
                        if (in != null) StreamUtil.streamToFile(in, libFile);
                        else throw new SFMLError("Could not find native library in the classpath: " + nativeResourcePath + lib);
                    } catch (IOException e) {
                        throw new SFMLError("Failed to extract native library: " + libFile, e);
                    }
                }
            }

            nativeLibs.forEach(library -> System.load(nativeLibPath.resolve(library).toAbsolutePath().toString()));

            org.jsfml.internal.SFMLNative.nativeInit();
        }
    }

    /**
     * Ensures that a display is available on this system. If that is not the case, a
     * {@link HeadlessException} is thrown to indicate that the desired Kyanite feature is not available.
     */
    public static void ensureDisplay() {
        if (GraphicsEnvironment.isHeadless())
            throw new HeadlessException("This Kyanite feature is not available in a headless environment");
    }
}
