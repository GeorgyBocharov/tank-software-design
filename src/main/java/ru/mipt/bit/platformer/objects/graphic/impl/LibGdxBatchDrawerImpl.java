package ru.mipt.bit.platformer.objects.graphic.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import lombok.RequiredArgsConstructor;
import ru.mipt.bit.platformer.objects.graphic.LibGdxBatchDrawer;

/**
 * Adapter level
 */
@RequiredArgsConstructor
public class LibGdxBatchDrawerImpl implements LibGdxBatchDrawer {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;

    @Override
    public void drawRectangle(Batch batch, float rotation) {
        int regionWidth = textureRegion.getRegionWidth();
        int regionHeight = textureRegion.getRegionHeight();
        float regionOriginX = regionWidth / 2f;
        float regionOriginY = regionHeight / 2f;
        batch.draw(
                textureRegion, rectangle.x, rectangle.y, regionOriginX, regionOriginY,
                regionWidth, regionHeight, 1f, 1f, rotation
        );
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
