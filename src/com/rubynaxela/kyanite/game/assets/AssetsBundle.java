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

package com.rubynaxela.kyanite.game.assets;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rubynaxela.kyanite.game.GameContext;
import com.rubynaxela.kyanite.graphics.*;
import com.rubynaxela.kyanite.util.AssetId;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Provides a handy {@link TreeMap}-based assets storage. Assets are registered and retrieved with {@code String} identifiers.
 * An identifier can be any text, however it is good to keep the identifiers organized, compliant with a consistent convention.
 * For instance, {@code kyanite:texture.flowers.purple} makes up a good identifier, however {@code txt_flower_13} does not.
 */
public final class AssetsBundle {

    private final Map<String, Asset> assets = new TreeMap<>();

    /**
     * Registers assets from an asset index file.
     *
     * @param file the asset index file
     */
    public void registerFromIndex(@NotNull File file) {
        new DataAsset(file).convertTo(AssetIndex.class).registerAll(this);
    }

    /**
     * Registers assets from an asset index file.
     *
     * @param pathname path to the asset index file
     */
    public void registerFromIndex(@NotNull String pathname) {
        registerFromIndex(new File(pathname));
    }

    /**
     * Registers assets from an asset index file.
     *
     * @param path path to the asset index file
     */
    public void registerFromIndex(@NotNull Path path) {
        registerFromIndex(path.toFile());
    }

    /**
     * Registers an {@link Asset} in the bundle.
     *
     * @param <T>   the asset type class
     * @param id    the identifier of this asset
     * @param asset the asset to be stored
     */
    public <T extends Asset> void register(@NotNull @AssetId String id, T asset) {
        assets.put(id, asset);
        if (asset instanceof final Sound sound) GameContext.getInstance().getAudioHandler().globalSounds.add(sound);
    }

    /**
     * Finds an {@link Asset} by its identifier.
     *
     * @param <T> the asset type class
     * @param id  the identifier of this asset
     * @return an {@link Asset} object
     * @throws NullPointerException when an asset of the specified id does not exist
     *                              or was attempted to be used before being registered
     * @throws ClassCastException   when the actual asset type is different than requested
     */
    @SuppressWarnings("unchecked")
    public <T extends Asset> T get(@NotNull @AssetId String id) {
        if (!assets.containsKey(id))
            throw new NullPointerException("Asset of ID " + id + " either does not exist or " +
                                           "was attempted to be used before being registered");
        return (T) assets.get(id);
    }

    private static class AssetIndex {

        private final Map<String, Asset> assets = new TreeMap<>();

        @JsonProperty("animated_textures")
        private Map<String, AnimatedTextureProperties> animatedTextures = new LinkedHashMap<>();
        @JsonProperty("data_assets")
        private Map<String, String> dataAssets = new LinkedHashMap<>();
        @JsonProperty("icons")
        private Map<String, String> icons = new LinkedHashMap<>();
        @JsonProperty("sounds")
        private Map<String, String> sounds = new LinkedHashMap<>();
        @JsonProperty("textures")
        private Map<String, String> textures = new LinkedHashMap<>();
        @JsonProperty("texture_atlases")
        private Map<String, String> textureAtlases = new LinkedHashMap<>();
        @JsonProperty("typefaces")
        private Map<String, String> typefaces = new LinkedHashMap<>();

        private void registerAll(@NotNull AssetsBundle assetsBundle) {
            dataAssets.forEach((id, path) -> assets.put(id, new DataAsset(path)));
            icons.forEach((id, path) -> assets.put(id, new Icon(path)));
            sounds.forEach((id, path) -> assets.put(id, new Sound(path)));
            textures.forEach((id, path) -> assets.put(id, new Texture(path)));
            textureAtlases.forEach((id, path) -> assets.put(id, new TextureAtlas(path)));
            animatedTextures.forEach((id, properties) -> assets.put(id, createAnimatedTexture(properties)));
            typefaces.forEach((id, path) -> assets.put(id, new Typeface(path)));
            assets.forEach(assetsBundle::register);
        }

        private AnimatedTexture createAnimatedTexture(@NotNull AnimatedTextureProperties properties) {

            if (properties.frameDuration == -1) throw new TextureCreationException("\"frame_duration\" property not specified");
            if (properties.frames != null)
                return new AnimatedTexture(properties.frames.toArray(Texture[]::new), properties.frameDuration);
            return new AnimatedTexture(new Texture[0], properties.frameDuration);
        }

        private static class AnimatedTextureProperties {

            @JsonProperty("frame_duration")
            float frameDuration = -1;
            @JsonProperty("frames")
            List<Texture> frames;
        }
    }
}
