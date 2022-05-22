package com.rubynaxela.kyanite.system;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * If the application is running on macOS, it must be started with the -XstartOnFirstThread JVM option.
 * Otherwise, the application thread will not be allowed to create a window. This is an SWT limitation.
 */
public final class FirstThreadTool {

    private FirstThreadTool() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
