package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.system.IOException;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.util.Objects;

/**
 * An external asset. This tagging interface groups assets allowing them to be stored
 * in an {@link AssetsBundle}. Kyanite provides several basic types of assets, but the
 * programmer should feel free to implement this interface in their own asset types.
 */
public interface Asset {

    /**
     * Finds a resource file with a given path. The path must be relative to the module source root
     * package. For instance, if the file is located at {@code project_name/src/res/sound/step1.ogg},
     * then the specified path  must be {@code "res/sound/step1.ogg"}. Needs a reference to an object
     * of any class that is in the same module as the desired resource.
     *
     * @param reference object of any class from the same module as the desired resource
     * @param path      the path to the desired file (relative to the module source root package)
     * @return an {@link java.io.InputStream} from the specified file
     * @throws IOException if no resource with this name is found, or access to the resource
     *                     is denied by the security manager or {@code path} is {@code null}
     */
    @NotNull
    @Contract(pure = true, value = "_, _ -> new")
    static InputStream internal(@NotNull Object reference, @NotNull String path) {
        try {
            return Objects.requireNonNull(reference.getClass().getResourceAsStream(!path.startsWith("/") ? "/" : "" + path));
        } catch (NullPointerException e) {
            throw new IOException(e);
        }
    }
}
