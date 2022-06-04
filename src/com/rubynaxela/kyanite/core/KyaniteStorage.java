package com.rubynaxela.kyanite.core;

import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("ResultOfMethodCallIgnored")
public final class KyaniteStorage {

    static final Path KYANITE_USER_HOME = Paths.get(System.getProperty("user.home"), ".kyanite");
    static final String KYANITE_BIN_RESOURCE_PATH = "/bin/";
    static final String KYANITE_TEMP_PATH = "/temp/";

    static {
        try {
            final File tmpDir = new File(KYANITE_USER_HOME + KYANITE_TEMP_PATH);
            tmpDir.mkdirs();
            Arrays.stream(Objects.requireNonNull(tmpDir.listFiles())).forEach(File::delete);
        } catch (NullPointerException e) {
            throw new KyaniteException("Could not create/access the temporary files storage");
        }
    }

    private KyaniteStorage() {
    }

    /**
     * Creates a temporary file and returns. The file is stored inside of the
     * Kyanite's home storage. The name of the file is a random {@link UUID}.
     *
     * @return the newly created temporary file
     * @throws IOException if the file could not be created
     */
    @Contract("-> new")
    public static TemporaryFile createTemporaryFile() {
        try {
            return new TemporaryFile();
        } catch (java.io.IOException e) {
            throw new IOException(e);
        }
    }

    /**
     * Represents a temporary file stored inside of the Kyanite's home storage. This class is intended to be used when
     * there is data that is not inside a disk file, but a file is needed for certain operation to work. Temporary files
     * are deleted automatically by the garbage collector, as well as by the class loader when this class is loaded.
     */
    public static class TemporaryFile {

        private final File file;

        private TemporaryFile() throws java.io.IOException {
            file = new File(KYANITE_USER_HOME + KYANITE_TEMP_PATH + UUID.randomUUID());
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        /**
         * Gets the path to this temporary file.
         *
         * @return path to this file file
         */
        @NotNull
        @Contract(pure = true)
        public Path getPath() {
            return file.toPath();
        }

        /**
         * Returns a {@link FileOutputStream} from this temporary file.
         *
         * @return a {@code FileOutputStream} from this file
         * @throws IOException if the file has been deleted by another process or user
         */
        public FileOutputStream getOutputStream() {
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                throw new IOException(e.getMessage());
            }
        }

        @SuppressWarnings("ResultOfMethodCallIgnored")
        private void delete() {
            file.delete();
        }

        @SuppressWarnings("deprecation")
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            delete();
        }
    }
}
