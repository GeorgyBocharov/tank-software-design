package ru.mipt.bit.platformer.graphic.impl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import lombok.RequiredArgsConstructor;

import ru.mipt.bit.platformer.graphic.Disposable;
import ru.mipt.bit.platformer.graphic.GraphicObjectRenderer;
import ru.mipt.bit.platformer.objects.Activated;
import ru.mipt.bit.platformer.objects.CollidingObject;

@RequiredArgsConstructor
public class LibGdxGraphicObstacle implements Disposable, GraphicObjectRenderer {

    private final Texture texture;
    private final TextureRegion textureRegion;
    private final Rectangle rectangle;
    private final CollidingObject collidingObject;


    @Override
    public void dispose() {
        texture.dispose();
    }

    @Override
    public void render(Batch batch) {
        float rotation = collidingObject.getOrientation().getValue();

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
    public boolean hasActivated(Activated activated) {
        return collidingObject == activated;
    }
}
