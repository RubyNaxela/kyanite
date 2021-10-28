package com.rubynaxela.kyanite.game.assets;

import com.rubynaxela.kyanite.util.AssetId;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a handy assets storage. Assets are registered and retrieved with {@code String} identifiers.
 *
 * @apiNote An identifier can be any text, however it is good to keep the identifiers
 * organized, compliant with a consistent convention. For instance, {@code kyanite:texture.flowers.purple}
 * makes up a good identifier, however {@code txt_flower_13} does not.
 */
public final class AssetsBundle {

    private final Map<String, Asset> assets = new HashMap<>();

    /**
     * Registers an {@link Asset} in the bundle.
     *
     * @param id    the identifier of this asset
     * @param asset the asset to be stored
     */
    public <T extends Asset> void register(@NotNull @AssetId String id, T asset) {
        assets.put(id, asset);
    }

    /**
     * Finds an {@link Asset} by its identifier.
     *
     * @param id the identifier of this asset
     * @return an {@link Asset} object
     */
    @SuppressWarnings("unchecked")
    public <T extends Asset> T get(@NotNull @AssetId String id) {
        if (!assets.containsKey(id)) throw new NullPointerException("Asset of ID " + id + " does not exist");
        return (T) assets.get(id);
    }
}
