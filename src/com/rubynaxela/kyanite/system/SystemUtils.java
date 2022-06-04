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

package com.rubynaxela.kyanite.system;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generic OS &amp; IO related functionalities.
 */
public final class SystemUtils {

    private SystemUtils() {
    }

    /**
     * Reboots the JVM on Mac OS X with VM option -XstartOnFirstThread if the VM wasn't started with it already.
     *
     * @param args arguments for the new instance of the application
     */
    public static void restartIfNecessary(String[] args) {

        final String osName = System.getProperty("os.name");
        if ((!osName.startsWith("Mac")) && (!osName.startsWith("Darwin"))) return;

        final String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        final String env = System.getenv("JAVA_STARTED_ON_FIRST_THREAD_" + pid);

        if ("1".equals(env)) return;

        final List<String> jvmArgs = new ArrayList<>(128);
        final String separator = System.getProperty("file.separator");
        jvmArgs.add(System.getProperty("java.home") + separator + "bin" + separator + "java");
        jvmArgs.add("-XstartOnFirstThread");
        jvmArgs.addAll(ManagementFactory.getRuntimeMXBean().getInputArguments());
        jvmArgs.add("-cp");
        jvmArgs.add(System.getProperty("java.class.path"));
        jvmArgs.add(System.getenv("JAVA_MAIN_CLASS_" + pid));
        jvmArgs.addAll(Arrays.asList(args));

        try {
            final ProcessBuilder processBuilder = new ProcessBuilder(jvmArgs);
            processBuilder.redirectErrorStream(true);
            final Process process = processBuilder.start();

            final BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = br.readLine()) != null) System.out.println(line);

            System.exit(process.waitFor());
        } catch (java.io.IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    /**
     * Gets the pathname of the JAR file containing the specified class, or
     * {@code c.getName() + "!"} if that class is not contained in a JAR file.
     *
     * @return the pathname of this JAR file, {@code c.getName() + "!"} if the code is not running from a JAR file
     */
    public static String getJarPath(@NotNull Class<?> c) {
        try {
            return c.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (URISyntaxException ignored) {
            return "";
        }
    }

    /**
     * Gets an {@link InputStream} from a specified file from the specified class resources.
     *
     * @param pathname a path relative to the root of the specified class module
     * @return an input stream from the specified file
     */
    @NotNull
    @Contract(pure = true)
    public static InputStream internalFile(@NotNull Class<?> c, @NotNull String pathname) throws FileNotFoundException {
        if (!pathname.startsWith("/") && !pathname.startsWith("\\")) pathname = File.separator + pathname;
        final InputStream stream = c.getResourceAsStream(pathname);
        if (stream == null) throw new FileNotFoundException("File not found inside the jar file: " + pathname);
        return stream;
    }
}
