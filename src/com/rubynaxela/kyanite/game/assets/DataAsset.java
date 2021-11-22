package com.rubynaxela.kyanite.game.assets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.rubynaxela.kyanite.system.IOException;
import com.rubynaxela.kyanite.util.Dictionary;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;
import java.util.Scanner;

/**
 * This class is designed to store data loaded from an external file as an asset. Currently the only supported format is JSON.
 */
@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
public class DataAsset extends Dictionary implements Asset {

    private final File dataFile;
    private final InputStream inputStream;

    private DataAsset(@NotNull Map<String, Object> data) {
        super(data);
        this.dataFile = null;
        this.inputStream = null;
    }

    /**
     * Constructs a data asset from the source JSON file.
     *
     * @param file the data source file
     */
    public DataAsset(@NotNull File file) {
        super(new JSONObject(new ReadFile(file).toString()).toMap());
        this.dataFile = file;
        this.inputStream = null;
    }

    /**
     * Constructs a data asset from the source JSON file specified by the path.
     *
     * @param path path to the data source
     */
    public DataAsset(@NotNull String path) {
        this(new File(path));
    }

    /**
     * Constructs a data asset from the source JSON file specified by the path.
     *
     * @param path path to the data source
     */
    public DataAsset(@NotNull Path path) {
        this(path.toFile());
    }

    /**
     * Constructs a data asset from the source JSON file.
     *
     * @param stream the data source file
     */
    public DataAsset(@NotNull InputStream stream) {
        super(new JSONObject(stream).toMap());
        this.dataFile = null;
        this.inputStream = stream;
    }

    /**
     * Creates an object of the specified class and binds the data from the assigned file to it.
     * The target class must have the same structure as the data in the source JSON file.
     *
     * @param type the destination class
     * @return an object of the specified class
     */
    @Override
    public <T> T convertTo(@NotNull Class<T> type) {
        if (dataFile != null) return new JSONParser(dataFile).as(type);
        else return new JSONParser(inputStream).as(type);
    }

    private static class ReadFile extends File {

        public ReadFile(@NotNull File file) {
            super(file.getAbsolutePath());
        }

        @Override
        public String toString() {
            try {
                final Scanner scanner = new Scanner(this);
                final StringBuilder fileContents = new StringBuilder();
                while (scanner.hasNextLine()) fileContents.append(scanner.nextLine());
                return fileContents.toString();
            } catch (FileNotFoundException e) {
                throw new IOException(e);
            }
        }
    }
}