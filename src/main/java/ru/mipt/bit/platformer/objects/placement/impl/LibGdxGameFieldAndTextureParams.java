package ru.mipt.bit.platformer.objects.placement.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import lombok.RequiredArgsConstructor;

import ru.mipt.bit.platformer.objects.placement.GameFieldAndTextureParams;

@RequiredArgsConstructor
public class LibGdxGameFieldAndTextureParams implements GameFieldAndTextureParams {

    private final TiledMapTileLayer tileLayer;
    private final Texture texture;

    @Override
    public int getTextureHeight() {
        return texture.getHeight() / tileLayer.getTileHeight();
    }

    @Override
    public int getTextureWidth() {
        return texture.getWidth() / tileLayer.getTileWidth();
    }

    @Override
    public int getGameFieldWidth() {
        return tileLayer.getWidth();
    }

    @Override
    public int getGameFieldHeight() {
        return tileLayer.getHeight();
    }
}
